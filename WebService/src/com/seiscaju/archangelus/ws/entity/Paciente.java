package com.seiscaju.archangelus.ws.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="AM_PACIENTE")
public class Paciente extends Pessoa {
	
	@Override
	public String toString() {
		return "Paciente [plano=" + plano + ", medicos=" + medicos + ", relatorios=" + relatorios + "]";
	}

	@Column(name="ds_plano")
	@Enumerated(EnumType.STRING)
	private PlanoSaude plano;

	@JsonIgnore
	@ManyToMany(mappedBy="pacientes", fetch=FetchType.LAZY)
	List<Medico> medicos = new ArrayList<Medico>();

	@JsonIgnore
	@OneToMany(mappedBy="paciente",cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private List<Relatorio> relatorios = new ArrayList<Relatorio>();
	
	public Paciente() {
	}
	
	public Paciente(PlanoSaude plano) {
		this.plano = plano;
	}
	
	public Paciente(String nome, String email, String senha, long cpf, Calendar dataNascimento, PlanoSaude plano) {
		super(nome, email, senha, cpf, dataNascimento);
		this.plano = plano;
	}
	
	public void adicionarMedico(Medico medico) {
		this.medicos.add(medico);
		medico.pacientes.add(this);
	}
	
	public void adicionarRelatorio(Relatorio relatorio) {
		this.relatorios.add(relatorio);
		relatorio.setPaciente(this);
	}

	public PlanoSaude getPlano() {
		return plano;
	}

	public void setPlano(PlanoSaude plano) {
		this.plano = plano;
	}

	@JsonIgnore
	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	@JsonIgnore
	public List<Relatorio> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}
	
}
