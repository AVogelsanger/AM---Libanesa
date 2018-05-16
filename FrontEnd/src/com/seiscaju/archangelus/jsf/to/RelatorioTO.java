package com.seiscaju.archangelus.jsf.to;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatorioTO {

	private int codigo;
	
	private double valor;
	
	private String notas;

	private TipoRelatorio tipo;
	
	private Calendar data;

	@JsonProperty("paciente")
	private PacienteTO paciente;
	
	public RelatorioTO() {
	}

	public RelatorioTO(int codigo, double valor, String notas, TipoRelatorio tipo, Calendar data, PacienteTO paciente) {
		this.codigo = codigo;
		this.valor = valor;
		this.notas = notas;
		this.tipo = tipo;
		this.data = data;
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
	
	public String getNotas() {
		return notas;
	}
	
	public void setNotas(String notas) {
		this.notas = notas;
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

	@JsonProperty("paciente")
	public PacienteTO getPacienteTO() {
		return paciente;
	}

	@JsonProperty("paciente")
	public void setPacienteTO(PacienteTO paciente) {
		this.paciente = paciente;
	}
	
}
