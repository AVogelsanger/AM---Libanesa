package com.seiscaju.archangelus.ws.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.seiscaju.archangelus.ws.dao.MedicoDAO;
import com.seiscaju.archangelus.ws.dao.PacienteDAO;
import com.seiscaju.archangelus.ws.dao.RelatorioDAO;
import com.seiscaju.archangelus.ws.dao.impl.MedicoDAOImpl;
import com.seiscaju.archangelus.ws.dao.impl.PacienteDAOImpl;
import com.seiscaju.archangelus.ws.dao.impl.RelatorioDAOImpl;
import com.seiscaju.archangelus.ws.entity.Medico;
import com.seiscaju.archangelus.ws.entity.Paciente;
import com.seiscaju.archangelus.ws.entity.Relatorio;
import com.seiscaju.archangelus.ws.exception.CommitException;
import com.seiscaju.archangelus.ws.singleton.EntityManagerFactorySingleton;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/pacientes")
public class PacienteResource {

	private PacienteDAO dao;
	EntityManager em;
	
	public PacienteResource() {
		em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();
		dao = new PacienteDAOImpl(em);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Paciente buscar(@PathParam("id") int id) {
		Paciente paciente = dao.buscar(id);

		return paciente;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Paciente> listar() {
		List<Paciente> pacientes = dao.listar();

		return pacientes;
	}
	
	@GET
	@Path("medico/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Paciente> listarPorMedico(@PathParam("id") int medicoId) {
		MedicoDAO medicoDAO = new MedicoDAOImpl(em);
		Medico medico = medicoDAO.buscar(medicoId);

		return medico.getPacientes();
	}
	
	@GET
	@Path("{id}/relatorios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> relatorios(@PathParam("id") int id) {
		RelatorioDAO relatorioDAO = new RelatorioDAOImpl(em);
		
		List<Relatorio> relatorios = relatorioDAO.listarPorPaciente(id);

		return relatorios;
	}
	
	@GET
	@Path("busca/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Paciente> buscaPorNome(@PathParam("nome") String nome) {
		return dao.buscaPorNome(nome);
	}
	
	@GET
	@Path("busca/{nome}/medico/{medicoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Paciente> buscaPorNomeEMedico(@PathParam("nome") String nome, @PathParam("medicoId") int medicoId) {
		return dao.buscaPorNomeEMedico(nome, medicoId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Paciente paciente, 
							@Context UriInfo uri) {
		try {
			dao.cadastrar(paciente);
			dao.commit();
			
			UriBuilder b = uri.getAbsolutePathBuilder();
			b.path(String.valueOf(paciente.getCodigo()));
			
			return Response.created(b.build()).build();
		} catch(CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("medico/{medicoId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Paciente paciente, @PathParam("medicoId") int medicoId, 
							@Context UriInfo uri) {
		try {
			MedicoDAO medicoDAO = new MedicoDAOImpl(em);
			Medico medico = medicoDAO.buscar(medicoId);
			
			paciente.adicionarMedico(medico);
			
			dao.cadastrar(paciente);
			dao.commit();
			
			UriBuilder b = uri.getAbsolutePathBuilder();
			b.path(String.valueOf(paciente.getCodigo()));
			
			return Response.created(b.build()).build();
		} catch(CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Paciente paciente, 
			@PathParam("id") int id) {
		try {
			paciente.setCodigo(id);
			
			dao.atualizar(paciente);
			dao.commit();
			
			return Response.ok().build();
		} catch (CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public void apagar(@PathParam("id") int id) {
		try {
			dao.excluir(id);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(
						Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}