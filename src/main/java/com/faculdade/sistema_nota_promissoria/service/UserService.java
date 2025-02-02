package com.faculdade.sistema_nota_promissoria.service;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.faculdade.sistema_nota_promissoria.dtos.CreateUserDTO;
import com.faculdade.sistema_nota_promissoria.model.Role;
import com.faculdade.sistema_nota_promissoria.model.User;
import com.faculdade.sistema_nota_promissoria.repository.RoleRepository;
import com.faculdade.sistema_nota_promissoria.repository.UserRepository;



@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public CreateUserDTO  saveUser(CreateUserDTO userDTO) {
		var basicRole = roleRepository.findByNome(Role.Values.BASIC.name());
		
		var userFromDb = userRepository.findByName(userDTO.userName());
		if(userFromDb.isPresent()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		var user = new User();
		user.setName(userDTO.userName());
		user.setSenha(passwordEncoder.encode(userDTO.password()));
		user.setRoles(Set.of(basicRole));
		
		var userSaved =  userRepository.save(user);
		
		return new CreateUserDTO(userSaved.getName(), null);
		
	}
	
	
	

}
