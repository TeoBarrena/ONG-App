package dao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import modelos.Estado;
import modelos.OrganizacionSocial;
import modelos.Rol;
import modelos.Usuario;

@RequestScoped
public class UsuarioDAO extends GenericDAO<Usuario> {

	@Inject
	PasswordHelper passwordHelper;

	@Inject
	private RolDAO rolDao;

	public UsuarioDAO() {
		super(Usuario.class);
	}

	private Boolean validar(Usuario source, Usuario target) {
		// Validar cada campo de source que tenga lógica adicional
		// Esto debe ser movido a un filtro
		try {
			return source.getNombre() != null && source.getApellido() != null && source.getEmail() != null
					&& source.getEstado() != null;
		} catch (Exception e) {
			System.out.print("AAAAAAAAAAAA");
			throw e;
		}

		// por ahora queda así hasta poder enviar mensajes de error
	}

	@Override
	public Usuario update(Usuario source, Long id) {
		try {
			Usuario target = this.em.find(this.entityClass, id);
			if (target != null && validar(source, target)) {
				/*
				if (!source.getEmail().equals(target.getEmail())) {
					Usuario usuarioConMismoEmail = super.get("email", source.getEmail());
					if (usuarioConMismoEmail != null) {
						return null;
					}
				}

				if (source.getMatricula() != null) {
					if (!source.getMatricula().equals(target.getMatricula())) {
						Usuario usuarioConMismaMatricula = super.get("matricula", source.getMatricula());
						if (usuarioConMismaMatricula != null) {
							return null;
						}
					}

				}
				*/

				// source.setEstado(target.getEstado());
				source.setPassword(target.getPassword());
				target = super.update(source, id);
			}
			return target;
		} catch (Exception e) {
			throw e;
		}
	}

	public Boolean checkLogIn(String email, String password) {
		try {
			Usuario usuario = super.get("email", email);
			if (usuario.getEstado() != Estado.HABILITADO) {
				return false;
			}
			return passwordHelper.check(password, usuario.getPassword());
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean cambiarEstado(Long id, Estado estado) {
		EntityTransaction transaction = this.em.getTransaction();
		try {
			Usuario usuario = super.get(id);
			if (usuario != null) {
				transaction.begin();
				usuario.setEstado(estado);
				transaction.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			return false;
		}
	}

	public Boolean changePwd(Long id, String password, String password2) {
		EntityTransaction transaction = this.em.getTransaction();
		try {
			Usuario usuario = super.get(id);
			if (usuario != null && password.equals(password2)) {
				transaction.begin();
				usuario.setPassword(passwordHelper.hash(password));
				transaction.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			return false;
		}
	}
/*
	@Override
	public List<Usuario> getList() {
	    try {
	        TypedQuery<Usuario> q = em.createQuery(
	            "SELECT DISTINCT u FROM Usuario u " +
	            "LEFT JOIN FETCH u.organizacion " +
	            "LEFT JOIN FETCH u.roles", 
	            Usuario.class
	        );
	        return q.getResultList();
	    } finally {
	        // em.close(); si lo abrís manualmente
	    }
	}
*/
	@Override
	public Boolean create(Usuario source) {
		// AGREGAR LOGIGCA DE SETEAR ROLES SEGUN SI VIENE MATRICULA, ORG, AMBAS O ADMIN
		if (source.getPassword() != null) {
			source.setPassword(passwordHelper.hash(source.getPassword()));
		}

		// lógica para agregar rol si pertenece a una organizacion de salud
		if (source.getOrganizacion() != null && source.getOrganizacion().getId() != null) {
			OrganizacionSocial orgExistente = em.find(OrganizacionSocial.class, source.getOrganizacion().getId());
			source.setOrganizacion(orgExistente);
			if (orgExistente.getRol() != null) {
				source.addRol(orgExistente.getRol());
			}
		}

		// lógica para agregar rol si pertenece al Personal de Salud
		if (source.getMatricula() != null && source.getMatricula() > 0) {
			Rol rolSalud = rolDao.get("nombre", "Personal de Salud");
			if (rolSalud != null) {
				source.addRol(rolSalud);
			}
		}

		// lógica para agregar rol de Admin si es un administrador
		if (Boolean.TRUE.equals(source.getAdmin())) {
			Rol rolAdmin = rolDao.get("nombre", "Admin");
			if (rolAdmin != null) {
				source.addRol(rolAdmin);
			}
		} else {
			source.setAdmin(false);
		}

		return super.create(source);
	}

}
