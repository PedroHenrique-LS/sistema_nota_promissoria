package com.faculdade.sistema_nota_promissoria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.CustomPageDTO;
import com.faculdade.sistema_nota_promissoria.model.Cliente;
import com.faculdade.sistema_nota_promissoria.service.ClienteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("v1/cliente")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	
	
	@GetMapping
	public ResponseEntity<CustomPageDTO<Cliente>> findAllCliente(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "2") @Positive @Max(100) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Cliente> clientePage = clienteService.findAllCliente(pageable);
        
        // Cria o record CustomPageDTO diretamente
        CustomPageDTO<Cliente> customPage = new CustomPageDTO<>(
                clientePage.getContent(),
                clientePage.getNumber(),
                clientePage.getSize(),
                clientePage.getTotalElements(),
                clientePage.getTotalPages()
        );

        return ResponseEntity.ok(customPage);
    }
	
	@PostMapping
	public ResponseEntity<Cliente> save(@RequestBody @Valid @NotNull Cliente cliente) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
		
	}

}
