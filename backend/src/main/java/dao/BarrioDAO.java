package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Barrio;
import modelos.Jornada;
import modelos.Ubicacion;
import modelos.Zona;

@RequestScoped
public class BarrioDAO extends GenericDAO<Barrio> {
	
	public BarrioDAO() {
		super(Barrio.class);
	}
	
	@Override
	public Boolean create(Barrio barrio) {
		/*
	    Ubicacion ubicacion = barrio.getCentroGeografico();

	    if (ubicacion != null) {
	        if (ubicacion.getId() != null) {
	            // Si viene con ID, buscamos la Ubicacion existente
	            Ubicacion existente = em.find(Ubicacion.class, ubicacion.getId());
	            barrio.setCentroGeografico(existente);
	        } else {
	            // Si no tiene ID, persistimos la nueva Ubicacion
	            em.persist(ubicacion);
	            barrio.setCentroGeografico(ubicacion);
	        }
	    }
*/
	    return super.create(barrio);
	}
	
}
