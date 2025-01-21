package com.faculdade.sistema_nota_promissoria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.sistema_nota_promissoria.dtos.ClienteComNotasDTO;
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
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> findCliente(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.findCliente(id));
	}
	
	@GetMapping
	public ResponseEntity<CustomPageDTO<Cliente>> findAllCliente(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "2") @Positive @Max(100) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Cliente> clientePage = clienteService.findAllCliente(pageable);
        
        CustomPageDTO<Cliente> customPage = new CustomPageDTO<>(
                clientePage.getContent(),
                clientePage.getNumber(),
                clientePage.getSize(),
                clientePage.getTotalElements(),
                clientePage.getTotalPages()
        );

        return ResponseEntity.ok(customPage);
    }
	
	@GetMapping("/{id}/notas")
	public ResponseEntity<ClienteComNotasDTO> getClienteComNotas(@PathVariable Long id, @RequestParam(required = false) String status){
		 if (status == null) {
		        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getClienteComNotas(id));
		    } else {
		        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getClienteComNotasByStatus(id, status));
		    }
	}
	
	@PostMapping
	public ResponseEntity<Cliente> save(@RequestBody @Valid @NotNull Cliente cliente) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
		
	}
	
	@PutMapping(value = "/{idOldCliente}")
	public ResponseEntity<Cliente> update(@PathVariable @Positive @NotNull Long idOldCliente, @RequestBody @Valid @NotNull Cliente clienteUpdated) {
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(idOldCliente, clienteUpdated));
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	

}
