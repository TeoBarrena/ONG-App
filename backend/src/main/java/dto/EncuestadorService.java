package dto;

import java.util.List;

import dao.EncuestadorDAO;
import dao.JornadaDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import modelos.Encuestador;
import modelos.Jornada;

@ApplicationScoped
public class EncuestadorService {

    @Inject
    private EncuestadorDAO encuestadorDAO;

    @Inject
    private JornadaDAO jornadaDAO;
    
    @Inject
    private MapperService mapperService;

    public EncuestadorDTO actualizarEncuestadorCompleto(Long encuestadorId, EncuestadorUpdateDTO dto) {
        // 1. Obtener el encuestador que vamos a modificar.
        // Al estar dentro de un método @Transactional, 'encuestador' es una entidad gestionada (managed).
        Encuestador encuestador = encuestadorDAO.get(encuestadorId);
        if (encuestador == null) {
            throw new WebApplicationException("Encuestador no encontrado", Response.Status.NOT_FOUND);
        }

        // 2. Actualizar los campos básicos
        encuestador.setNombre(dto.getNombre());
        encuestador.setDni(dto.getDni());
        encuestador.setNacimiento(dto.getNacimiento());
        encuestador.setOcupacion(dto.getOcupacion());
        encuestador.setGenero(dto.getGenero());

        // 3. Sincronizar la relación Many-to-Many (la forma correcta)
        
        // Primero, removemos este encuestador de las jornadas en las que ya no participa.
        // Iteramos sobre una copia para evitar ConcurrentModificationException
        for (Jornada jornadaActual : List.copyOf(encuestador.getJornadas())) {
            if (!dto.getJornadasIds().contains(jornadaActual.getId())) {
                jornadaActual.getEncuestadores().remove(encuestador);
            }
        }
        
        // Limpiamos la lista del lado del encuestador (el lado inverso)
        encuestador.getJornadas().clear();

        // Segundo, agregamos el encuestador a las nuevas jornadas.
        if (dto.getJornadasIds() != null && !dto.getJornadasIds().isEmpty()) {
            for (Long jornadaId : dto.getJornadasIds()) {
                Jornada jornada = jornadaDAO.get(jornadaId);
                if (jornada != null) {
                    // Sincronizamos ambos lados de la relación
                    encuestador.getJornadas().add(jornada); // Lado inverso
                    jornada.getEncuestadores().add(encuestador); // Lado dueño (¡esto es lo que actualiza la tabla intermedia!)
                    jornadaDAO.update(jornada, jornada.getId());
                }
            }
        }

        // 4. Persistir los cambios.
        // Con @Transactional, no necesitas llamar a 'update' o 'merge' manualmente.
        // Cuando el método termine, la transacción se confirmará (commit) y JPA/Hibernate
        // guardará todos los cambios detectados en las entidades gestionadas.
        
        // encuestadorDAO.update(encuestador); // Esta línea probablemente ya no sea necesaria.

        // Mapeamos a DTO para la respuesta
        encuestadorDAO.update(encuestador, encuestador.getId());
        
        return mapperService.toEncuestadorDTO(encuestador); 
    }
}