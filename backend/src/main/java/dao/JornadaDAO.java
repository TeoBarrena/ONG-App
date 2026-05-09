package dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import modelos.Encuestador;
import modelos.Jornada;

@RequestScoped
public class JornadaDAO extends GenericDAO<Jornada> {
	
	public JornadaDAO() {
		super(Jornada.class);
	}
	
	public void actualizarJornadasEncuestador(List<Jornada> nuevasJornadas, Encuestador encuestadorExistente) {
	    // Paso 1: Eliminar las relaciones existentes
	    for (Jornada j : new ArrayList<>(encuestadorExistente.getJornadas())) {
	        j.getEncuestadores().remove(encuestadorExistente);
	        this.update(j, j.getId());
	    }

	    // Paso 2: Agregar las nuevas relaciones
	    if (nuevasJornadas != null) {
	        for (Jornada nuevaJornada : nuevasJornadas) {
	            Jornada jornadaGestionada = this.get(nuevaJornada.getId());
	            if (jornadaGestionada != null) {
	                jornadaGestionada.getEncuestadores().add(encuestadorExistente);
	                this.update(jornadaGestionada, jornadaGestionada.getId());
	            }
	        }
	    }
	}
}
