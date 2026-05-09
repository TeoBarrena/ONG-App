package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.Area;

@RequestScoped
public class AreaDAO extends GenericDAO<Area> {
	
	public AreaDAO() {
		super(Area.class);
	}
	
}
