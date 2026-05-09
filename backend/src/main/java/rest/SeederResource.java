package rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import seeds.BarrioSeeds;
import seeds.CampañaSeeds;
import seeds.OrganizacionSocialSeeds;
import seeds.RolSeeds;
import seeds.PermisoSeeds;
import seeds.UsuarioSeeds;

@Path("/seed")
public class SeederResource {

    @Inject	private PermisoSeeds permisoSeeds;
    @Inject private RolSeeds rolSeeds;
    @Inject private OrganizacionSocialSeeds organizacionSocialSeeds;
    @Inject private UsuarioSeeds usuarioSeeds;
    @Inject private BarrioSeeds barrioSeeds;
    @Inject private CampañaSeeds campañaSeeds;

    @POST
    public Response runSeeds() {
    	try {
    		
    		// ⚠ IMPORTANTE: Orden de los seeds
    		// Permiso -> Rol -> OrganizacionSocial -> Usuario
    		// Barrio -> Campaña
    		
    		//Agregar invocación al metodo seed correspondiente a cada modelo.
    		barrioSeeds.seed();
    		campañaSeeds.seed();
    		
    		permisoSeeds.seed();
    		rolSeeds.seed();
    		organizacionSocialSeeds.seed();
    		usuarioSeeds.seed();
    		
    		return Response.ok("Base de datos cargada.").build();
    	}
    	catch (Exception e) {
    		throw e; //hasta saber que hacer con una posible excepcion
    	}
        
    }
}
