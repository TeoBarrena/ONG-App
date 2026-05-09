package modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.File;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "DocumentoPublico")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE DocumentoPublico SET borrado = true WHERE id = ?")
public class DocumentoPublico {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
    
	private String titulo;
	private String descripcion;
	private Date fecha;
	private File archivo;

	// CONSTRUCTORES
	public DocumentoPublico() {
	} // Constructor vacío obligatorio

	public DocumentoPublico(String titulo, String descripcion, Date fecha, File archivo) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.archivo = archivo;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

}
