package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import jakarta.json.bind.annotation.JsonbTransient;

@Table(name = "Permiso", indexes = { @Index(name = "nombre", columnList = "nombre", unique = true) })

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SQLRestriction("borrado = false") //filtrado automático en los get
@SQLDelete(sql = "UPDATE Permiso SET borrado = true WHERE id = ?")
public class Permiso {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(unique = true, nullable = false)
	private String nombre;
	private String descripcion;

	@ManyToMany(mappedBy = "permisos")
	// @JsonbTransient
	@JsonIgnore
	private List<Rol> roles = new ArrayList<Rol>();

	// CONSTRUCTORES
	public Permiso() {
	} // Constructor vacío obligatorio

	public Permiso(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.roles = new ArrayList<Rol>();
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Rol> getRoles() {
		return new ArrayList<Rol>(roles);
	}

}
