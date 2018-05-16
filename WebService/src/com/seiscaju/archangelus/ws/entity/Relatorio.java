package com.seiscaju.archangelus.ws.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="AM_RELATORIO")
@SequenceGenerator(name="relatorio", sequenceName="SQ_AM_RELATORIO", allocationSize=1)
public class Relatorio {

	@Override
	public String toString() {
		return "Relatorio [codigo=" + codigo + ", valor=" + valor + ", tipo=" + tipo + ", data=" + data + ", notas="
				+ notas + ", paciente=" + paciente + "]";
	}

	@Id
	@Column(name="cd_relatorio")
	@GeneratedValue(generator="relatorio",strategy=GenerationType.SEQUENCE)
	private int codigo;
	
	@Column(name="vl_dado_medico", nullable=false)
	private double valor;

	@Column(name="ds_tipo", nullable=false)
	private TipoRelatorio tipo;
	
	@Column(name="data", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	
	@Column(name="ds_notas", nullable=false)
	private String notas;

	@ManyToOne()
	@JoinColumn(name="cd_paciente", updatable=false)
	private Paciente paciente;
	
	public Relatorio() {
	}

	public Relatorio(int codigo, double valor, TipoRelatorio tipo, Calendar data, String notas, Paciente paciente) {
		this.codigo = codigo;
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
		this.notas = notas;
		this.paciente = paciente;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public TipoRelatorio getTipo() {
		return tipo;
	}

	public void setTipo(TipoRelatorio tipo) {
		this.tipo = tipo;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
}
