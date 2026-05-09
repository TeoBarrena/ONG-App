package rest;

import java.util.List;

import dao.ZonaDAO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelos.Estado;
import modelos.Usuario;
import modelos.Zona;

@Path("/zonas")
public class ZonaResource extends GenericResource<Zona>{
	
	@Inject ZonaDAO dao;
	
	public ZonaResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	}
	
	@POST
	@Path("/nuevaZona")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody Zona zona, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "CREATE_ZONA", "crear zonas");
			if(checkeo != null)
				return checkeo;		
    		return super.create(zona);    		
    	}
    	catch (PersistenceException e) {
    	    Throwable cause = e.getCause();
    	    if (cause instanceof org.hibernate.exception.ConstraintViolationException hce) {
    	        String constraintName = hce.getConstraintName();
    	        if (constraintName != null) {
    	        	String mensaje = "Ocurrió un conflicto";
    	            return Response.status(Response.Status.CONFLICT).entity(mensaje.trim()).build();
    	        }
    	    }
    	    throw e;
    	} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
    }
}
