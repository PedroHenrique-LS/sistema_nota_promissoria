package com.faculdade.sistema_nota_promissoria.dtos;

import java.time.LocalDate;

import com.faculdade.sistema_nota_promissoria.enums.Status;

public record SimplesNotaDTO(Long id, String descricao, Double valor, Integer quantidadeParcelas, LocalDate dataEmissao, LocalDate dataFechamento, Status status) {

}
