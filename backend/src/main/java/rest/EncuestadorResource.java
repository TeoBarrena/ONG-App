package rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.EncuestadorDAO;
import dao.JornadaDAO;
import dto.EncuestadorDTO;
import dto.EncuestadorService;
import dto.EncuestadorUpdateDTO;
import dto.MapperService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelos.Encuestador;
import modelos.Jornada;
import rest.auth.JwtUtil;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;


@Path("/encuestadores")
public class EncuestadorResource extends GenericResource<Encuestador>{

	@Inject
	private EncuestadorDAO dao;
	
	@Inject 
	private EncuestadorDTO dto;
	
	@Inject
	private MapperService mapperService;
	
	@Inject
	private JornadaDAO jornadaDAO;
	
	@Inject
	private JornadaResource jornadaResource;
	
	@Inject
	private EncuestadorService encuestadorService;
	
	public EncuestadorResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	} 
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id")Long id) {
		
		Encuestador encuestador = dao.get(id);
		
		if (encuestador == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("No se encontro un encuestador con ese id").build();
		}
		
		EncuestadorDTO dto = mapperService.toEncuestadorDTO(encuestador); //uso esta lógica para poder pasar al front las jornadas
		
		return Response.ok().entity(dto).build();
	}
	
	
	@POST
	@Path("/nuevoEncuestador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@RequestBody Encuestador encuestador, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "CREATE_ENCUESTADOR", "crear encuestadores");
			if(checkeo != null)
				return checkeo;

    		//se maneja esta lógica porque no se puede añadir encuestadores a jornadas sin que encuestador este en la BD
    		Encuestador encuestadorPersistido = dao.createAndReturn(encuestador);
    		
    		//añadir a las jornadas este encuestador
    		if (encuestadorPersistido.getJornadas() != null) {
    			for (Jornada jornada : encuestadorPersistido.getJornadas()) {
    				jornada.addEncuestador(encuestadorPersistido);
    				jornadaDAO.update(jornada, jornada.getId());
    			}
    		}
    		
    		return Response.status(Response.Status.CREATED).build();
    		
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		}  
	}
	
	
	@DELETE 
	@Path("/deleteEncuestador/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	//ACA ESTA DANDO ERROR PORQUE SE HACE BORRADO FISICO, SI TENEMOS BORRADO LÓGICO, EN JORNADAS SOLO BUSCA POR FILTRO DE NO BORRADO Y NO HAY PROBLEMA
	public Response delete(@PathParam("id")Long id,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "DELETE_ENCUESTADOR", "borrar encuestadores");
			if(checkeo != null)
				return checkeo;
    		return super.borrar(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
	
	
	@PUT
	@Path("/editEncuestador/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editar(@PathParam("id")Long id, Encuestador encuestador, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_ENCUESTADOR", "editar encuestadores");
			if(checkeo != null)
				return checkeo;
			
	        /*EncuestadorDTO encuestadorActualizadoDTO = encuestadorService.actualizarEncuestadorCompleto(id, dto);
	        
	        return Response.ok().entity(encuestadorActualizadoDTO).build();*/
	        
	        Encuestador encuestadorBD = dao.get(id);
	        
	        encuestadorBD.setNombre(encuestador.getNombre());
	        encuestadorBD.setDni(encuestador.getDni());
	        encuestadorBD.setNacimiento(encuestador.getNacimiento());
	        encuestadorBD.setOcupacion(encuestador.getOcupacion());
	        encuestadorBD.setGenero(encuestador.getGenero());
	        
	        
	        encuestadorBD = dao.update(encuestador, id);
	        
	        //aca le paso las jornadas que recibew por parametro (del encuestador que viene)
	        //lo hago asi porque Jornada es la dueña de la tabla intermedia
	        jornadaResource.actualizarJornadasEncuestador(encuestador.getJornadas(), encuestadorBD);
	        
	        return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	        
	         
	}
	
	
}
