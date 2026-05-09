package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Campaña;

@RequestScoped
public class CampañaDAO extends GenericDAO<Campaña> {
	
	public CampañaDAO() {
		super(Campaña.class);
	}
	
}
