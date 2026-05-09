package modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Campaña")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Campaña SET borrado = true WHERE id = ?")
public class Campaña {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
    
	private String nombre;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date inicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fin;

	@ManyToOne
	@JoinColumn(name = "barrio_id")
	private Barrio barrio;

	@OneToMany(mappedBy = "campaña")
	@JsonManagedReference
	private List<Jornada> jornadas;

	// CONSTRUCTORES
	public Campaña() {
	} // Constructor vacío obligatorio

	public Campaña(String nombre, Date inicio, Barrio barrio) {
		super();
		this.nombre = nombre;
		this.inicio = inicio;
		this.barrio = barrio;
		this.jornadas = new ArrayList<Jornada>();
	}

	public Campaña(String nombre, Date inicio, Date fin, Barrio barrio) {
		this(nombre, inicio, barrio);
		this.fin = fin;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInicio() {
		return inicio;
	}
	
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Barrio getBarrio() {
		return barrio;
	}
	
	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
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
