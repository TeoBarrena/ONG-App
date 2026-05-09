package seeds;

public class Reseter {
	/*
	@Inject
	EntityManager em;
	
	public void resetDB() {
	    EntityTransaction transaction = em.getTransaction();
	    try {
	    	
	        transaction.begin();

	        //desactivar restricciones de claves foráneas
	        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
	        
	        em.createNativeQuery("DROP TABLE IF EXISTS usuario_rol").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS rol_permiso").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS jornada_encuestador").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS jornada_zona").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS reporte").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS documentoPublico").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS dato").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS usuario").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS rol").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS permiso").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS organizacionSocial").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS encuestador").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS jornada").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS campaña").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS zona").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS barrio").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS area").executeUpdate();
	        em.createNativeQuery("DROP TABLE IF EXISTS ubicacion").executeUpdate();

	        em.createNativeQuery("DROP TABLE IF EXISTS hibernate_sequence").executeUpdate();

	        // Reactivar restricciones
	        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

	        transaction.commit();
	        System.out.println("Tablas eliminadas.");
	    } catch (Exception e) {
	        if (transaction.isActive()) 
	        	transaction.rollback();
	        e.printStackTrace();
	    }
	}
	*/
}
