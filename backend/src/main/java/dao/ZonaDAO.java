package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Zona;

@RequestScoped
public class ZonaDAO extends GenericDAO<Zona> {
	
	public ZonaDAO() {
		super(Zona.class);
	}
	
}
