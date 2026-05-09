package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Dato;

@RequestScoped
public class DatoDAO extends GenericDAO<Dato>{
	
	public DatoDAO() {
		super(Dato.class);
	}

}
