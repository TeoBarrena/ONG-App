package dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
 
//s

@ApplicationScoped
public class EMF {

  private final EntityManagerFactory emf;

  public EMF() {
    // Se inicializa solo una vez durante el ciclo de vida de la aplicación (No hace falta que sea singleton, pero queda prohibido hacer new EMF())
    this.emf = Persistence.createEntityManagerFactory("production");
  }

  @Produces
  public EntityManagerFactory produceEntityManagerFactory() {
    return emf;
  }

  @Produces
  @RequestScoped
  public EntityManager produceEntityManager(EntityManagerFactory emf) {
    return emf.createEntityManager();
  }
}

/*
 * 
 * Poner breakpoint en el creador de EM, modod debug y hacer varios request para verificar:
 * que tenga una direccion de memoria distinta
 * que su ciclo de vida sea el request
public class EMF { //Singleton, para no crear un EntityManagerFactory con cada requerimiento (Muy costoso)

	private static EMF instance;
	private EntityManagerFactory entitymanagerfactory;
	
	private EMF(String nombreUnidad) {
		this.entitymanagerfactory= Persistence.createEntityManagerFactory(nombreUnidad);
	}
	
	public static EMF getInstance(String nombreUnidad) {
		if (instance == null) {
			instance = new EMF(nombreUnidad);
		}
		return instance;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entitymanagerfactory;
	}
}
*/
