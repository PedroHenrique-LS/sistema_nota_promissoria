package com.faculdade.sistema_nota_promissoria.enums;

public enum Status {

	ABERTA(1),
	FECHADA(2),
	ATRASADA(3),
	NEGOCIADA(4);

	private final int codigo;

	Status(int codigo) {
	        this.codigo = codigo;
	    }

	public int getCodigo() {
		return codigo;
	}

	public static Status fromCodigo(int codigo) {
		for (Status status : values()) {
			if (status.getCodigo() == codigo) {
				return status;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}

}
