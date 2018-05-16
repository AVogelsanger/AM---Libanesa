package com.seiscaju.archangelus.jsf.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.seiscaju.archangelus.jsf.service.PessoaService;
import com.seiscaju.archangelus.jsf.to.PessoaTO;

@ManagedBean
public class LoginBean {

	private String email;
	
	private String senha;
	
	private PessoaService service;
	
	@PostConstruct
	private void init() {
		service = new PessoaService();
	}
	
	@SuppressWarnings("unused")
	public String logar() {
        FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		
		PessoaTO loginInfo = new PessoaTO();
		loginInfo.setEmail(email);
		loginInfo.setSenha(senha);
		
		PessoaTO pessoa;
		
		try {
			pessoa = service.logar(loginInfo);
		} catch (Exception e) {
			e.printStackTrace();

			msg = new FacesMessage("Erro desconhecido");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			return "index";
		}
		
		if (pessoa == null) {
			msg = new FacesMessage("Usuario ou senha invalido");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage(null, msg);
			
			return "login";
		}
		
        context.getExternalContext().getSessionMap().put("user", pessoa);
        
        String mensagem = "Logado com sucesso";
        
		switch (pessoa.getTipo()) {
			case "Admin":
				mensagem += new FacesMessage(" como admin!");
				break;
			case "Medico":
				mensagem += new FacesMessage(" como medico!");
				break;
			case "Paciente":
				mensagem += new FacesMessage(" como paciente!");
				break;
			default:
				break;
		}
		
		msg = new FacesMessage(mensagem);
		
		context.addMessage(null, msg);
		
		return "index";
	}
	
	public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		
        context.getExternalContext().invalidateSession();
		
		msg = new FacesMessage("Logout realizado com sucesso");
		context.addMessage(null, msg);
		
		return "login";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
