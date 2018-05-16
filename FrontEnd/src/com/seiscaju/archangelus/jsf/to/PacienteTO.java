package com.seiscaju.archangelus.jsf.to;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PacienteTO extends PessoaTO {
	
	private PlanoSaude plano;

	@JsonProperty("medicos")
	List<MedicoTO> medicos = new ArrayList<MedicoTO>();
	
	private List<RelatorioTO> relatorios = new ArrayList<RelatorioTO>();
	
	public PacienteTO() {
	}
	
	public PacienteTO(PlanoSaude plano) {
		this.plano = plano;
	}
	
	public PacienteTO(String nome, String email, String senha, long cpf, Calendar dataNascimento, PlanoSaude plano) {
		super(nome, email, senha, cpf, dataNascimento);
		this.plano = plano;
	}

	public PlanoSaude getPlano() {
		return plano;
	}

	public void setPlano(PlanoSaude plano) {
		this.plano = plano;
	}

	@JsonProperty("medicos")
	public List<MedicoTO> getMedicoTOs() {
		return medicos;
	}

	@JsonProperty("medicos")
	public void setMedicoTOs(List<MedicoTO> medicos) {
		this.medicos = medicos;
	}

	public List<RelatorioTO> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioTO> relatorios) {
		this.relatorios = relatorios;
	}
	
}
