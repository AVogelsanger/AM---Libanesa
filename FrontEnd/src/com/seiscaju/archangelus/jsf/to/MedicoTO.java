package com.seiscaju.archangelus.jsf.to;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicoTO extends PessoaTO {

	private int crm;
	
	private MedicoEspecialidade especialidade;

	@JsonProperty("pacientes")
	List<PacienteTO> pacientes = new ArrayList<PacienteTO>();
	
	public MedicoTO() {
	}
	
	public MedicoTO(int crm, MedicoEspecialidade especialidade) {
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public MedicoTO(String nome, String email, String senha, long cpf, Calendar dataNascimento, int crm, MedicoEspecialidade especialidade) {
		super(nome, email, senha, cpf, dataNascimento);
		this.crm = crm;
		this.especialidade = especialidade;
	}

	public int getCrm() {
		return crm;
	}

	public void setCrm(int crm) {
		this.crm = crm;
	}

	public MedicoEspecialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(MedicoEspecialidade especialidade) {
		this.especialidade = especialidade;
	}

	@JsonProperty("pacientes")
	public List<PacienteTO> getPacienteTOs() {
		return pacientes;
	}

	@JsonProperty("pacientes")
	public void setPacienteTOs(List<PacienteTO> pacientes) {
		this.pacientes = pacientes;
	}
	
}
