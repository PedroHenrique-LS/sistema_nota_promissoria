package com.faculdade.sistema_nota_promissoria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.CreateUserDTO;
import com.faculdade.sistema_nota_promissoria.service.UserService;

@RestController
@RequestMapping("v1/user")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping
	public ResponseEntity<CreateUserDTO> saveUser(@RequestBody CreateUserDTO userDTO){
		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userDTO));
	}
	

}
