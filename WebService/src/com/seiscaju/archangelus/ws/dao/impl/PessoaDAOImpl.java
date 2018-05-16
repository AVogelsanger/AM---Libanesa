package com.seiscaju.archangelus.ws.dao.impl;

import javax.persistence.EntityManager;

import com.seiscaju.archangelus.ws.dao.PessoaDAO;
import com.seiscaju.archangelus.ws.entity.Admin;
import com.seiscaju.archangelus.ws.entity.Medico;
import com.seiscaju.archangelus.ws.entity.Paciente;
import com.seiscaju.archangelus.ws.entity.Pessoa;

public class PessoaDAOImpl 
			extends GenericDAOImpl<Pessoa, Integer>
					implements PessoaDAO{

	public PessoaDAOImpl(EntityManager em) {
		super(em);
	}

	public Pessoa logar(String email, String senha) {
        Pessoa pessoa = null;

		try {
            pessoa = em.createQuery("from Pessoa p where p.email = :e and p.senha = :s", Pessoa.class)
				.setParameter("e", email)
				.setParameter("s", senha)
				.getSingleResult();
            
            if (pessoa != null) {
                switch (pessoa.getTipo()) {
	                case "Admin":
	                    pessoa = em.createQuery("from Admin a where a.codigo = :c", Admin.class)
	                        .setParameter("c", pessoa.getCodigo())
	                        .getSingleResult();
	                    break;
                    case "Medico":
                        pessoa = em.createQuery("from Medico m where m.codigo = :c", Medico.class)
                            .setParameter("c", pessoa.getCodigo())
                            .getSingleResult();
                        break;
                    case "Paciente":
                    	System.out.println("aaaaa");
                        pessoa = em.createQuery("from Paciente p where p.codigo = :c", Paciente.class)
                            .setParameter("c", pessoa.getCodigo())
                            .getSingleResult();
                        break;
                }
            }
            
            return pessoa;
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	
        return null;
	}

}