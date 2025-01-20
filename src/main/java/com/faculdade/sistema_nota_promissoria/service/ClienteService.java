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
	
	
	public Page<Cliente> findAllCliente(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
		
	}

}
