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

import com.seiscaju.archangelus.ws.dao.RelatorioDAO;
import com.seiscaju.archangelus.ws.dao.impl.RelatorioDAOImpl;
import com.seiscaju.archangelus.ws.entity.Relatorio;
import com.seiscaju.archangelus.ws.exception.CommitException;
import com.seiscaju.archangelus.ws.singleton.EntityManagerFactorySingleton;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/relatorios")
public class RelatorioResource {

	private RelatorioDAO dao;
	
	public RelatorioResource() {
		EntityManager em = 
			EntityManagerFactorySingleton.getInstance()
			.createEntityManager();
		dao = new RelatorioDAOImpl(em);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Relatorio buscar(@PathParam("id") int id) {
		Relatorio relatorio = dao.buscar(id);

		return relatorio;
	}
	
	@GET
	@Path("busca/{nota}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> relatorios(@PathParam("nota") String nota) {
		return dao.buscaPorNotas(nota);
	}
	
	@GET
	@Path("busca/{nota}/paciente/{pacienteId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> relatoriosPorPaciente(@PathParam("nota") String nota, @PathParam("pacienteId") int pacienteId) {
		return dao.buscaPorNotasEPaciente(nota, pacienteId);
	}
	
	@GET
	@Path("busca/{nota}/medico/{medicoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> relatoriosPorPacienteDeMedico(@PathParam("nota") String nota, @PathParam("medicoId") int medicoId) {
		return dao.buscaPorNotasEMedico(nota, medicoId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Relatorio> listar() {
		List<Relatorio> relatorios = dao.listar();

		return relatorios;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Relatorio relatorio, 
							@Context UriInfo uri) {
		try {
			dao.cadastrar(relatorio);
			dao.commit();
			
			UriBuilder b = uri.getAbsolutePathBuilder();
			b.path(String.valueOf(relatorio.getCodigo()));
			
			return Response.created(b.build()).build();
		} catch(Exception e) {
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Relatorio relatorio, 
			@PathParam("id") int id) {
		try {
			relatorio.setCodigo(id);
			
			dao.atualizar(relatorio);
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