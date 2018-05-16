package com.seiscaju.archangelus.ws.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DS_TIPO")
@DiscriminatorValue("P")

@Entity
@Table(name="AM_PESSOA")
@SequenceGenerator(name="pessoa", sequenceName="SQ_AM_PESSOA", allocationSize=1)
public class Pessoa {

	@Id
	@Column(name="cd_pessoa")
	@GeneratedValue(generator="pessoa", strategy=GenerationType.SEQUENCE)
	private int codigo;

	@Column(name="nm_pessoa", nullable=false)
	private String nome;

	@Column(name="ds_email", nullable=false)
	private String email;

	@JsonProperty(access=Access.WRITE_ONLY)
	@Column(name="ds_senha", nullable=false)
	private String senha;

	@Column(name="nr_cpf", nullable=false)
	private long cpf;
	
	@Column(name="DS_TIPO", updatable=false, insertable=false)
	private String tipo;

	@Column(name="dt_nascimento")
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento;
	
	public Pessoa() {
	}
	
	public Pessoa(String nome, String email, String senha, long cpf, Calendar dataNascimento) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
