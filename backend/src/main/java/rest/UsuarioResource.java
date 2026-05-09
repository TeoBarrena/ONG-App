package rest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import dao.UsuarioDAO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.DELETE;
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
import modelos.Estado;
import modelos.Usuario;
import rest.auth.JwtUtil;
import rest.auth.UpdatePasswordDTO;

@Path("/usuarios")
public class UsuarioResource extends GenericResource<Usuario> {

	@Inject
	private UsuarioDAO dao;

	public UsuarioResource() {
		super();
	}

	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	}

	@POST
	@Path("/nuevoUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@RequestBody Usuario usuario, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "CREATE_USUARIO", "crear usuarios");
			if (checkeo != null)
				return checkeo;

			usuario.setEstado(Estado.HABILITADO);
			if (Boolean.TRUE.equals(usuario.getAdmin())) {
				usuario.setAdmin(true);
			} else {
				usuario.setAdmin(false);
			}
			return super.create(usuario);
		} catch (PersistenceException e) {
			Throwable cause = e.getCause();
			if (cause instanceof org.hibernate.exception.ConstraintViolationException hce) {
				String constraintName = hce.getConstraintName();
				if (constraintName != null) {
					String mensaje = "";
					if (constraintName.contains("email")) {
						mensaje += "Ese email ya está registrado\n";
					}
					if (constraintName.contains("matricula")) {
						mensaje += "Esa matrícula ya está registrada\n";
					}
					return Response.status(Response.Status.CONFLICT).entity(mensaje.trim()).build();
				}
			}
			throw e;
		} catch (Exception e) {
			// e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).entity("Ya existe un usuario con esa información").build();
		}
	}

	@PATCH
	@Path("/updateContraseña")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateContraseña(UpdatePasswordDTO dto, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATESELF_USUARIO", "cambiar su contraseña");
			if (checkeo != null)
				return checkeo;

			if (dao.checkLogIn(dto.email, dto.password)) {
				if (dao.changePwd(dto.id, dto.passwordNueva, dto.passwordNueva)) {
					return Response.ok().build();
				} else
					return Response.notModified().entity("Ocurrió un error al actualizar la contraseña").build();
			} else
				return Response.status(Response.Status.UNAUTHORIZED).entity("Contraseña actual incorrecta").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Ocurrió un error interno. " + e.getMessage()).build();
		}
	}

	@PATCH
	@Path("/{id}/{estado}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response cambiarEstado(@PathParam("id") Long id, @PathParam("estado") Estado estado,
			@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_USUARIO",
					"cambiar el estado de los usuarios");
			if (checkeo != null)
				return checkeo;

			if (dao.cambiarEstado(id, estado))
				return Response.noContent().build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("No existe el elemento con ese id").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Ocurrió un error interno. " + e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/deleteUser/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Long id, @CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "DELETE_USUARIO", "borrar usuarios");
			if(checkeo != null)
				return checkeo;
    		return super.borrar(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}

	@PUT
	@Path("/updateUsuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editarUsuario(@PathParam("id") Long id, Usuario usuario,@CookieParam("jwt_token") String token) {
		try {
			Response checkeo = super.authPermisoCheckFailure(token, "UPDATE_USUARIO", "editar usuarios");
			if(checkeo != null)
				return checkeo; 
			return super.editar(id, usuario);
		}  catch (PersistenceException e) {
			Throwable cause = e.getCause();
			if (cause instanceof org.hibernate.exception.ConstraintViolationException hce) {
				String constraintName = hce.getConstraintName();
				if (constraintName != null) {
					String mensaje = "";
					if (constraintName.contains("email")) {
						mensaje += "Ese email ya está registrado\n";
					}
					if (constraintName.contains("matricula")) {
						mensaje += "Esa matrícula ya está registrada\n";
					}
					return Response.status(Response.Status.CONFLICT).entity(mensaje.trim()).build();
				}
			}
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un error interno. " + e.getMessage()).build();
		} 
	}
}
