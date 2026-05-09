package seeds;

import java.util.ArrayList;

import dao.AreaDAO;
import dao.BarrioDAO;
import dao.CampañaDAO;
import dao.EncuestadorDAO;
import dao.JornadaDAO;
import dao.ZonaDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.Area;
import modelos.Barrio;
import modelos.Jornada;
import modelos.Ubicacion;
import modelos.Campaña;
import modelos.Zona;
import modelos.Encuestador;
import java.util.List;
import java.sql.Date;

@RequestScoped
public class CampañaSeeds implements Seed {
	// ANTES HAY QUE HACER EL DE BARRIOS

	@Inject
	private CampañaDAO dao;

	@Inject
	private JornadaDAO jornadaDao;

	@Inject
	private BarrioDAO barrioDao;

	@Inject
	private EncuestadorDAO encuestadorDao;

	@Override
	public void seed() {
		Barrio villaArguello = barrioDao.get("nombre", "Villa Argüello");

		Campaña campañaVA = new Campaña("Campaña Villa Argüello 2025", Date.valueOf("2025-07-01"),Date.valueOf("2025-07-05"), villaArguello);
		dao.create(campañaVA);

		Jornada jornada1 = new Jornada(Date.valueOf("2025-07-05"), null); //falta manejo de files
		Jornada jornada2 = new Jornada(Date.valueOf("2025-07-15"), null);
		Jornada jornada3 = new Jornada(Date.valueOf("2025-07-25"), null);

		List<Zona> zonasVA = villaArguello.getZonas();

		jornada1.addZona(zonasVA.get(0));
		jornada1.addZona(zonasVA.get(1));
		
		jornada2.addZona(zonasVA.get(0));
		jornada2.addZona(zonasVA.get(1));
		jornada2.addZona(zonasVA.get(2));
		
		jornada3.addZona(zonasVA.get(2));

		Encuestador e1 = new Encuestador(Date.valueOf("1980-01-15"), "Ana Pérez", "12345678", "F", "Estudiante");
		Encuestador e2 = new Encuestador(Date.valueOf("1975-05-20"), "Luis Gómez", "23456789", "M", "Profesor");
		Encuestador e3 = new Encuestador(Date.valueOf("1990-08-30"), "María López", "34567890", "F", "Investigadora");
		Encuestador e4 = new Encuestador(Date.valueOf("1985-12-10"), "Jorge Díaz", "45678901", "M", "Analista");
		Encuestador e5 = new Encuestador(Date.valueOf("1992-03-25"), "Laura Torres", "56789012", "F", "Administrativa");

		encuestadorDao.create(e1);
		encuestadorDao.create(e2);
		encuestadorDao.create(e3);
		encuestadorDao.create(e4);
		encuestadorDao.create(e5);

		jornada1.addEncuestador(e1);
		jornada1.addEncuestador(e2);
		
		jornada2.addEncuestador(e2);
		jornada2.addEncuestador(e3);
		jornada2.addEncuestador(e4);
		
		jornada3.addEncuestador(e1);
		jornada3.addEncuestador(e5);
		
		jornada1.setCampaña(campañaVA);
		jornada2.setCampaña(campañaVA);
		jornada3.setCampaña(campañaVA);

		jornadaDao.create(jornada1);
		jornadaDao.create(jornada2);
		jornadaDao.create(jornada3);

		e1.addJornada(jornada1);
		e1.addJornada(jornada3);
		e2.addJornada(jornada1);
		e2.addJornada(jornada2);
		e3.addJornada(jornada2);
		e4.addJornada(jornada2);
		e5.addJornada(jornada3);
		
		campañaVA.addJornada(jornada1);
		campañaVA.addJornada(jornada2);
		campañaVA.addJornada(jornada3);
		
		dao.update(campañaVA, campañaVA.getId()); 
		
	}
}