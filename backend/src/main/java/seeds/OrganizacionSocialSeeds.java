package seeds;

import java.util.ArrayList;
import java.util.Map;

import dao.OrganizacionSocialDAO;
import dao.RolDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.OrganizacionSocial;
import modelos.Reporte;

@RequestScoped
public class OrganizacionSocialSeeds implements Seed{

	@Inject
	private OrganizacionSocialDAO dao;
	
	@Inject
	private RolDAO rolDao;
	
	public OrganizacionSocialSeeds() {}
	
	public void seed() {
		
        dao.create(new OrganizacionSocial(
            "Prosecretaría de Salud de la UNLP",
            "Organismo de la Universidad Nacional de La Plata dedicado a la promoción, prevención y atención integral de la salud.",
            "Campañas sanitarias, atención médica gratuita, promoción de la salud, vacunación, educación comunitaria",
            "Calle 50 nº 1026, La Plata"
        ));
        var org1 = dao.get("nombre", "Prosecretaría de Salud de la UNLP");
        org1.setRol(rolDao.get("nombre", "Organización de Salud"));
        dao.update(org1, org1.getId());
        
        dao.create(new OrganizacionSocial(
            "Hogar Padre Cajade",
            "Organización que trabaja con niñas, niños y adolescentes en situación de vulnerabilidad social.",
            "Acompañamiento educativo, talleres de oficios, apoyo psicológico, actividades culturales",
            "Calle 643 Nº 275, Villa Elvira, La Plata"
        ));
        var org2 = dao.get("nombre", "Hogar Padre Cajade");
        org2.setRol(rolDao.get("nombre", "Organización Social"));
        dao.update(org2, org2.getId());

        
        dao.create(new OrganizacionSocial(
            "Fundación Pelota de Trapo La Plata",
            "Espacio comunitario y educativo para niños, niñas y adolescentes en situación de calle o pobreza.",
            "Comedor, apoyo escolar, talleres, contención social",
            "Calle 122 y 38, La Plata"
        ));
        //SIN ROL ASIGNADO
        
        dao.create(new OrganizacionSocial(
            "Barrio Unido",
            "Colectivo barrial que trabaja en la integración comunitaria y el desarrollo social en zonas periféricas.",
            "Talleres de oficios, actividades recreativas, merendero, promoción de derechos",
            "Calle 607 entre 121 y 122, Barrio El Mercadito, La Plata",
            rolDao.get("nombre", "Organización Social")
        ));

        dao.create(new OrganizacionSocial(
            "Red de Médicos del Mundo La Plata",
            "Red de profesionales de la salud que brindan atención y promueven el derecho a la salud en poblaciones vulnerables.",
            "Atención médica, capacitaciones, campañas de prevención, salud comunitaria",
            "Calle 60 n° 1065, La Plata",
            rolDao.get("nombre", "Organización de Salud")
        ));
	}
}
