package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Rol;

@RequestScoped
public class RolDAO extends GenericDAO<Rol> {

	public RolDAO() {
		super(Rol.class);
	}
	
}
