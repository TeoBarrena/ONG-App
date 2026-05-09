package modelos;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Dato")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Dato SET borrado = true WHERE id = ?")
public class Dato{
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
    
	private String pregunta;
	private String respuesta;
	private String tipo;

	@ManyToOne
	@JoinColumn(name = "jornada_id")
	@JsonBackReference
	private Jornada jornada;

	// CONSTRUCTORES
	public Dato() {
	} // Constructor vacío obligatorio

	public Dato(String pregunta, String respuesta, String tipo, Jornada jornada) {
		super();
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.tipo = tipo;
		this.jornada = jornada;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public String getTipo() {
		return tipo;
	}

	public Jornada getJornada() {
		return jornada;
	}

}