package rest;

import java.util.List;

import dao.CampañaDAO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelos.Campaña;
import rest.auth.JwtUtil;

@Path("/campanias")
public class CampañaResource extends GenericResource<Campaña> {
	
	@Inject
	private CampañaDAO dao;
	
	public CampañaResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	}
	 
	
	@POST
	@Path("/nuevaCampania")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@RequestBody Campaña campaña, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "CREATE_CAMPAÑA", "crear campañas");
			if(checkeo != null)
				return checkeo;
			return super.create(campaña);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
    		 
	}
	
	@DELETE
	@Path("/deleteCampania/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Long id,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "DELETE_CAMPAÑA", "borrar campañas");
			if(checkeo != null)
				return checkeo;
    		return super.borrar(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
	
	@PUT
	@Path("/editCampania/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editar(@PathParam("id") Long id, Campaña campaña,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_CAMPAÑA", "editar campañas");
			if(checkeo != null)
				return checkeo;
			
    		Campaña campañaExistente = dao.get(id);
    		
    		if (campañaExistente == null) {
    			return Response.status(Response.Status.NOT_FOUND).entity("Campaña no encontrada").build();
    		}
    		campañaExistente.setNombre(campaña.getNombre());
    		campañaExistente.setInicio(campaña.getInicio());
    		campañaExistente.setFin(campaña.getFin());
    		campañaExistente.setBarrio(campaña.getBarrio());
    		
    		return super.editar(id, campañaExistente);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
	
}
