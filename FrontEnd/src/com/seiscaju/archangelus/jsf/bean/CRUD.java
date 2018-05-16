package com.seiscaju.archangelus.jsf.bean;

import java.util.List;

public interface CRUD<TO, Chave> {
	
	void excluir(Chave id);
	
	List<TO> getAll();

    String salvar();
	
}
