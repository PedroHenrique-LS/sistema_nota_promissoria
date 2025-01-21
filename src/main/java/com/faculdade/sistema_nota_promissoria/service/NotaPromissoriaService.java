package com.faculdade.sistema_nota_promissoria.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.enums.Status;
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

}
