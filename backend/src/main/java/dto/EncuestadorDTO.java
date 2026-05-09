package dto;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EncuestadorDTO {
	 
	private String nombre;
	private String dni;
	private String genero;
	private String ocupacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date nacimiento;
	private List<JornadaDTO> jornadas;
	
	public EncuestadorDTO() {
		
	}
	
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
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
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	public List<JornadaDTO> getJornadas() {
		return jornadas;
	}
	public void setJornadas(List<JornadaDTO> jornadas) {
		this.jornadas = jornadas;
	}
	
	
}
