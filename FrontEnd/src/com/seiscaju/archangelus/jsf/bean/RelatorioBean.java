package com.seiscaju.archangelus.jsf.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.seiscaju.archangelus.jsf.service.MedicoService;
import com.seiscaju.archangelus.jsf.service.PacienteService;
import com.seiscaju.archangelus.jsf.service.RelatorioService;
import com.seiscaju.archangelus.jsf.to.PacienteTO;
import com.seiscaju.archangelus.jsf.to.PessoaTO;
import com.seiscaju.archangelus.jsf.to.RelatorioTO;
import com.seiscaju.archangelus.jsf.to.TipoRelatorio;

@ManagedBean
public class RelatorioBean implements CRUD<RelatorioTO, Integer> {

	private RelatorioTO relatorio;
	private PacienteTO paciente;
	
	private String busca = "";
	
	private RelatorioService service;
	private PacienteService pacienteService;
	private MedicoService medicoService;
	
	private FacesContext context;
	private PessoaTO user;
	
	@PostConstruct
	private void init() {
		relatorio = new RelatorioTO();
		paciente = new PacienteTO();
		
		relatorio.setData(Calendar.getInstance());

		service = new RelatorioService();
		pacienteService = new PacienteService();
		medicoService = new MedicoService();
		
		context = FacesContext.getCurrentInstance();
		user = (PessoaTO) context.getExternalContext().getSessionMap().get("user");
	}
	
	@Override
	public void excluir(Integer id) {
		FacesMessage msg;
        
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
	public List<RelatorioTO> getAll() {
		if (user == null) {
			return null;
		}
				
		try {
			if (!busca.isEmpty()) {
				if (user.getTipo().equals("Paciente")) {
					return service.buscaPorNotasEPaciente(busca, user.getCodigo());
				}
				
				if (user.getTipo().equals("Medico")) {
					return service.buscaPorNotasEPacienteDeMedico(busca, user.getCodigo());
				}

				return service.buscaPorNotas(busca);
			}

			if (user.getTipo().equals("Paciente")) {
				return pacienteService.listarRelatorios(user.getCodigo());
			} else if (user.getTipo().equals("Medico")) {
				return medicoService.listarRelatorios(user.getCodigo());
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
		
		try {
			if (relatorio.getCodigo() == 0) {
		        if (user == null || !user.getTipo().equals("Paciente")) {
		        	return "index";
		        }
		        
		        paciente.setCodigo(user.getCodigo());
		        relatorio.setPacienteTO(paciente);
		        
				service.cadastrar(relatorio);
				msg = new FacesMessage("Cadastrado com sucesso!");
			} else {
				service.atualizar(relatorio);
				msg = new FacesMessage("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			msg = new FacesMessage("Nao foi possivel salvar");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return "relatorio";
		}
        
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		return "relatorios";
	}

	public String buscar() {
		if (user == null) {
			return null;
		}

		return "relatorios";
	}

	public RelatorioTO getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioTO relatorio) {
		this.relatorio = relatorio;
	}
	
    public TipoRelatorio[] getTipos(){
        return TipoRelatorio.values();
    }

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public PacienteTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteTO paciente) {
		this.paciente = paciente;
	}
	
}
