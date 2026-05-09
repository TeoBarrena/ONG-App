package rest;

import dao.OrganizacionSocialDAO;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import modelos.OrganizacionSocial;

@Path("/organizaciones")
public class OrganizacionSocialResource extends GenericResource<OrganizacionSocial>{
	
	@Inject
	private OrganizacionSocialDAO dao;
	
	public OrganizacionSocialResource() {
		super();
	}
	
	@PostConstruct
	public void postConstruct() {
		this.setDAO(dao);
	}

}
