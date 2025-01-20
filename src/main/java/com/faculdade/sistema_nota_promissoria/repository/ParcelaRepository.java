package com.faculdade.sistema_nota_promissoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.sistema_nota_promissoria.model.Parcela;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

}