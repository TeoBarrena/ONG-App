package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Encuestador;

@RequestScoped
public class EncuestadorDAO extends GenericDAO<Encuestador>{

	public EncuestadorDAO() {
		super(Encuestador.class);
	}
	
}
