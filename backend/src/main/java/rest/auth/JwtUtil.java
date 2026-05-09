package rest.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.List;
import javax.crypto.*;


public class JwtUtil {

	private static final String SECRET_STRING = "jyaa_grupo8_jyaa_grupo8_jyaa_grupo8";
	private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
	private static final long EXPIRATION_TIME = 86400000;
	
	//public static String generateToken(String email, List<String> roles,  List<String> permisos) {

	public static String generateToken(String id, List<String> roles, List<String> permisos) {

		return Jwts.builder()
				.setSubject(id)
				.claim("roles", roles)
				.claim("permisos", permisos)
				.setIssuedAt(new Date())
				//.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //SIN EXPIRACION
				.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public static Claims validateToken(String token) throws Exception {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	
	private static List<String> obtenerLista(String token, String claveLista) throws Exception{
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
	
	public static List<String> getRoles(String token) throws Exception{
		return obtenerLista(token,"roles");
	}
	
	public static List<String> getPermisos(String token) throws Exception{
		return obtenerLista(token,"permisos");
	}

	public static String getUserId(String token){
		Claims claims;
		try {
			claims = validateToken(token);
			
			String sub = claims.getSubject();
			
			return sub;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
