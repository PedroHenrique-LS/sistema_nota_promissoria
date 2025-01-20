package com.faculdade.sistema_nota_promissoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_nota_promissoria.model.Cliente;
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
