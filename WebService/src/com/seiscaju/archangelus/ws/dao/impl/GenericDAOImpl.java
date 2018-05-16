package com.seiscaju.archangelus.ws.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.seiscaju.archangelus.ws.dao.GenericDAO;
import com.seiscaju.archangelus.ws.exception.CommitException;
import com.seiscaju.archangelus.ws.exception.RegistroNaoEncontradoException;
import com.seiscaju.archangelus.ws.singleton.EntityManagerFactorySingleton;

public abstract class GenericDAOImpl <Tabela, Chave>
					implements GenericDAO<Tabela, Chave>{

	protected EntityManager em;
	
	private Class<Tabela> clazz;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl(EntityManager em) {
		this.em = em;
		clazz = (Class<Tabela>)
			((ParameterizedType)
				getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
	}

	@Override
	public void cadastrar(Tabela tabela) {
		em.persist(tabela);
	}

	@Override
	public void atualizar(Tabela tabela) {
		em.merge(tabela);
	}

	@Override
	public void excluir(Chave codigo) throws RegistroNaoEncontradoException {
		Tabela t  = buscar(codigo);
		if (t == null) {
			throw new RegistroNaoEncontradoException("Registro não encontrado");
		}
		em.remove(t);
	}

	@Override
	public Tabela buscar(Chave codigo) {
		return em.find(clazz, codigo);
	}

	@Override
	public void commit() throws CommitException {
		try {
			em.getTransaction().begin();
			em.getTransaction().commit();
		}catch(Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			throw new CommitException("Erro ao gravar");
		}
		
	}

	@Override
	public List<Tabela> listar() {
		return em.createQuery("from " + clazz.getName(),
				clazz).getResultList();
	}

	@Override
	public void fechar() {
		EntityManagerFactory fabrica = EntityManagerFactorySingleton.getInstance();
		
		em.close();

		if (fabrica.isOpen()) {
			fabrica.close();
		}
	}

}





