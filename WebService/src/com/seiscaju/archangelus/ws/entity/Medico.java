package com.seiscaju.archangelus.ws.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="AM_MEDICO")
public class Medico extends Pessoa {

	@Column(name="cd_crm", nullable=false, unique=true)
	private int crm;
	
	@Column(name="ds_especialidade")
	private MedicoEspecialidade especialidade;

	@ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinTable(name="AM_MEDICO_PACIENTE",
		joinColumns= @JoinColumn(name="cd_medico"),
		inverseJoinColumns=@JoinColumn(name="cd_paciente"))
	List<Paciente> pacientes = new ArrayList<Paciente>();
	
	public Medico() {
	}
	
	public Medico(int crm, MedicoEspecialidade especialidade) {
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public Medico(String nome, String email, String senha, long cpf, Calendar dataNascimento, int crm, MedicoEspecialidade especialidade) {
		super(nome, email, senha, cpf, dataNascimento);
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public void adicionarPaciente(Paciente paciente) {
		this.pacientes.add(paciente);
		paciente.medicos.add(this);
	}
	
	public void removerPaciente(Paciente paciente) {
		this.pacientes.remove(paciente);
		paciente.medicos.remove(this);
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

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	
}
