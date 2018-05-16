package com.seiscaju.archangelus.jsf.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.seiscaju.archangelus.jsf.service.MedicoService;
import com.seiscaju.archangelus.jsf.service.PacienteService;
import com.seiscaju.archangelus.jsf.to.MedicoEspecialidade;
import com.seiscaju.archangelus.jsf.to.MedicoTO;
import com.seiscaju.archangelus.jsf.to.PacienteTO;
import com.seiscaju.archangelus.jsf.to.PessoaTO;

@ManagedBean
public class MedicoBean implements CRUD<MedicoTO, Integer> {

	private MedicoTO medico;

	private String busca = "";
	private int associarPacienteId = 0;
	
	private MedicoService service;

    FacesContext context;
    PessoaTO user;
	
	@PostConstruct
	private void init() {
		medico = new MedicoTO();
		medico.setDataNascimento(Calendar.getInstance());
		
		service = new MedicoService();
		
		context = FacesContext.getCurrentInstance();
		user = (PessoaTO) context.getExternalContext().getSessionMap().get("user");
	}
	
	@Override
	public void excluir(Integer id) {
		FacesMessage msg;

        if (user == null || !user.getTipo().equals("Admin")) {
        	return;
        }

		try {
			service.remover(id);
			msg = new FacesMessage("Removido com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro ao remover");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@Override
	public List<MedicoTO> getAll(){
        if (user == null || !user.getTipo().equals("Admin")) {
        	return null;
        }
        
		try {
			if (!busca.isEmpty()) {
				return service.buscaPorNome(busca);
			}

			return service.listar();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String salvar() {
        if (user == null || !user.getTipo().equals("Admin")) {
        	return "index";
        }
        
		FacesMessage msg;
		
		if (String.valueOf(medico.getCpf()).length() < 11) {
			msg = new FacesMessage("O CPF precisa ter 11 digitos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return "medico";
		}
		
		int crmLength = String.valueOf(medico.getCrm()).length();
		
		if (crmLength < 4 || crmLength > 10) {
			msg = new FacesMessage("O CRM precisa ter entre 4 e 10 digitos");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return "medico";
		}

		try {
			if (medico.getCodigo() == 0) {
				service.cadastrar(medico);
				msg = new FacesMessage("Cadastrado com sucesso!");
			} else {
				service.atualizar(medico);
				msg = new FacesMessage("Atualizado com sucesso!");
				
				return "medicos";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Nao foi possivel cadastrar");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}

		return "medico";
	}
	
	public void associarPaciente() {
		if (user == null || !user.getTipo().equals("Medico")) {
        	return;
        }
		
		FacesMessage msg;

		try {
			service.associar(user.getCodigo(), associarPacienteId);
			
			msg = new FacesMessage("Paciente associado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Nao foi possivel cadastrar");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
        
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<PacienteTO> getAllPacientes() {
		if (user == null || user.getTipo().equals("Paciente")) {
        	return null;
        }
		
		try {
			PacienteService pacienteService = new PacienteService();
			
			return pacienteService.listar();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String buscar() {
		if (user == null || !user.getTipo().equals("Admin")) {
        	return "index";
        }

		return "pacientes";
	}

	public MedicoTO getMedico() {
		return medico;
	}

	public void setMedico(MedicoTO medico) {
		this.medico = medico;
	}
	
    public MedicoEspecialidade[] getEspecialidades(){
        return MedicoEspecialidade.values();
    }

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public int getAssociarPacienteId() {
		return associarPacienteId;
	}

	public void setAssociarPacienteId(int associarPacienteId) {
		this.associarPacienteId = associarPacienteId;
	}
	
}
