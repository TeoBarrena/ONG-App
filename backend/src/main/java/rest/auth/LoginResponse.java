package rest.auth;

import java.util.List;

public class LoginResponse {

	public List<String> roles;
	public List<String> permisos;
	public Boolean admin;
	public String nombre;
	public Long id;
	

//	public LoginResponse(String token, List<String> roles, List<String> permisos, Boolean admin, String nombre, Long id) {
//		this.token = token;

	public LoginResponse(List<String> roles,List<String> permisos, Boolean admin, String nombre, Long id) {
		this.roles = roles;
		this.permisos = permisos;
		this.admin = admin;
		this.nombre = nombre;
		this.id = id;
	}
}
