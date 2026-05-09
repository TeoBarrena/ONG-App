package dao;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordHelper {
	
	@ApplicationScoped
	private static final String pepper = "Grupo8/Teo/Felix/Prof:Fran";
	
	public PasswordHelper() {}
	
	// Hash a password for the first time
	// gensalt's log_rounds parameter determines the complexity
	// the work factor is 2**log_rounds, and the default is 10
	// Se agrega salt and pepper para mejorar el hash. Pepper es un valor secreto y global a la aplicación.
	public String hash(String string) {
		return BCrypt.hashpw(string, BCrypt.gensalt() + pepper);
	}

	// Check that an unencrypted password matches one that has
	// previously been hashed
	public Boolean check(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}
}
