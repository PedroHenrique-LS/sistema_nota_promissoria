package com.faculdade.sistema_nota_promissoria.dtos;

import jakarta.validation.constraints.NotBlank;

public record NotaPromissoriaDTO(@NotBlank String descricao, @NotBlank Double valorTotal, @NotBlank Integer quantidadeParcelas, @NotBlank Long idCliente, Double jurosAtraso) {

}
