package com.seiscaju.archangelus.ws.teste;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.seiscaju.archangelus.ws.dao.PessoaDAO;
import com.seiscaju.archangelus.ws.dao.impl.PessoaDAOImpl;
import com.seiscaju.archangelus.ws.entity.Admin;
import com.seiscaju.archangelus.ws.entity.Medico;
import com.seiscaju.archangelus.ws.entity.MedicoEspecialidade;
import com.seiscaju.archangelus.ws.entity.Paciente;
import com.seiscaju.archangelus.ws.entity.Pessoa;
import com.seiscaju.archangelus.ws.entity.PlanoSaude;
import com.seiscaju.archangelus.ws.exception.CommitException;
import com.seiscaju.archangelus.ws.singleton.EntityManagerFactorySingleton;

public class PopulaBanco {
	public static void main(String[] args) {
		EntityManagerFactory fabrica = 
			EntityManagerFactorySingleton.getInstance();
		EntityManager em = fabrica.createEntityManager();

		PessoaDAO pessoaDAO = new PessoaDAOImpl(em);
		
		Paciente paciente = new Paciente("User Paciente", "paciente@archangelus.com", "paciente", 2345678945L, new GregorianCalendar(1995, Calendar.APRIL, 2), PlanoSaude.OUTRO);
		Medico medico = new Medico("User Medico", "medico@archangelus.com", "medico", 54985645236L, new GregorianCalendar(1970, Calendar.SEPTEMBER, 5), 44555, MedicoEspecialidade.GERAL);
		Admin admin = new Admin("User Admin", "admin@archangelus.com", "admin", 45678915688L, new GregorianCalendar(1997, Calendar.JANUARY, 19));

		pessoaDAO.cadastrar(admin);
		pessoaDAO.cadastrar(medico);
		pessoaDAO.cadastrar(paciente);
		
		try {
			pessoaDAO.commit();
		} catch (CommitException e) {
			e.printStackTrace();
		}
		
		List<Pessoa> pessoas = pessoaDAO.listar();
		
		System.out.println("Lista de pessoas");
		
		for (Pessoa pessoa : pessoas) {
			System.out.println("Nome: " + pessoa.getNome());
		}

		em.close();
		fabrica.close();
	}
}
