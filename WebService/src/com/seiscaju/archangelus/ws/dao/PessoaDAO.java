package com.seiscaju.archangelus.ws.dao;

import com.seiscaju.archangelus.ws.entity.Pessoa;

public interface PessoaDAO 
			extends GenericDAO<Pessoa, Integer>{

	Pessoa logar(String email, String senha);
	
}