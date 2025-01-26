package com.faculdade.sistema_nota_promissoria.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.dtos.PagamentoNotaDTO;
import com.faculdade.sistema_nota_promissoria.enums.Status;
import com.faculdade.sistema_nota_promissoria.enums.TipoPagamento;
import com.faculdade.sistema_nota_promissoria.model.NotaPromissoria;
import com.faculdade.sistema_nota_promissoria.model.Parcela;
import com.faculdade.sistema_nota_promissoria.repository.NotaPromissoriaRepository;
import com.faculdade.sistema_nota_promissoria.repository.ParcelaRepository;

import jakarta.transaction.Transactional;

@Service
public class NotaPromissoriaService {
	
	@Autowired
	NotaPromissoriaRepository notaRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ParcelaRepository parcelaRepository;
	
	public NotaPromissoria findNota(Long id) {
		var nota = notaRepository.findById(id);
		if(!nota.isEmpty()) {
			return verificarVencimento(nota.get());
		}
		return null;
	}
	
	private NotaPromissoria verificarVencimento(NotaPromissoria nota) {
		if(nota != null)
			for (Parcela parcelaAtual : nota.getParcelas()) {
				Boolean vencida = parcelaAtual.getVencimento().isBefore(LocalDate.now());
				if(vencida && parcelaAtual.getStatus() == Status.ABERTA) {
					parcelaAtual.setValorParcela(parcelaAtual.getValorParcela()*(1 + nota.getJurosAtraso()));
					parcelaAtual.setStatus(Status.ATRASADA);
					nota.setStatus(Status.ATRASADA);
					parcelaRepository.save(parcelaAtual);
				}
			}
		return notaRepository.save(nota);
	}
	
	@Transactional
	public NotaPromissoria save(NotaPromissoriaDTO notaPromissoriaDTO) {
		return notaRepository.save(processarNota(notaPromissoriaDTO));

	}

	private NotaPromissoria processarNota(NotaPromissoriaDTO dto) {
        NotaPromissoria notaSalva = new NotaPromissoria();
        notaSalva.setCliente(clienteService.findCliente(dto.idCliente()));
        notaSalva.setStatus(Status.ABERTA);
        notaSalva.setQuantidadeParcelas(dto.quantidadeParcelas());
        notaSalva.setDataEmissao(LocalDate.now());
        notaSalva.setDescricao(dto.descricao());
        notaSalva.setValor(dto.valorTotal());
        notaSalva.setJurosAtraso((dto.jurosAtraso() == null) ? 0.05 : dto.jurosAtraso());
        notaRepository.save(notaSalva);
        
        Double valorParcela = dto.valorTotal() / dto.quantidadeParcelas();

        for (int i = 1; i <= dto.quantidadeParcelas(); i++) {
            Parcela parcela = new Parcela();
            parcela.setValorParcela(valorParcela);
            parcela.setDataPagamento(null); 
            parcela.setVencimento(calcularDataVencimento(i)); 
            parcela.setNotaPromissoria(notaSalva); 
            parcela.setStatus(Status.ABERTA);
            parcelaRepository.save(parcela);
            notaSalva.getParcelas().add(parcela);
        }

        notaSalva.setDataFechamento(calcularDataVencimento(dto.quantidadeParcelas()));
        return notaSalva;

	}
	
	private LocalDate calcularDataVencimento(int parcelaNumero) {
        LocalDate data =  LocalDate.now().plusMonths(parcelaNumero);
        if(data.getMonthValue() == 2 && data.getDayOfMonth() > 28)
        	data = data.minusDays(3);
        return data;
    }
	
public void pagarParcela(Long notaPromissoriaId, PagamentoNotaDTO pagamentoNotaDTO) {
		
		Optional<Parcela> parceOptional = parcelaRepository.findProximaParcelaNaoPaga(notaPromissoriaId);
		
		if(parceOptional.isEmpty()) {
			NotaPromissoria notaPromissoria = findNota(notaPromissoriaId);
			notaPromissoria.setStatus(Status.FECHADA);
			notaRepository.save(notaPromissoria);
			throw new IllegalArgumentException("Não há parcelas pendentes para esta nota promissória.");
		}
			

		//próxima parcela não paga
		Parcela parcela = parceOptional.get();

		// Valida se o valor pago é suficiente
		Double valorPago = pagamentoNotaDTO.valorPago();
		Double valorTotal = parcela.getValorParcela();
		if (LocalDate.now().isAfter(parcela.getVencimento()) & parcela.getStatus() != Status.ATRASADA) { // Se a parcela
																											// está
																											// atrasada
			valorTotal += parcela.getValorParcela() * parcela.getNotaPromissoria().getJurosAtraso();
		}

		if (valorPago < valorTotal) {
			throw new IllegalArgumentException(
					"O valor pago é menor do que o valor total da parcela (incluindo juros, se aplicável).");
		}

		// Realiza o pagamento
		parcela.setValorPago(valorPago);
		parcela.setDataPagamento(LocalDate.now());
		parcela.setStatus(Status.FECHADA); // Status de "paga"
		parcela.setTipoPagamento(TipoPagamento.fromCodigo(pagamentoNotaDTO.tipoPagamento()));

		parcelaRepository.save(parcela);
	}

}
