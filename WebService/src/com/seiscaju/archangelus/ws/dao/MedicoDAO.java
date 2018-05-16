package com.seiscaju.archangelus.ws.dao;

import java.util.List;

import com.seiscaju.archangelus.ws.entity.Medico;

public interface MedicoDAO 
			extends GenericDAO<Medico, Integer>{

	List<Medico> buscaPorNome(String nome);
	void desassociar(int medicoId, int pacienteId);

}