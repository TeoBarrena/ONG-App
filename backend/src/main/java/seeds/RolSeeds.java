package seeds;

import dao.GenericDAO;
import dao.PermisoDAO;
import dao.RolDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.Permiso;
import modelos.Rol;

@RequestScoped
public class RolSeeds implements Seed {
	
	@Inject
	private RolDAO dao;
	@Inject
	private PermisoDAO pdao;
	
	public RolSeeds() {}
	
	public void seed() {
		
		Rol rolPersonalDeSalud = new Rol("Personal de Salud");
		Rol rolVoluntariado = new Rol("Voluntariado");
		Rol rolEncuestadores = new Rol("Encuestadores");
		Rol rolVisitantes = new Rol("Visitantes");
		Rol rolOrgSalud = new Rol("Organización de Salud");
		Rol rolOrgSocial = new Rol("Organización Social");
		Rol rolONG = new Rol("Organización sin fines de lucro");
		Rol rolAdmin = new Rol("Admin");
		
		//asignación de permisos de ADMIN
		rolAdmin.addPermiso(pdao.get("nombre", "ADMIN"));
		
		//asignación de permisos de PERSONAL DE SALUD
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "VER_ANALITICAS"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "VER_MAPAS"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_JORNADA"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_DATO_SALUD"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "CREATE_DOCUMENTOPUBLICO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "UPDATE_DOCUMENTOPUBLICO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "CREATE_REPORTE"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "UPDATE_REPORTE"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "DELETE_REPORTE"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_REPORTE"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_BARRIO"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_ZONA"));
		rolPersonalDeSalud.addPermiso(pdao.get("nombre", "GET_UBICACION"));
		
		//asignación de permisos de VOLUNTARIADO
		rolVoluntariado.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO"));
		rolVoluntariado.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolVoluntariado.addPermiso(pdao.get("nombre", "GET_JORNADA"));
		rolVoluntariado.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolVoluntariado.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolVoluntariado.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		//asignación de permisos de ENCUESTADORES
		rolEncuestadores.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO")); 
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_BARRIO"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_ZONA"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_UBICACION"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_JORNADA")); 
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "CREATE_DATO"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "UPDATE_DATO"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "DELETE_DATO"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_DATO_SOCIAL"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_DATO_SALUD"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_DATO_PERSONAL"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolEncuestadores.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		
		//asignación de permisos de VISITANTES
		rolVisitantes.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO")); 
		rolVisitantes.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolVisitantes.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolVisitantes.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		
		//asignación de permisos de ORGANIZACION DE SALUD
		rolOrgSalud.addPermiso(pdao.get("nombre", "VER_ANALITICAS"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_JORNADA"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_DATO_SALUD"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_REPORTE"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolOrgSalud.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		
		//asignación de permisos de ORGANIZACION SOCIAL
		rolOrgSocial.addPermiso(pdao.get("nombre", "VER_ANALITICAS"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_JORNADA"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_DATO_SOCIAL"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_REPORTE"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolOrgSocial.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		
		//asignación de permisos de ONG
		rolONG.addPermiso(pdao.get("nombre", "VER_ANALITICAS"));
		rolONG.addPermiso(pdao.get("nombre", "UPDATESELF_USUARIO"));
		rolONG.addPermiso(pdao.get("nombre", "GET_CAMPAÑA"));
		rolONG.addPermiso(pdao.get("nombre", "GET_JORNADA"));
		rolONG.addPermiso(pdao.get("nombre", "GET_DATO_SOCIAL"));
		rolONG.addPermiso(pdao.get("nombre", "GET_DOCUMENTOPUBLICO"));
		rolONG.addPermiso(pdao.get("nombre", "GET_REPORTE"));
		rolONG.addPermiso(pdao.get("nombre", "GET_ROL"));
		rolONG.addPermiso(pdao.get("nombre", "GET_PERMISO"));
		
		

		dao.create(rolAdmin);
		dao.create(rolPersonalDeSalud);
		dao.create(rolVoluntariado);
		dao.create(rolEncuestadores);
		dao.create(rolVisitantes);
		dao.create(rolOrgSalud);
		dao.create(rolOrgSocial);
		dao.create(rolONG);	
	}
}
