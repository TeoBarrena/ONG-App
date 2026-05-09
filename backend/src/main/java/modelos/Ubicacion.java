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
@Table(name = "Ubicacion")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Ubicacion SET borrado = true WHERE id = ?")
public class Ubicacion {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
	private String latitud;
	private String longitud;

	@ManyToOne
	@JoinColumn(name = "area_id")
	@JsonBackReference
	private Area area;

	// CONSTRUCTORES
	public Ubicacion() {
	} // Constructor vacío obligatorio

	public Ubicacion(String latitud, String longitud){
		super();
		this.latitud = latitud;
		this.longitud = longitud;		
	}
	
	public Ubicacion(String latitud, String longitud, Area area) {
		this(latitud,longitud);
		this.area = area;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
