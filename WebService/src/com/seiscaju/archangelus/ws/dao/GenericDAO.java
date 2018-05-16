package com.seiscaju.archangelus.ws.dao;

import java.util.List;

import com.seiscaju.archangelus.ws.exception.CommitException;
import com.seiscaju.archangelus.ws.exception.RegistroNaoEncontradoException;

public interface GenericDAO <Tabela, Chave> {

	void cadastrar(Tabela tabela);
	
	void atualizar(Tabela tabela);
	
	void excluir(Chave codigo) throws RegistroNaoEncontradoException;
	
	Tabela buscar(Chave codigo);
	
	void commit() throws CommitException;
	
	List<Tabela> listar();
	
	void fechar();
	
}


