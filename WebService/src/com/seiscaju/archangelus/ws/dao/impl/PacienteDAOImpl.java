package com.seiscaju.archangelus.ws.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.seiscaju.archangelus.ws.dao.PacienteDAO;
import com.seiscaju.archangelus.ws.entity.Paciente;

public class PacienteDAOImpl 
			extends GenericDAOImpl<Paciente, Integer>
					implements PacienteDAO{

	public PacienteDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public List<Paciente> buscaPorNome(String nome) {
		return em.createQuery("from Paciente p where p.nome LIKE :n", Paciente.class)
				.setParameter("n", "%" + nome + "%")
				.getResultList();
	}

	@Override
	public List<Paciente> buscaPorNomeEMedico(String nome, int medicoId) {
		return em.createQuery("SELECT DISTINCT p FROM Paciente p JOIN p.medicos m WHERE p.nome LIKE :n AND m.codigo = :m", Paciente.class)
				.setParameter("n", "%" + nome + "%")
				.setParameter("m", medicoId)
				.getResultList();
	}

}