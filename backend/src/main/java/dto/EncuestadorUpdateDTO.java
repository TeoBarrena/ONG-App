package dto;

import java.util.Date;
import java.util.List;

public class EncuestadorUpdateDTO {
	
	private String nombre;
	 private String dni;
    private String ocupacion;
    private String genero;
    private Date nacimiento;
    private List<Long> jornadasIds;
    
    public EncuestadorUpdateDTO() {
    }
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
	public List<Long> getJornadasIds() {
		return jornadasIds;
	}
	public void setJornadasIds(List<Long> jornadasIds) {
		this.jornadasIds = jornadasIds;
	}

    
    
}
