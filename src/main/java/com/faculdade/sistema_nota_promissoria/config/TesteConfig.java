package com.faculdade.sistema_nota_promissoria.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.model.Cliente;
import com.faculdade.sistema_nota_promissoria.model.Role;
import com.faculdade.sistema_nota_promissoria.model.User;
import com.faculdade.sistema_nota_promissoria.repository.ClienteRepository;
import com.faculdade.sistema_nota_promissoria.repository.RoleRepository;
import com.faculdade.sistema_nota_promissoria.repository.UserRepository;
import com.faculdade.sistema_nota_promissoria.service.NotaPromissoriaService;


@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {

private ClienteRepository clienteRepository;
	
	private NotaPromissoriaService notaPromissoriaService;
	
	private RoleRepository roleRepository;
	
	private UserRepository userRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public TesteConfig(ClienteRepository clienteRepository, NotaPromissoriaService notaPromissoriaService,
			RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.clienteRepository = clienteRepository;
		this.notaPromissoriaService = notaPromissoriaService;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Override
	public void run(String... args) throws Exception {

		Role roleAdmin = roleRepository.save(new Role(null, Role.Values.ADMIN.name()));
		Role roleBasic = roleRepository.save(new Role(null, Role.Values.BASIC.name()));

		var userAdmin = userRepository.findByName("admin");

		userAdmin.ifPresentOrElse((user) -> {
			System.out.println("admin já existe!");
		}, () -> {
			var user = new User();
			user.setName("admin");
			user.setSenha(bCryptPasswordEncoder.encode("123"));
			user.setRoles(Set.of(roleAdmin));
			userRepository.save(user);
		});

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
