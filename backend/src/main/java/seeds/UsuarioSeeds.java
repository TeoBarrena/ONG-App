package seeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.OrganizacionSocialDAO;
import dao.RolDAO;
import dao.UsuarioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.Estado;
import modelos.OrganizacionSocial;
import modelos.Reporte;
import modelos.Rol;
import modelos.Usuario;

@RequestScoped
public class UsuarioSeeds implements Seed{
	
	@Inject
	private UsuarioDAO dao;
	
	@Inject
	private OrganizacionSocialDAO orgDao;

	@Inject
	private RolDAO rolDao;
	
	public UsuarioSeeds() {}
	
	public void seed() {
		//Este método debe ser llamado únicamente luego de haber creado por lo menos 2 organizaciones sociales en la base de datos.
		//Este metodo crea 6 usuarios en base de datos, de los cuales hay 1 PENDIENTE, 1 DESHABILITADO y 4 HABILITADO
		OrganizacionSocial org1 = orgDao.get("nombre", "Prosecretaría de Salud de la UNLP");
		OrganizacionSocial org2 = orgDao.get("nombre", "Hogar Padre Cajade");

        dao.create(new Usuario("Ana", "Gomez", "abcd", "ana@example.com", 654321)); 					//Usuario Personal de Salud
        dao.cambiarEstado((dao.get("searchByEmail", Map.of("email", "ana@example.com")).getId()), Estado.HABILITADO);
        var u1 = dao.get("searchByEmail", Map.of("email", "ana@example.com"));
        u1.addRol(rolDao.get("nombre", "Personal de Salud"));
        u1.addRol(rolDao.get("nombre", "Encuestadores"));
        dao.update(u1, u1.getId());																		//prueba de asignacion de roles posterior a la creación
        
        dao.create(new Usuario("Carlos", "Lopez", "pass", "admin@example.com", true)); //Usuario admin		
        var uAdmin1 = dao.get("searchByEmail", Map.of("email", "admin@example.com"));
        uAdmin1.addRol(rolDao.get("nombre", "Admin"));
        dao.update(uAdmin1, uAdmin1.getId());
        
        dao.create(new Usuario("Lucía", "Martinez", "pw1", "lucia@example.com", org1));					//Usuario Org. Social (crear antes)
        dao.cambiarEstado((dao.get("searchByEmail", Map.of("email", "lucia@example.com")).getId()), Estado.HABILITADO);
        
        dao.create(new Usuario("Marcos", "Martinez", "pw1", "marcos@example.com", 898999));				//Usuario Personal de Salud (QUEDA COMO PENDIENTE)
        //Queda como Estado.PENDIENTE
        
        var u2 = new Usuario("Gustavo", "Gasualdi", "abcd", "gus@example.com", 599221);					//prueba de asignacion de roles anterior a la creación
        u2.addRol(rolDao.get("nombre", "Personal de Salud"));
        dao.create(u2); 																				//Usuario Personal de Salud (QUEDA COMO DESHABILITADO)
        dao.cambiarEstado((dao.get("searchByEmail", Map.of("email", "gus@example.com")).getId()), Estado.DESHABILITADO);

        dao.create(new Usuario("Sofía", "Fernandez", "pw2", "sofia@example.com", 111222));				//Usuario Org.Social Y Personal de salud
        var u = dao.get("searchByEmail", Map.of("email", "sofia@example.com"));
        u.setEstado(Estado.HABILITADO);
        u.setOrganizacion(org2);
        u.addRoles(List.of(rolDao.get("nombre", "Personal de Salud"),rolDao.get("nombre", "Visitantes")));
        dao.update(u, u.getId());


        dao.create(new Usuario("Voluntario", "Ejemplo", "1234", "voluntario@example.com", 124222));				//Usuario Voluntariado
        u = dao.get("searchByEmail", Map.of("email", "voluntario@example.com"));
        u.setEstado(Estado.HABILITADO);
        u.addRoles(List.of(rolDao.get("nombre", "Voluntariado")));
        dao.update(u, u.getId());
        

        dao.create(new Usuario("Encuestador", "Ejemplo", "1234", "encuestador@example.com", 111922));				//Usuario Voluntariado
        u = dao.get("searchByEmail", Map.of("email", "encuestador@example.com"));
        u.setEstado(Estado.HABILITADO);
        u.addRoles(List.of(rolDao.get("nombre", "Encuestadores")));
        dao.update(u, u.getId());
        

        dao.create(new Usuario("Encuestador", "Ejemplo", "1234", "visitante@example.com", 117922));				//Usuario Voluntariado
        u = dao.get("searchByEmail", Map.of("email", "visitante@example.com"));
        u.setEstado(Estado.HABILITADO);
        u.addRoles(List.of(rolDao.get("nombre", "Visitantes")));
        dao.update(u, u.getId());


	}
}
