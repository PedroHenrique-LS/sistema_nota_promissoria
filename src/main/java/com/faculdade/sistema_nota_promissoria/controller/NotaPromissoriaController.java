package com.faculdade.sistema_nota_promissoria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.model.NotaPromissoria;
import com.faculdade.sistema_nota_promissoria.service.NotaPromissoriaService;



@RestController
@RequestMapping("v1/nota")
public class NotaPromissoriaController {
	
	@Autowired
	NotaPromissoriaService notaPromissoriaService;
	
	@PostMapping
	public ResponseEntity<NotaPromissoria> save(@RequestBody NotaPromissoriaDTO notaPromissoriaDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(notaPromissoriaService.save(notaPromissoriaDTO));
		
	}

}
