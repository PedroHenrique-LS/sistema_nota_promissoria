package com.faculdade.sistema_nota_promissoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faculdade.sistema_nota_promissoria.model.Parcela;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
	
	@Query("SELECT p FROM Parcela p WHERE p.notaPromissoria.id = :notaPromissoriaId AND p.status != 2 ORDER BY p.vencimento ASC LIMIT 1")
	public Optional<Parcela> findProximaParcelaNaoPaga(Long notaPromissoriaId);

}