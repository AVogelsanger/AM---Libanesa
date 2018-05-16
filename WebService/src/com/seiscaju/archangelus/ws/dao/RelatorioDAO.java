package com.seiscaju.archangelus.ws.dao;

import java.util.List;

import com.seiscaju.archangelus.ws.entity.Relatorio;

public interface RelatorioDAO 
			extends GenericDAO<Relatorio, Integer>{

	List<Relatorio> buscaPorNotas(String busca);
	List<Relatorio> buscaPorNotasEPaciente(String busca, int pacienteId);
	List<Relatorio> buscaPorNotasEMedico(String busca, int medicoId);
	List<Relatorio> listarPorPaciente(int id);
	List<Relatorio> listarPorMedico(int medicoId);

}