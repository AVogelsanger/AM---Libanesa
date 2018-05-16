package com.seiscaju.archangelus.ws.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="AM_ADMIN")
public class Admin extends Pessoa {

	public Admin() {
	}

	public Admin(String nome, String email, String senha, long cpf, Calendar dataNascimento) {
		super(nome, email, senha, cpf, dataNascimento);
	}
	
}
