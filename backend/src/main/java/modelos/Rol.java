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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import jakarta.json.bind.annotation.JsonbTransient;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Table(name="Rol", indexes = { @Index(name = "nombre", columnList = "nombre", unique = true) })
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Rol SET borrado = true WHERE id = ?")
public class Rol {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(unique = true, nullable = false)
	private String nombre;

	@Column(nullable = false)
	private Boolean activo = true;

	@ManyToMany(mappedBy = "roles")
	// @JsonbTransient
	@JsonIgnore
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	// hago esta relacion many to many porque sino estoy limitando a que un permiso
	// pertenezca a un rol, en cambio asi
	// digo que un permiso puede estar en distintos roles
	@ManyToMany
	@JoinTable(name = "rol_permiso", joinColumns = @JoinColumn(name = "rol_id"), inverseJoinColumns = @JoinColumn(name = "permiso_id"))
	@JsonIgnore
	private List<Permiso> permisos = new ArrayList<Permiso>();

	// CONSTRUCTORES
	public Rol() {
	} // Constructor vacío obligatorio

	public Rol(String nombre) {
		super();
		this.nombre = nombre;
		this.usuarios = new ArrayList<Usuario>();
		this.permisos = new ArrayList<Permiso>();
	}

	public Rol(String nombre, Boolean activo) {
		this(nombre);
		this.activo = activo;
	}

	// MÉTODOS PRIVADOS

	// MÉTODOS PÚBLICOS

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Usuario> getUsuarios() {
		return new ArrayList<Usuario>(usuarios);
	}

	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}

	public void removeUsuario(Usuario usuario) {
		this.usuarios.remove(usuario);
	}

	public List<Permiso> getPermisos() {
		return new ArrayList<Permiso>(permisos);
	}

	public void addPermiso(Permiso permiso) {
		this.permisos.add(permiso);
	}

	public void removePermiso(Permiso permiso) {
		this.permisos.remove(permiso);
	}

}
