package com.faculdade.sistema_nota_promissoria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.model.Cliente;
import com.faculdade.sistema_nota_promissoria.repository.ClienteRepository;
import com.faculdade.sistema_nota_promissoria.service.NotaPromissoriaService;

@Configuration
@Profile("test")
public class TesteConfig implements  CommandLineRunner {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	NotaPromissoriaService notaPromissoriaService; 
	

	@Override
	public void run(String... args) throws Exception {

		Cliente C1 = new Cliente(null, "João da Silva", "(11) 99999-9999", "joao.silva@example.com");
		Cliente C2 = new Cliente(null, "Maria Oliveira", "(21) 88888-8888", "maria.oliveira@example.com");
		Cliente C3 = new Cliente(null, "Carlos Pereira", "(31) 77777-7777", "carlos.pereira@example.com");
		
		
		clienteRepository.save(C1);
		clienteRepository.save(C2);
		clienteRepository.save(C3);
		
		notaPromissoriaService.save(new NotaPromissoriaDTO("Nota teste 01", 5000.00, 5, 1L, null));
		notaPromissoriaService.save(new NotaPromissoriaDTO("Nota teste 02", 2000.00, 2, 1L, null));
		notaPromissoriaService.save(new NotaPromissoriaDTO("Nota teste 03", 6000.00, 2, 2L, 0.03));
	}

}
