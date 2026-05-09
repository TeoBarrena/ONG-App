package rest;

import dao.GenericDAO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import rest.auth.JwtUtil;

import java.io.StringWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequestScoped
public abstract class GenericResource<T> {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	protected GenericDAO<T> dao;

	private String mensaje;

	public GenericResource(GenericDAO<T> dao) {
		this.dao = dao;
	}

	public GenericResource() {

	}

	public void setDAO(GenericDAO<T> dao) {
		this.dao = dao;
	}
	
	
	protected List<String> obtenerLista(String token, String claveLista) throws Exception{
		// USO ADECUADO: en clave lista, ingresar el nombre de la clave de la lista que se quiere extraer del token.
		// Ejemplos actuales: "roles", "permisos",
		Claims claims = JwtUtil.validateToken(token);
		Object claveClaim = claims.get(claveLista);
		List<String> lista;
		
		if (claveClaim instanceof List<?>) {
			lista = ((List<?>) claveClaim).stream()
					.map(Object::toString)
					.toList();
		} else {
			lista = List.of();
		}
		return lista;
    }
 
	protected Response authRolCheckFailure(String token, String rol, String mensaje) throws Exception{
		// EN CASO DE RETORNAR NULL, QUIERE DECIR QUE NO HUBO FALLOS. 

		if (token == null || token.isEmpty()) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
    		
		List<String> roles = JwtUtil.getRoles(token);

        if (!(roles.contains("Admin") || roles.contains(rol))) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("No posee el rol para "+ mensaje).build();
        }
        
