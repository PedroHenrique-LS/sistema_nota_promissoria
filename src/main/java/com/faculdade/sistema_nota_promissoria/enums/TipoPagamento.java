package com.faculdade.sistema_nota_promissoria.enums;

public enum TipoPagamento {
	
	DINHEIRO(1),
	PIX(2),
	CARTAO(3);

	private final int codigo;

	TipoPagamento(int codigo) {
	        this.codigo = codigo;
	    }

	public int getCodigo() {
		return codigo;
	}

	public static TipoPagamento fromCodigo(int codigo) {
		for (TipoPagamento status : values()) {
			if (status.getCodigo() == codigo) {
				return status;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
