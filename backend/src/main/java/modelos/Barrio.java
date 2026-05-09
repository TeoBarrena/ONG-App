package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

//import org.w3c.dom.Text;

@Entity
@Table(name = "Barrio")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Barrio SET borrado = true WHERE id = ?")
public class Barrio {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(unique = false, nullable = false)
	private String nombre;

	private String descripcion;

	@OneToOne(cascade = CascadeType.ALL)
	private Ubicacion centroGeografico;

	@OneToMany(mappedBy = "barrio", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("barrio-zona")
	private List<Zona> zonas = new ArrayList<>();

	@OneToMany(mappedBy = "barrio")
	@JsonIgnore
	private List<Campaña> campañas;

	// CONSTRUCTORES

	public Barrio() {
	} // Constructor vacío obligatorio

	public Barrio(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.zonas = new ArrayList<Zona>();
		this.campañas = new ArrayList<Campaña>();
	}

	public Barrio(String nombre, String descripcion, Ubicacion centroGeografico) {
		this(nombre, descripcion);
		this.centroGeografico = centroGeografico;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
/*
	public Area getArea() {
		return new Area(this.zonas.stream().map(z -> z.getArea()).collect(Collectors.toList()));
	}
*/
	public Ubicacion getCentroGeografico() {
		return centroGeografico;
	}

	public void setCentroGeografico(Ubicacion centroGeografico) {
		this.centroGeografico = centroGeografico;
	}

	public List<Campaña> getCampañas() {
		return new ArrayList<Campaña>(campañas);
	}

	public void addCampaña(Campaña campaña) {
		this.campañas.add(campaña);
	}

	public void removeCampaña(Campaña campaña) {
		this.campañas.remove(campaña);
	}

	public List<Zona> getZonas() {
		return new ArrayList<Zona>(zonas);
	}

	public void addZona(Zona zona) {
		this.zonas.add(zona);
        zona.setBarrio(this);
	}

	public void removeZona(Zona zona) {
		this.zonas.remove(zona);
	}

	public void addZonas(List<Zona> zonas) {
		zonas.forEach(z -> {
            this.zonas.add(z);
            z.setBarrio(this);
        });
	}

	public void removeZonas(List<Zona> zonas) {
		this.zonas.removeAll(zonas);
	}
}