        return null;
	}
	
	protected Response authRolCheckFailure(String token, String permiso) throws Exception{
		return authRolCheckFailure(token,permiso,"realizar esta accion");
	}
	

	protected Response authPermisoCheckFailure(String token, String permiso, String mensaje) throws Exception{
		// EN CASO DE RETORNAR NULL, QUIERE DECIR QUE NO HUBO FALLOS.

		if (token == null || token.isEmpty()) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("No tiene jwt token. Inicie sesión nuevamente.").build();
		}
    	
        List<String> permisos = JwtUtil.getPermisos(token);
        
        if (!(permisos.contains("ADMIN") || permisos.contains(permiso))) {
            return Response.status(Response.Status.FORBIDDEN).entity("No posee permisos para "+ mensaje).build();
        }
        
        return null;
	}
	

	protected Response authPermisoCheckFailure(String token, String permiso) throws Exception{
		return authPermisoCheckFailure(token,permiso,"realizar esta accion");
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Este endpoint permite obtener un listado", description = "Permite obtener listado")
	public List<T> getList() {
		var listaDe = dao.getList();
		return listaDe;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Este endpoint permite filtrar por el id", description = "Permite filtrar por el id al ingresar el mismo mediante URL")
	public Response get(@PathParam("id") Long id) {
		T elemento = dao.get(id);
		if (elemento != null) {
			return Response.ok().entity(elemento).build();
		} else {
			mensaje = ("No se encontró el elemento.");
			return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Este endpoint nos permite crear un objeto, toma como body un JSON y retorna el mismo JSON pero con un ID de creacion", requestBody = @RequestBody(description = "Un nuevo objeto en formato JSON", required = true, content = @Content(mediaType = "application/json", examples = {
			@ExampleObject(name = "Usuario Personal de Salud ejemplo", summary = "Personal de Salud", value = """
					  {
					  "nombre": "Juan",
					  "apellido": "Pérez",
					  "password": "password123",
					  "email": "personalSalud@gmail.com",
					  "matricula": 1
					}
					"""),
			@ExampleObject(name = "Miembro Organizacion Social ejemplo", summary = "Miembro Organizacion Social", value = """
					{
						"nombre": "Juan",
					    "apellido": "Pérez",
					       "password": "password123",
					       "email": "personalSalud@gmail.com",
					       "organizacion": {
						   "id": 67
					       }
					}
							"""),
			@ExampleObject(name = "Miembro Personal de Salud y de Organizacion Social ejemplo", summary = "Miembro Personal de Salud y de Organizacion Social", value = """
					{
						"nombre": "Juan",
					    "apellido": "Pérez",
					       "password": "password123",
					       "email": "personalSalud@gmail.com",
					       "matricula": 887,
					       "organizacion": {
						   "id": 67
					       }
					}
							"""),
			@ExampleObject(name = "Encuestador", summary = "Encuestador", value = """
					{
						"nombre":"Pepe",
						"dni": "4435",
						"genero": "Masculino",
						"ocupacion": "Estudiante",
						"nacimiento": "1990-05-12"
					}
							"""), @ExampleObject(name = "Barrio", summary = "Barrio", value = """
					{
						"nombre": "Meridiano V",
						"descripcion": "Barrio de la zona de 17 y 71",
						"centroGeografico": {
						    "latitud": "189.23.43",
						    "longitud": "394.32.12"
						}
					}
							"""),
			@ExampleObject(name = "Zona", summary = "Ejemplo de una zona con barrio, centro geográfico y área", value = """
					{
					  "nombre": "nombre-zona",
					  "centroGeografico": {
					    "latitud": "239.32",
					    "longitud": "234.23"
					  },
					  "barrio": {
					    "id": 90
					  },
					  "area": {
					    "puntos": [
					      {
					        "latitud": "-34.6037",
					        "longitud": "-58.3816"
					      },
					      {
					        "latitud": "-34.6048",
					        "longitud": "-58.3827"
					      }
					    ]
					  }
					}
					"""),
			@ExampleObject(name = "Campaña", summary = "Campaña sin jornadas", value = """
					{
					  "nombre": "Nombre-campaña",
					  "barrio": {"id": 106},
					  "inicio": "2025-07-23",
					  "fin": "2025-08-24"
					}
							"""), @ExampleObject(name = "Jornada", summary = "Jornada", value = """
					{
						"fecha": "2025-07-23",
						"campaña": {"id": 122},
						"encuestadores": [{"id":87},{"id":125}],
						"zonas": [{"id":111},{"id":116}]
					}
							""") })))
	public Response create(@RequestBody T elemento) throws PersistenceException {
		try {
			if (dao.create(elemento))
				return Response.status(Response.Status.CREATED).build();
			else
				return Response.status(Response.Status.CONFLICT).entity("Ya existe un elemento con esa información")
						.build();

		} catch (Exception e) {
			throw e;
		}
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Este endpoint nos permite actualizar un objeto, toma como body un JSON. No retorna nada, esperar el código 204 (No Content)", requestBody = @RequestBody(description = "Un objeto actualizado (formato JSON)", required = true, content = @Content(mediaType = "application/json", examples = {
			@ExampleObject(name = "Usuario para actualizar", summary = "Un usuario existente que se va a actualizar", value = "{\n"
					+ "  \"apellido\": \"Gomez\",\r\n" + "    \"email\": \"ana@example.com\",\r\n"
					+ "    \"estado\": \"PENDIENTE\",\r\n" + "    \"id\": 72,\r\n" + "    \"matricula\": 654321,\r\n"
					+ "    \"nombre\": \"Ana\",\r\n" + "    \"reportes\": [],\r\n" + "    \"roles\": [] " + "}"),
			@ExampleObject(name = "Encuestador para actualizar", summary = "Encuestador para actualizar", value = """
					{
						"nombre": "Nombre-actualizado",
						"dni": "333",
						"ocupacion": "alguna-ocupacion",
						"id": 86,
						"jornadas": [],
						"genero": "Masculino",
						"nacimiento": "1990-03-20"
					}
						""") })), responses = {
					@ApiResponse(responseCode = "204", description = "Objeto actualizado correctamente"),
					@ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el JSON no es válido"), })
	public Response editar(@PathParam("id") Long id, T elemento) {
		try {
			T actualizado = dao.update(elemento, id);
			if (actualizado == null)
				return Response.status(Response.Status.NOT_FOUND).entity("[]").build();
			return Response.ok(actualizado).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(description = "Este endpoint nos permite eliminar un objeto, toma como parámetro por URL un ID de objeto, este endpoint no retorna nada, esperar el código 204 (No Content)", parameters = @Parameter(name = "id", description = "ID del objeto a eliminar"))
	public Response borrar(@PathParam("id") Long id) {
		try {
			if (dao.delete(id))
				return Response.noContent().build();
			else {
				mensaje = "No existe el elemento con ese id";
				return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
			}
		}
		catch (Exception e) {
		    // Convertir stacktrace a string sin PrintWriter
		    StringBuilder sb = new StringBuilder();
		    sb.append(e.toString()).append("\n"); // mensaje de la excepción
		    for (StackTraceElement elem : e.getStackTrace()) {
		        sb.append(elem.toString()).append("\n");
		    }

		    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		                   .entity(sb.toString())
		                   .type(MediaType.TEXT_PLAIN)
		                   .build();
		

		}

	}
}
