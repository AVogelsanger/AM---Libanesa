package com.seiscaju.archangelus.jsf.service;

import javax.ws.rs.core.MediaType;

import com.seiscaju.archangelus.jsf.to.AdminTO;
import com.seiscaju.archangelus.jsf.to.MedicoTO;
import com.seiscaju.archangelus.jsf.to.PacienteTO;
import com.seiscaju.archangelus.jsf.to.PessoaTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PessoaService {
	
	//private static String URL = "http://localhost:8080/WebService/rest/pessoas";
	private static String URL = "http://localhost:8080/06-WS-Restful-Server/rest/pessoas";
	private Client client = Client.create();
	
	public PessoaTO logar(PessoaTO pessoa) throws Exception {
		WebResource resource = client.resource(URL);

		ClientResponse response = resource
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, pessoa);
		
		response.bufferEntity();

		if (response.getStatus() == 200) {
        	PessoaTO p = response.getEntity(PessoaTO.class);

        	response.getEntityInputStream().reset();
        	
        	switch (p.getTipo()) {
            	case "Admin":
            		return response.getEntity(AdminTO.class);
            	case "Medico":
            		return response.getEntity(MedicoTO.class);
            	case "Paciente":
            		return response.getEntity(PacienteTO.class);
        	}
		}
		
	    return null;
	}

}