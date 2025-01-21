package com.faculdade.sistema_nota_promissoria.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_nota_promissoria.dtos.ClienteComNotasDTO;
import com.faculdade.sistema_nota_promissoria.dtos.SimplesNotaDTO;
import com.faculdade.sistema_nota_promissoria.enums.Status;
import com.faculdade.sistema_nota_promissoria.model.Cliente;
import com.faculdade.sistema_nota_promissoria.model.NotaPromissoria;
import com.faculdade.sistema_nota_promissoria.repository.ClienteRepository;



@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
	public Cliente findCliente(Long id) {
		var cliente = clienteRepository.findById(id);
		if(cliente.isEmpty())
			return null;
		return cliente.get();
	}
	
	public ClienteComNotasDTO getClienteComNotas(Long idCliente) {
		Cliente cliente = findCliente(idCliente);
		ClienteComNotasDTO clienteComNotasDTO = new ClienteComNotasDTO(idCliente, cliente.getNome(), new HashSet<SimplesNotaDTO>());
		
		for (NotaPromissoria nota : cliente.getNotas()) {
			clienteComNotasDTO.notas().add(montarSimplesNotaDTO(nota));
		}
		
		return clienteComNotasDTO;
		
	}
	
	public ClienteComNotasDTO getClienteComNotasByStatus(Long idCliente, String statusNota) {
		Cliente cliente = findCliente(idCliente);
		Status status;
		switch (statusNota.toUpperCase()) {
		case "ABERTA": {
			status = Status.ABERTA;
			return montarClienteComNotasByStatus(cliente, status);
		}
		case "FECHADA": {
			status = Status.FECHADA;
			return montarClienteComNotasByStatus(cliente, status);
		}
		case "ATRASADA": {
			status = Status.ATRASADA;
			return montarClienteComNotasByStatus(cliente, status);
		}
		case "NEGOCIADA": {
			status = Status.NEGOCIADA;
			return montarClienteComNotasByStatus(cliente, status);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + statusNota);
		}
		
		
	}
	
	private ClienteComNotasDTO montarClienteComNotasByStatus(Cliente cliente, Status statusNota) {
		
		ClienteComNotasDTO clienteComNotasDTO = new ClienteComNotasDTO(cliente.getId(), cliente.getNome(), new HashSet<SimplesNotaDTO>());
		
		for (NotaPromissoria nota : cliente.getNotas()) {
			if (nota.getStatus() == statusNota)
			    clienteComNotasDTO.notas().add(montarSimplesNotaDTO(nota));
		}
		
		return clienteComNotasDTO;
		
	}
	
	
	private SimplesNotaDTO montarSimplesNotaDTO(NotaPromissoria nota) {
		
		SimplesNotaDTO simplesNotaDTO = new SimplesNotaDTO(nota.getId(),
                nota.getDescricao(), 
                nota.getValor(), 
                nota.getQuantidadeParcelas(), 
                nota.getDataEmissao(), 
                nota.getDataFechamento(), 
                nota.getStatus());
		return simplesNotaDTO;
		
	}
	
	
	public Page<Cliente> findAllCliente(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
		
	}
	
	
	public Cliente update(Long idOldCliente, Cliente clienteUpdated) {
		Cliente oldCliente = findCliente(idOldCliente);
		oldCliente.setNome(clienteUpdated.getNome());
		oldCliente.setEmail(clienteUpdated.getEmail());
		oldCliente.setTelefone(clienteUpdated.getTelefone());
		return save(oldCliente);
		
	}
	
	public Void delete(Long id) {
		Cliente cliente = findCliente(id);
		clienteRepository.delete(cliente);
		return null;
		
	}

}
