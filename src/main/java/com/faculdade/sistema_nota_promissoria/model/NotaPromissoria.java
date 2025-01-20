package com.faculdade.sistema_nota_promissoria.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.faculdade.sistema_nota_promissoria.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class NotaPromissoria implements Serializable  {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;
	private double valor;
	private LocalDate dataEmissao;
	private int quantidadeParcelas;
	private LocalDate dataFechamento;
	private int status;
	private double jurosAtraso = 0.05;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy = "notaPromissoria")
	private Set<Parcela> parcelas =  new HashSet<>();

	public NotaPromissoria() {}

	public NotaPromissoria(Long id, String descricao, double valor, LocalDate dataEmissao, int quantidadeParcelas,
			LocalDate dataFechamento, Status status, Cliente cliente, double jurosAtraso) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.dataEmissao = dataEmissao;
		this.quantidadeParcelas = quantidadeParcelas;
		this.dataFechamento = dataFechamento;
		setStatus(status);
		this.cliente = cliente;
		this.jurosAtraso = jurosAtraso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Double getValorTotal() {
		Double valorTotal = 0.0;
		for (Parcela parcela : parcelas) {
			valorTotal += parcela.getValorParcela();
		}
		
		return valorTotal;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Status getStatus() {
		return Status.fromCodigo(status);
	}

	public void setStatus(Status status) {
		this.status = status.getCodigo();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getJurosAtraso() {
		return jurosAtraso;
	}

	public void setJurosAtraso(double jurosAtraso) {
		this.jurosAtraso = jurosAtraso;
	}
	
	public Set<Parcela> getParcelas() {
		return parcelas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaPromissoria other = (NotaPromissoria) obj;
		return Objects.equals(id, other.id);
	}
	

}
