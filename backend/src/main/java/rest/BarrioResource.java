package rest;

import java.util.List;

import dao.BarrioDAO;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelos.Barrio;
import rest.auth.JwtUtil;

@Path("/barrios")
public class BarrioResource extends GenericResource<Barrio>{

	@Inject
	private BarrioDAO dao;
	
	public BarrioResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	}
 

	
	@DELETE
	@Path("/deleteBarrio/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Long id,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "DELETE_BARRIO", "eliminar barrios");
			if(checkeo != null)
				return checkeo;
			return super.borrar(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		}
		
	}
	
	@PUT
	@Path("/updateBarrio/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editar(@PathParam("id") Long id, Barrio barrio, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_BARRIO", "actualizar barrios");
			if(checkeo != null)
				return checkeo;	    	
	    	
	        // 1. Traer el barrio original desde DB
	        Barrio barrioExistente = dao.get(id);

	        if (barrioExistente == null) {
	            return Response.status(Response.Status.NOT_FOUND).entity("Barrio no encontrado").build();
	        }

	        // 2. Actualizar solo los campos que vienen del frontend
	        barrioExistente.setNombre(barrio.getNombre());
	        barrioExistente.setDescripcion(barrio.getDescripcion());
	        barrioExistente.setCentroGeografico(barrio.getCentroGeografico());

	        // 3. NO tocar zonas, las mantenemos igual
	        
	        // 4. Guardar cambios
	        return super.editar(id, barrioExistente);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		}
	}

	
}
