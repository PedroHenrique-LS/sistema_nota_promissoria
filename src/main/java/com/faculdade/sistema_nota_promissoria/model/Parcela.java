package com.faculdade.sistema_nota_promissoria.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.faculdade.sistema_nota_promissoria.enums.Status;
import com.faculdade.sistema_nota_promissoria.enums.TipoPagamento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Parcela implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descrição;
	@ManyToOne
	@JoinColumn(name = "nota_promissoria_id")
	private NotaPromissoria notaPromissoria;
	private Double valorParcela;
	private Double valorPago;
	private LocalDate vencimento;
	private LocalDate dataPagamento;
	private int status;
	private int tipoPagamento;
	
	public Parcela() {}
	
	public Parcela(Long id, String descrição, NotaPromissoria notaPromissoria, Double valorParcela, Double valorPago,
			LocalDate vencimento, LocalDate dataPagamento, Status status, TipoPagamento tipoPagamento) {
		this.id = id;
		this.descrição = descrição;
		this.notaPromissoria = notaPromissoria;
		this.valorParcela = valorParcela;
		this.valorPago = valorPago;
		this.vencimento = vencimento;
		this.dataPagamento = dataPagamento;
		setStatus(status);
		setTipoPagamento(tipoPagamento);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrição() {
		return descrição;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}

	public NotaPromissoria getNotaPromissoria() {
		return notaPromissoria;
	}

	public void setNotaPromissoria(NotaPromissoria notaPromissoria) {
		this.notaPromissoria = notaPromissoria;
	}

	public Double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Status getStatus() {
		return Status.fromCodigo(status);
	}

	public void setStatus(Status status) {
		this.status = status.getCodigo();
	}

	public TipoPagamento getTipoPagamento() {
		return TipoPagamento.fromCodigo(tipoPagamento);
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento.getCodigo();
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
		Parcela other = (Parcela) obj;
		return Objects.equals(id, other.id);
	}
	

}
