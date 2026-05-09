package modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import jakarta.json.bind.annotation.JsonbTransient;

//import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Encuestador")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Encuestador SET borrado = true WHERE id = ?")
public class Encuestador {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private boolean borrado = false;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date nacimiento;
	private String nombre;
	private String dni;
	private String genero;
	private String ocupacion;

	@ManyToMany(mappedBy = "encuestadores")
	// @JsonbTransient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Jornada> jornadas = new ArrayList<>();

	// CONSTRUCTORES
	public Encuestador() {
	} // Constructor vacío obligatorio

	public Encuestador(Date nacimiento, String nombre, String dni, String genero, String ocupacion) {
		this.nacimiento = nacimiento;
		this.nombre = nombre;
		this.dni = dni;
		this.genero = genero;
		this.ocupacion = ocupacion;
	}

	// MÉTODOS PRIVADOS
 
	// MÉTODOS PÚBLICOS

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Jornada> getJornadas() {
		return this.jornadas;
	}
	
	public void setJornadas(List<Jornada> jornadas) {
		this.jornadas = jornadas;
	}

	public void addJornada(Jornada jornada) {
		this.jornadas.add(jornada);
	}

	public void removeJornada(Jornada jornada) {
		this.jornadas.remove(jornada);
	}

}
