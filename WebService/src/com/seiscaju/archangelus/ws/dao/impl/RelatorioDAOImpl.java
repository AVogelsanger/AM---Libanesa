package com.seiscaju.archangelus.ws.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.seiscaju.archangelus.ws.dao.RelatorioDAO;
import com.seiscaju.archangelus.ws.entity.Relatorio;

public class RelatorioDAOImpl 
			extends GenericDAOImpl<Relatorio, Integer>
					implements RelatorioDAO{

	public RelatorioDAOImpl(EntityManager em) {
		super(em);
	}
	
	@Override
	public List<Relatorio> listarPorPaciente(int id) {
		return em.createQuery("from Relatorio r where r.paciente.codigo = :id", Relatorio.class)
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<Relatorio> listarPorMedico(int medicoId) {
		return em.createQuery("SELECT DISTINCT r FROM Relatorio r JOIN r.paciente p JOIN p.medicos m WHERE m.codigo = :m", Relatorio.class)
				.setParameter("m", medicoId)
				.getResultList();
	}

	@Override
	public List<Relatorio> buscaPorNotas(String busca) {
		return em.createQuery("from Relatorio r where r.notas LIKE :n", Relatorio.class)
				.setParameter("n", "%" + busca + "%")
				.getResultList();
	}

	@Override
	public List<Relatorio> buscaPorNotasEPaciente(String busca, int pacienteId) {
		return em.createQuery("FROM Relatorio r WHERE r.notas LIKE :n AND r.paciente.codigo = :p", Relatorio.class)
				.setParameter("n", "%" + busca + "%")
				.setParameter("p", pacienteId)
				.getResultList();
	}

	@Override
	public List<Relatorio> buscaPorNotasEMedico(String busca, int medicoId) {
		return em.createQuery("SELECT DISTINCT r FROM Relatorio r JOIN r.paciente p JOIN p.medicos m WHERE r.notas LIKE :n AND m.codigo = :m", Relatorio.class)
				.setParameter("n", "%" + busca + "%")
				.setParameter("m", medicoId)
				.getResultList();
	}

}