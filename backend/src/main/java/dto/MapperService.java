package dto;

import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import modelos.Encuestador;
import modelos.Jornada;

@ApplicationScoped
public class MapperService {

	
	public JornadaDTO toJornadaDTO(Jornada jornada) {
		if (jornada == null) {
			return null;
		}
		
		JornadaDTO dto = new JornadaDTO();
		dto.setId(jornada.getId());
		dto.setFecha(jornada.getFecha());
		return dto;
	}
	
	public EncuestadorDTO toEncuestadorDTO(Encuestador encuestador) {
		if (encuestador == null) {
			return null;
		}
		
		EncuestadorDTO dto = new EncuestadorDTO();
		dto.setNombre(encuestador.getNombre());
		dto.setDni(encuestador.getDni());
		dto.setNacimiento(encuestador.getNacimiento());
		dto.setOcupacion(encuestador.getOcupacion());
		dto.setGenero(encuestador.getGenero());
		
		if (encuestador.getJornadas() != null) {
			dto.setJornadas(encuestador.getJornadas().stream().map(this::toJornadaDTO).collect(Collectors.toList()));
		}
		
		return dto;
		
	}
}
