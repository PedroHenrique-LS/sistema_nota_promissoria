package com.faculdade.sistema_nota_promissoria.dtos;

import java.util.List;

public record CustomPageDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {

}
