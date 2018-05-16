package com.seiscaju.archangelus.jsf.to;

import java.util.Calendar;

public class AdminTO extends PessoaTO {

	public AdminTO() {
	}

	public AdminTO(String nome, String email, String senha, long cpf, Calendar dataNascimento) {
		super(nome, email, senha, cpf, dataNascimento);
	}
	
}
