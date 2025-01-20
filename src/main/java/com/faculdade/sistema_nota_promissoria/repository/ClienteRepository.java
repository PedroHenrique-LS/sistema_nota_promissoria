package com.faculdade.sistema_nota_promissoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.sistema_nota_promissoria.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
