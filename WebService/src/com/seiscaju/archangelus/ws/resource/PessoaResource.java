package com.seiscaju.archangelus.ws.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.seiscaju.archangelus.ws.dao.PessoaDAO;
import com.seiscaju.archangelus.ws.dao.impl.PessoaDAOImpl;
import com.seiscaju.archangelus.ws.entity.Pessoa;
import com.seiscaju.archangelus.ws.singleton.EntityManagerFactorySingleton;

@Path("/pessoas")
public class PessoaResource {

	private PessoaDAO dao;
	
	public PessoaResource() {
		EntityManager em = 
			EntityManagerFactorySingleton.getInstance()
			.createEntityManager();
		dao = new PessoaDAOImpl(em);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa logar(Pessoa loginInfo, 
							@Context UriInfo uri) {
			Pessoa pessoa = dao.logar(loginInfo.getEmail(), loginInfo.getSenha());

	        return pessoa;
	}

}