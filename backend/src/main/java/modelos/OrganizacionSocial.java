package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "OrganizacionSocial")
@SQLRestriction("borrado = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SQLDelete(sql = "UPDATE OrganizacionSocial SET borrado = true WHERE id = ?")
public class OrganizacionSocial {

	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(nullable = false)
	private String nombre;
	private String descripcion;
	private String actividades;
	private String domicilio;

	// distintas organizaciones sociales pueden tener el mismo rol, se navega desde
	// org. social hacia rol
	@ManyToOne
	@JoinColumn(name = "rol_id")
	private Rol rol;

	@OneToMany(mappedBy = "organizacion", orphanRemoval = true)
	private List<Usuario> usuarios = new ArrayList<>();

	@OneToMany(mappedBy = "organizacionSocial",orphanRemoval = true)
	private List<Reporte> reportes = new ArrayList<Reporte>();

	// CONSTRUCTORES
	public OrganizacionSocial() {
	} // Constructor vacío obligatorio

	public OrganizacionSocial(String nombre, String descripcion, String actividades, String domicilio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.actividades = actividades;
		this.domicilio = domicilio;
		this.reportes = new ArrayList<Reporte>();
	}

	public OrganizacionSocial(String nombre, String descripcion, String actividades, String domicilio, Rol rol) {
		this(nombre, descripcion, actividades, domicilio);
		this.rol = rol;
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

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Reporte> getReportes() {
		return new ArrayList<Reporte>(reportes);
	}

	public void addReporte(Reporte reporte) {
		this.reportes.add(reporte);
	}

	public void removeReporte(Reporte reporte) {
		this.reportes.remove(reporte);
	}

}
