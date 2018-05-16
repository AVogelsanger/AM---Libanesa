package com.seiscaju.archangelus.jsf.service;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.seiscaju.archangelus.jsf.to.RelatorioTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RelatorioService implements Service<RelatorioTO, Integer> {
	
	//private static String URL = "http://localhost:8080/WebService/rest/relatorios";
	private static String URL = "http://localhost:8080/06-WS-Restful-Server/rest/relatorios";
	private Client client = Client.create();
	
	@Override
	public void remover(Integer id) throws Exception {
		WebResource resource = client.resource(URL)
				.path(String.valueOf(id));
		ClientResponse response = resource
				.delete(ClientResponse.class);
		
		if (response.getStatus() != 204) {
			throw new Exception("Erro " + response.getStatus());
		}
	}
	
	@Override
	public void atualizar(RelatorioTO relatorio) throws Exception {
		WebResource resource = client.resource(URL)
			.path(String.valueOf(relatorio.getCodigo()));
		
		ClientResponse response =
			resource.type(MediaType.APPLICATION_JSON)
			.put(ClientResponse.class,relatorio);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
	} 
	
	@Override
	public List<RelatorioTO> listar() throws Exception {
		WebResource resource = client.resource(URL);
		ClientResponse response = resource
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
		
		return Arrays.asList(
			response.getEntity(RelatorioTO[].class));
	}
	
	@Override
	public RelatorioTO buscar(Integer id) throws Exception {
		WebResource resource = client.resource(URL)
							.path(String.valueOf(id));
		
		ClientResponse response = resource
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
		
		return response.getEntity(RelatorioTO.class);
	}
	
	public List<RelatorioTO> buscaPorNotas(String nota) throws Exception {
		WebResource resource = client.resource(URL + "/busca/" + nota);
		ClientResponse response = resource
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
		
		return Arrays.asList(
			response.getEntity(RelatorioTO[].class));
	}
	
	public List<RelatorioTO> buscaPorNotasEPaciente(String nota, int pacienteId) throws Exception {
		WebResource resource = client.resource(URL + "/busca/" + nota + "/paciente/" + pacienteId);
		ClientResponse response = resource
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
		
		return Arrays.asList(
			response.getEntity(RelatorioTO[].class));
	}
	
	public List<RelatorioTO> buscaPorNotasEPacienteDeMedico(String nota, int medicoId) throws Exception {
		WebResource resource = client.resource(URL + "/busca/" + nota + "/medico/" + medicoId);
		ClientResponse response = resource
			.accept(MediaType.APPLICATION_JSON)
			.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new Exception("Erro " + response.getStatus());
		}
		
		return Arrays.asList(
			response.getEntity(RelatorioTO[].class));
	}

	@Override
	public void cadastrar(RelatorioTO relatorio) throws Exception {
		WebResource resource = client.resource(URL);
		
		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, relatorio);

		if (response.getStatus() != 201) {
			throw new Exception("Erro: " + response.getStatus());
		} 
	}

}