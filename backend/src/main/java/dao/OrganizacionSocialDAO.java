package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.OrganizacionSocial;

@RequestScoped
public class OrganizacionSocialDAO extends GenericDAO<OrganizacionSocial> {

  public OrganizacionSocialDAO() {
    super(OrganizacionSocial.class);
  }

}
