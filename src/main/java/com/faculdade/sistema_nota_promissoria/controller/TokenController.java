package com.faculdade.sistema_nota_promissoria.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.LoginRequestDTO;
import com.faculdade.sistema_nota_promissoria.dtos.LoginResposeDTO;
import com.faculdade.sistema_nota_promissoria.model.Role;
import com.faculdade.sistema_nota_promissoria.repository.UserRepository;


@RestController
@RequestMapping("v1/login")
public class TokenController {
	
	private final JwtEncoder jwtEncoder; 
	private final UserRepository userRepository; 
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping
	public ResponseEntity<LoginResposeDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		var user = userRepository.findByName(loginRequestDTO.userName());
		
		if (user.isEmpty()|| !user.get().isLoginCorrect(loginRequestDTO, bCryptPasswordEncoder)) {
			throw new BadCredentialsException("Nome do usuário ou senha inválido!");
		}
		
		var now = Instant.now();
		var expiresIn = 300L;
		
		var scopes = user.get().getRoles()
                .stream()
                .map(Role::getNome)
                .collect(Collectors.joining(" "));
		
		var claims = JwtClaimsSet.builder()
				.issuer("meuBackend")
				.subject(user.get().getId().toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.claim("scope", scopes)
				.build();
		
		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
		return ResponseEntity.ok(new LoginResposeDTO(jwtValue, expiresIn));
		
	}
	

}
