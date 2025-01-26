package com.faculdade.sistema_nota_promissoria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.NotaPromissoriaDTO;
import com.faculdade.sistema_nota_promissoria.dtos.PagamentoNotaDTO;
import com.faculdade.sistema_nota_promissoria.model.NotaPromissoria;
import com.faculdade.sistema_nota_promissoria.service.NotaPromissoriaService;



@RestController
@RequestMapping("v1/nota")
public class NotaPromissoriaController {
	
	@Autowired
	NotaPromissoriaService notaPromissoriaService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NotaPromissoria> findCliente(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(notaPromissoriaService.findNota(id));
	}
	
	@PostMapping
	public ResponseEntity<NotaPromissoria> save(@RequestBody NotaPromissoriaDTO notaPromissoriaDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(notaPromissoriaService.save(notaPromissoriaDTO));
		
	}
	
	@PutMapping("/{id}/pagamento")
    public ResponseEntity<String> pagarParcela(@PathVariable Long id, @RequestBody PagamentoNotaDTO pagamentoNotaDTO) {
        try {
        	notaPromissoriaService.pagarParcela(id, pagamentoNotaDTO);
            return ResponseEntity.ok("Pagamento realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	

}
