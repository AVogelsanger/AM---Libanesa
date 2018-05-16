package com.seiscaju.archangelus.jsf.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.seiscaju.archangelus.jsf.service.MedicoService;
import com.seiscaju.archangelus.jsf.service.PacienteService;
import com.seiscaju.archangelus.jsf.to.PacienteTO;
import com.seiscaju.archangelus.jsf.to.PessoaTO;
import com.seiscaju.archangelus.jsf.to.PlanoSaude;
import com.seiscaju.archangelus.jsf.to.RelatorioTO;

@ManagedBean
public class PacienteBean implements CRUD<PacienteTO, Integer> {

	private PacienteTO paciente;

	private String busca = "";
	
	private PacienteService service;

    FacesContext context;
    PessoaTO user;
	
	@PostConstruct
	private void init() {
		paciente = new PacienteTO();
		paciente.setDataNascimento(Calendar.getInstance());
		
		service = new PacienteService();
		
		context = FacesContext.getCurrentInstance();
		user = (PessoaTO) context.getExternalContext().getSessionMap().get("user");
	}
	
	@Override
	public void excluir(Integer id) {
		FacesMessage msg;

        if (user == null || user.getTipo().equals("Paciente")) {
        	return;
        }
        
		try {
			if (user.getTipo().equals("Medico")) {
				MedicoService medicoService = new MedicoService();
				
				medicoService.desassociar(user.getCodigo(), id);
			} else {
				service.remover(id);
			}
			msg = new FacesMessage("Removido com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro ao remover");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@Override
	public List<PacienteTO> getAll() {
		if (user == null || user.getTipo().equals("Paciente")) {
        	return null;
        }
		
		try {
			if (!busca.isEmpty()) {
				if (user.getTipo().equals("Medico")) {
					return service.buscaPorNomeEMedico(busca, user.getCodigo());
				}
				
				return service.buscaPorNome(busca);
			}
			
			if (user.getTipo().equals("Medico")) {
				return service.listarPorMedico(user.getCodigo());
			}

			return service.listar();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String salvar() {
		FacesMessage msg;
		
		if (user == null || user.getTipo().equals("Paciente")) {
        	return "index";
        }

		if (String.valueOf(paciente.getCpf()).length() < 11) {
			msg = new FacesMessage("O CPF precisa ter 11 digitos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return "paciente";
		}
        
		try {
			if (paciente.getCodigo() == 0) {
				if (user.getTipo().equals("Medico")) {
					service.cadastrar(paciente, user.getCodigo());
				} else {
					service.cadastrar(paciente);
				}
				
				msg = new FacesMessage("Cadastrado com sucesso!");
			} else {
				service.atualizar(paciente);
				msg = new FacesMessage("Atualizado com sucesso!");
			}
	        
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return "paciente";
		} catch (Exception e) {
			e.printStackTrace();
			
			msg = new FacesMessage("Nao foi possivel cadastrar");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
        
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return "paciente";
	}
	
	public List<RelatorioTO> relatorios(int id){
		if (user == null || user.getTipo().equals("Paciente")) {
        	return null;
        }
		
		try {
			return service.listarRelatorios(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String buscar() {
		if (user == null || user.getTipo().equals("Paciente")) {
        	return "index";
        }

		return "pacientes";
	}

	public PacienteTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteTO paciente) {
		this.paciente = paciente;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}
	
    public PlanoSaude[] getPlanos(){
        return PlanoSaude.values();
    }
	
}
