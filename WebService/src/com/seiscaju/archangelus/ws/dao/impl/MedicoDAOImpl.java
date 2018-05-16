package com.seiscaju.archangelus.ws.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.seiscaju.archangelus.ws.dao.MedicoDAO;
import com.seiscaju.archangelus.ws.entity.Medico;

public class MedicoDAOImpl 
			extends GenericDAOImpl<Medico, Integer>
					implements MedicoDAO{

	public MedicoDAOImpl(EntityManager em) {
		super(em);
	}

	public List<Medico> buscaPorNome(String nome) {
		return em.createQuery("from Medico m where m.nome LIKE :n", Medico.class)
				.setParameter("n", "%" + nome + "%")
				.getResultList();
	}

	@Override
	public void desassociar(int medicoId, int pacienteId) {
		em.createQuery("delete from am_medico_paciente where cd_medico = :m and cd_paciente = :p")
		.setParameter("m", "%" + medicoId + "%")
		.setParameter("p", "%" + pacienteId + "%")
		.executeUpdate();
	}
	
}