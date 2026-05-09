package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Permiso;

@RequestScoped
public class PermisoDAO extends GenericDAO<Permiso> {
	
	public PermisoDAO() {
		super(Permiso.class);
	}
	
}
