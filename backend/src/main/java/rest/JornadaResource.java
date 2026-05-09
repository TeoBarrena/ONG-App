package rest;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

import dao.EncuestadorDAO;
import dao.JornadaDAO;
import dto.JornadaDTO;
import dto.MapperService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelos.Encuestador;
import modelos.Jornada;
import modelos.Zona;
import rest.auth.JwtUtil;

@Path("/jornadas")
public class JornadaResource extends GenericResource<Jornada>{

	@Inject
	private JornadaDAO dao;
	
	@Inject
	private EncuestadorDAO encuestadorDAO;
	
	@Inject
	private MapperService mapperService;
	
	public JornadaResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	} 
	
	@POST
	@Path("/nuevaJornada")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@RequestBody Jornada jornada,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "CREATE_JORNADA", "crear jornadas");
			if(checkeo != null)
				return checkeo;
			return super.create(jornada);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		}  
	}
	
	@GET
	@Path("/jornadasDTO")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJornadasToJornadaDTO() {
		
		var l = dao.getList();
		
		List<JornadaDTO> jornadas = new ArrayList<JornadaDTO>();
		
		l.forEach(j -> jornadas.add(mapperService.toJornadaDTO(j)));
		
		return Response.ok().entity(jornadas).build();
		
	}
	
	
	@PUT
	@Path("/editJornada/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editar(@PathParam("id")Long id, Jornada jornada, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_JORNADA", "editar jornadas");
			if(checkeo != null)
				return checkeo;
    		Jornada jornadaExistente = dao.get(id);
    		
    		if (jornadaExistente == null) {
    			return Response.status(Response.Status.NOT_FOUND).entity("Jornada no encontrada").build();
    		}
    		
    		jornadaExistente.setCampaña(jornada.getCampaña());
    		jornadaExistente.setFecha(jornada.getFecha());
    		jornadaExistente.getEncuestadores().forEach(e -> jornadaExistente.removeEncuestador(e));
    		jornadaExistente.getZonas().forEach(z -> jornadaExistente.removeZona(z));
    		
    		for (Encuestador encuestador : jornada.getEncuestadores()) {
    			jornadaExistente.addEncuestador(encuestador);
    		}
    		
    		for (Zona zona : jornada.getZonas()) {
    			jornadaExistente.addZona(zona);
    		}
    		
    		return super.editar(id, jornadaExistente);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
	
	public void actualizarJornadasEncuestador(List<Jornada> jornadas, Encuestador encuestador) {
		
		Encuestador encuestadorBD = encuestadorDAO.get(encuestador.getId());
		
		for (Jornada jornada : encuestadorBD.getJornadas()) {
			jornada.removeEncuestador(encuestadorBD);;
			super.editar(jornada.getId(), jornada);
		}
		
		for (Jornada jornada : jornadas) {
			Jornada jornadaBD = dao.get(jornada.getId());
			if (jornadaBD != null) {
				jornadaBD.addEncuestador(encuestadorBD);
				super.editar(jornadaBD.getId(), jornadaBD);
			}
		}
		
	}
	
	@DELETE
	@Path("/deleteJornada/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id")Long id,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "DELETE_JORNADA", "borrar jornadas");
			if(checkeo != null)
				return checkeo;
    		return super.borrar(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
	
	
}
