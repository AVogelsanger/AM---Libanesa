package com.seiscaju.archangelus.ws.dao;

import java.util.List;

import com.seiscaju.archangelus.ws.entity.Paciente;

public interface PacienteDAO 
			extends GenericDAO<Paciente, Integer>{

	List<Paciente> buscaPorNome(String nome);
	List<Paciente> buscaPorNomeEMedico(String nome, int medicoId);

}