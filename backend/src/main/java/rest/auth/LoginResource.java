package rest.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dao.UsuarioDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelos.Permiso;
import modelos.Rol;
import jakarta.ws.rs.core.NewCookie; 
import modelos.Usuario;

@Path("/auth")
public class LoginResource {
	
	@Inject
	private UsuarioDAO dao;
	
	public LoginResource() {
		super();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request) {
		if (dao.checkLogIn(request.email, request.password)) {
			Usuario usuario = dao.get("email", request.email);

			List<String> rolesStr = usuario.getRoles().stream()
					.map(Rol::getNombre)
					.collect(Collectors.toList());

			List<String> permisosStr = new ArrayList<String>(
					usuario.getRoles().stream()
					.map(Rol::getPermisos)
					.flatMap(List::stream)   
					.map(Permiso::getNombre)
					.collect(Collectors.toSet()));
			
/*
			String token = JwtUtil.generateToken(usuario.getEmail(), rolesStr, permisosStr);
			LoginResponse response = new LoginResponse(token, rolesStr, permisosStr, usuario.getAdmin(), usuario.getNombre(), usuario.getId());
			return Response.ok(response).build();*/

			String token = JwtUtil.generateToken(String.valueOf(usuario.getId()), rolesStr, permisosStr);
			//aca se manda el token por JSON quedando vulnerable
			//LoginResponse response = new LoginResponse(token, rolesStr, usuario.getAdmin(), usuario.getNombre(), usuario.getId());
			
			NewCookie cookie = new NewCookie(
					"jwt_token",
					token,
					"/", //indica que esta disponible para toda la app
					null,
					"Session JWT",
					86400,
					true, //atributo Secure
					true //atriubto HttpOnly
					); 
			
			LoginResponse loginResponse = new LoginResponse(rolesStr, permisosStr, usuario.getAdmin(), usuario.getNombre(), usuario.getId());
			
			Response response = Response.ok(loginResponse).build();
			response.getHeaders().add("Set-Cookie", cookie.toString() + "; SameSite=Strict");
			
			return response;
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("Datos inválidos").build();
	}
	
	@GET
	@Path("/check-session")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkSession(@CookieParam("jwt_token") String token) {
		
		if (token == null || token.isEmpty()) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("No hay token de sesión").build();
		}
		
		try {

			List<String> roles = JwtUtil.getRoles(token);
			List<String> permisos = JwtUtil.getPermisos(token);
			String userId = JwtUtil.getUserId(token);
			
			return Response.ok(Map.of("roles", roles, "id", userId, "permisos", permisos)).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Sesión inválida o expirada")
                    .build();
		}
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@CookieParam("jwt_token") String token) {
		NewCookie expiredCookie = new NewCookie(
				"jwt_token",
				null, //aca le saco el valor al token para invalidarlo
				"/",
				null,
				"Session JWT",
				0, //aca seteo el max-age a 0 para que expire automaticamente
				true,
				true
				);
		Response response = Response.ok().cookie(expiredCookie).build();
		return response;
	}

}
