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

@Path("/medicos")
public class MedicoResource {

	private MedicoDAO dao;
	private EntityManager em;
	
	public MedicoResource() {
		em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();
		dao = new MedicoDAOImpl(em);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Medico buscar(@PathParam("id") int id) {
		Medico medico = dao.buscar(id);

		return medico;
	}
	
	@GET
	@Path("busca/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Medico> relatorios(@PathParam("nome") String nome) {
		return dao.buscaPorNome(nome);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Medico> listar() {
		List<Medico> medicos = dao.listar();

		return medicos;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Medico medico, 
							@Context UriInfo uri) {
		try {
			dao.cadastrar(medico);
			dao.commit();	
			UriBuilder b = uri.getAbsolutePathBuilder();
			b.path(String.valueOf(medico.getCodigo()));
			
			return Response.created(b.build()).build();
		} catch(CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("associar/medico/{medicoId}/paciente/{pacienteId}")
	public Response associar(@PathParam("medicoId") int medicoId, @PathParam("pacienteId") int pacienteId, 
							@Context UriInfo uri) {
		try {
			Medico medico = dao.buscar(medicoId);
			
			PacienteDAO pacienteDAO = new PacienteDAOImpl(em);
			Paciente paciente = pacienteDAO.buscar(pacienteId);
			
			medico.adicionarPaciente(paciente);
			
			dao.atualizar(medico);
			dao.commit();
			
			return Response.ok().build();
		} catch(CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("desassociar/medico/{medicoId}/paciente/{pacienteId}")
	public Response desassociar(@PathParam("medicoId") int medicoId, @PathParam("pacienteId") int pacienteId, 
							@Context UriInfo uri) {
		try {
			Medico medico = dao.buscar(medicoId);
			
			PacienteDAO pacienteDAO = new PacienteDAOImpl(em);
			Paciente paciente = pacienteDAO.buscar(pacienteId);
			
			medico.removerPaciente(paciente);
			
			dao.atualizar(medico);
			dao.commit();
			
			return Response.noContent().build();
		} catch(CommitException e) {
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Medico medico, 
			@PathParam("id") int id) {
		try {
			medico.setCodigo(id);
			dao.atualizar(medico);
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
	
	@GET
	@Path("{id}/relatorios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> relatorios(@PathParam("id") int id) {
		RelatorioDAO relatorioDAO = new RelatorioDAOImpl(em);
		
		List<Relatorio> relatorios = relatorioDAO.listarPorMedico(id);

		return relatorios;
	}
	
}