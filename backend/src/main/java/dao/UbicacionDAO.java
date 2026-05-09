package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Ubicacion;

@RequestScoped
public class UbicacionDAO extends GenericDAO<Ubicacion> {
	
	public UbicacionDAO() {
		super(Ubicacion.class);
	}

}
