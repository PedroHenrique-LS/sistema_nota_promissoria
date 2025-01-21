package com.faculdade.sistema_nota_promissoria.dtos;

import java.util.Set;

public record ClienteComNotasDTO( Long id, String nome, Set<SimplesNotaDTO> notas) {

}
