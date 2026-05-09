package modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "Jornada")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Jornada SET borrado = true WHERE id = ?")
public class Jornada {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;
    
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fecha;
	@JsonIgnore
	private File archivo;

	@ManyToMany
	@JoinTable(name = "jornada_zona", joinColumns = @JoinColumn(name = "jornada_id"), inverseJoinColumns = @JoinColumn(name = "zona_id"))
	private List<Zona> zonas = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "jornada_encuestador", joinColumns = @JoinColumn(name = "jornada_id"), inverseJoinColumns = @JoinColumn(name = "encuestador_id"))
	private List<Encuestador> encuestadores = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "campaña_id")
	@JsonBackReference
	private Campaña campaña;

	@OneToMany(mappedBy = "jornada")
	private List<Dato> datos = new ArrayList<>();

	// CONSTRUCTORES
	public Jornada() {
	} // Constructor vacío obligatorio

	public Jornada(Date fecha, File archivo) {
		super();
		this.fecha = fecha;
		this.archivo = archivo;
		this.zonas = new ArrayList<Zona>();
		this.encuestadores = new ArrayList<Encuestador>();
		this.datos = new ArrayList<Dato>();
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public Campaña getCampaña() {
		return campaña;
	}

	public void setCampaña(Campaña campaña) {
		this.campaña = campaña;
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
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Zona> getZonas() {
		return new ArrayList<Zona>(zonas);
	}

	public void addZona(Zona zona) {
		this.zonas.add(zona);
	}

	public void removeZona(Zona zona) {
		this.zonas.remove(zona);
	}

	public List<Encuestador> getEncuestadores() {
		return new ArrayList<Encuestador>(encuestadores);
	}

	public void addEncuestador(Encuestador encuestador) {
		this.encuestadores.add(encuestador);
	}

	public void removeEncuestador(Encuestador encuestador) {
		this.encuestadores.remove(encuestador);
	}

	public List<Dato> getDatos() {
		return new ArrayList<Dato>(datos);
	}

	public void addDato(Dato dato) {
		this.datos.add(dato);
	}

	public void removeDato(Dato dato) {
		this.datos.remove(dato);
	}
}
