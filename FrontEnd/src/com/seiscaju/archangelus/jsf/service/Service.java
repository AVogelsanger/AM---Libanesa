package com.seiscaju.archangelus.jsf.service;

import java.util.List;

public interface Service<TO, Chave> {
	
	void remover(Chave id) throws Exception;
	
	void atualizar(TO tabela) throws Exception;
	
	List<TO> listar() throws Exception;
	
	TO buscar(Chave id) throws Exception;

	void cadastrar(TO tabela) throws Exception;
	
}
