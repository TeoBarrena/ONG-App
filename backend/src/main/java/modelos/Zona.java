package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.json.bind.annotation.JsonbTransient;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Zona")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Zona SET borrado = true WHERE id = ?")
public class Zona {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(unique = false, nullable = false)
	private String nombre;

	@OneToOne(cascade = CascadeType.ALL)
	private Area area;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Ubicacion centroGeografico;

	@ManyToOne
	@JoinColumn(name = "barrio_id")
	@JsonBackReference("barrio-zona")
	//@JsonIgnore
	private Barrio barrio;

	@ManyToMany(mappedBy = "zonas")
	@JsonIgnore
	private List<Jornada> jornadas = new ArrayList<>();

	// CONSTRUCTORES
	public Zona() {
	} // Constructor vacío obligatorio

	public Zona(String nombre, Area area, Ubicacion centroGeografico, Barrio barrio) {
		this.nombre = nombre;
		this.area = area;
		this.centroGeografico = centroGeografico;
		this.barrio = barrio;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Ubicacion getCentroGeografico() {
		return centroGeografico;
	}

	public void setCentroGeografico(Ubicacion centroGeografico) {
		this.centroGeografico = centroGeografico;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Jornada> getJornadas() {
		return new ArrayList<Jornada>(jornadas);
	}

	public void addJornada(Jornada jornada) {
		this.jornadas.add(jornada);
	}

	public void removeJornada(Jornada jornada) {
		this.jornadas.remove(jornada);
	}

}
