package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.File;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Reporte")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Reporte SET borrado = true WHERE id = ?")
public class Reporte {

	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
	private String titulo;
	private String descripcion;
	private Date fecha;
	private File archivo;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario creador;

	@ManyToOne
	@JoinColumn(name = "organizacion_social_id")
	private OrganizacionSocial organizacionSocial;

	// CONSTRUCTORES
	public Reporte() {
	} // Constructor vacío obligatorio

	public Reporte(String titulo, String descripcion, Date fecha, File archivo, Estado estado, Usuario creador,
			OrganizacionSocial organizacionSocial) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.archivo = archivo;
		this.estado = Estado.PENDIENTE;
		this.creador = creador;
		this.organizacionSocial = organizacionSocial;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public OrganizacionSocial getOrganizacionSocial() {
		return organizacionSocial;
	}

	public void setOrganizacionSocial(OrganizacionSocial organizacionSocial) {
		this.organizacionSocial = organizacionSocial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public Usuario getCreador() {
		return creador;
	}

}
