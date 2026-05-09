package seeds;

import dao.PermisoDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.Permiso;

public class PermisoSeeds implements Seed {

	@Inject
	private PermisoDAO dao;

	public PermisoSeeds() {}

	public void seed() {



		// Ubicacion
		
		dao.create(new Permiso("CREATE_UBICACION", "Crear una nueva Ubicación"));
		dao.create(new Permiso("GET_UBICACION", "Ver detalles de una Ubicación"));
		dao.create(new Permiso("UPDATE_UBICACION", "Actualizar una Ubicación"));
		dao.create(new Permiso("DELETE_UBICACION", "Eliminar una Ubicación"));
		
		dao.create(new Permiso("GESTION_UBICACION", "Crear una nueva Ubicación"));
		
		// Area
		dao.create(new Permiso("CREATE_AREA", "Crear una nueva Área"));
		dao.create(new Permiso("GET_AREA", "Obtener información de una Área"));
		dao.create(new Permiso("UPDATE_AREA", "Actualizar una Área existente"));
		dao.create(new Permiso("DELETE_AREA", "Eliminar una Área"));
		
		// Zona
		dao.create(new Permiso("CREATE_ZONA", "Crear una nueva Zona"));
		dao.create(new Permiso("GET_ZONA", "Ver información de una Zona"));
		dao.create(new Permiso("UPDATE_ZONA", "Actualizar una Zona existente"));
		dao.create(new Permiso("DELETE_ZONA", "Eliminar una Zona"));
		
		// Barrio
		dao.create(new Permiso("CREATE_BARRIO", "Crear un nuevo Barrio"));
		dao.create(new Permiso("GET_BARRIO", "Obtener información de un Barrio"));
		dao.create(new Permiso("UPDATE_BARRIO", "Actualizar un Barrio existente"));
		dao.create(new Permiso("DELETE_BARRIO", "Eliminar un Barrio"));
		
		// Jornada
		dao.create(new Permiso("CREATE_JORNADA", "Crear una nueva Jornada"));
		dao.create(new Permiso("GET_JORNADA", "Ver información de una Jornada"));
		dao.create(new Permiso("UPDATE_JORNADA", "Actualizar una Jornada"));
		dao.create(new Permiso("DELETE_JORNADA", "Eliminar una Jornada"));
		
		// Campaña
		dao.create(new Permiso("CREATE_CAMPAÑA", "Crear una nueva Campaña"));
		dao.create(new Permiso("GET_CAMPAÑA", "Obtener información de una Campaña"));
		dao.create(new Permiso("UPDATE_CAMPAÑA", "Actualizar una Campaña existente"));
		dao.create(new Permiso("DELETE_CAMPAÑA", "Eliminar una Campaña"));

		// Dato
		dao.create(new Permiso("CREATE_DATO", "Crear un nuevo Dato"));
		dao.create(new Permiso("GET_DATO_SOCIAL", "Obtener información de un Dato de tipo social"));
		dao.create(new Permiso("GET_DATO_SALUD", "Obtener información de un Dato de tipo social"));
		dao.create(new Permiso("GET_DATO_PERSONAL", "Obtener información de un Dato de tipo social"));
		dao.create(new Permiso("UPDATE_DATO", "Actualizar un Dato existente"));
		dao.create(new Permiso("DELETE_DATO", "Eliminar un Dato"));

		// DocumentoPublico
		dao.create(new Permiso("CREATE_DOCUMENTOPUBLICO", "Crear un nuevo Documento Público"));
		dao.create(new Permiso("GET_DOCUMENTOPUBLICO", "Obtener un Documento Público"));
		dao.create(new Permiso("UPDATE_DOCUMENTOPUBLICO", "Actualizar un Documento Público"));
		dao.create(new Permiso("DELETE_DOCUMENTOPUBLICO", "Eliminar un Documento Público"));

		// Encuestador
		dao.create(new Permiso("CREATE_ENCUESTADOR", "Registrar un nuevo Encuestador"));
		dao.create(new Permiso("GET_ENCUESTADOR", "Ver datos de un Encuestador"));
		dao.create(new Permiso("UPDATE_ENCUESTADOR", "Modificar un Encuestador existente"));
		dao.create(new Permiso("DELETE_ENCUESTADOR", "Eliminar un Encuestador"));

		// OrganizacionSocial
		dao.create(new Permiso("CREATE_ORGANIZACIONSOCIAL", "Registrar una nueva Organización Social"));
		dao.create(new Permiso("GET_ORGANIZACIONSOCIAL", "Ver información de una Organización Social"));
		dao.create(new Permiso("UPDATE_ORGANIZACIONSOCIAL", "Actualizar una Organización Social"));
		dao.create(new Permiso("DELETE_ORGANIZACIONSOCIAL", "Eliminar una Organización Social"));

		// Permiso
		dao.create(new Permiso("CREATE_PERMISO", "Crear un nuevo Permiso"));
		dao.create(new Permiso("GET_PERMISO", "Ver información de un Permiso"));
		dao.create(new Permiso("UPDATE_PERMISO", "Modificar un Permiso"));
		dao.create(new Permiso("DELETE_PERMISO", "Eliminar un Permiso"));

		// Reporte
		dao.create(new Permiso("CREATE_REPORTE", "Crear un nuevo Reporte"));
		dao.create(new Permiso("GET_REPORTE", "Ver un Reporte"));
		dao.create(new Permiso("UPDATE_REPORTE", "Actualizar un Reporte"));
		dao.create(new Permiso("DELETE_REPORTE", "Eliminar un Reporte"));

		// Rol
		dao.create(new Permiso("CREATE_ROL", "Crear un nuevo Rol"));
		dao.create(new Permiso("GET_ROL", "Ver información de un Rol"));
		dao.create(new Permiso("UPDATE_ROL", "Actualizar un Rol"));
		dao.create(new Permiso("DELETE_ROL", "Eliminar un Rol"));


		// Usuario
		dao.create(new Permiso("CREATE_USUARIO", "Registrar un nuevo Usuario"));
		dao.create(new Permiso("GET_USUARIO", "Obtener información de un Usuario"));
		dao.create(new Permiso("UPDATE_USUARIO", "Modificar datos de un Usuario"));
		dao.create(new Permiso("DELETE_USUARIO", "Eliminar un Usuario"));
		dao.create(new Permiso("UPDATESELF_USUARIO", "Modificar los propios datos de usuario"));
		dao.create(new Permiso("VER_ANALITICAS", "Acceder a las analíticas"));
		dao.create(new Permiso("VER_MAPAS", "Acceder a las analíticas"));

		
		// Admin
		dao.create(new Permiso("ADMIN", "Acceso completo"));
	}
}
