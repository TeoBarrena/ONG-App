package modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

//import jakarta.json.bind.annotation.JsonbTransient;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@NamedQuery(name = "searchByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")

@NamedQuery(name = "searchByMatricula", query = "SELECT u FROM Usuario u WHERE u.matricula = :matricula")

@Table(name="Usuario", indexes = { @Index(name = "email", columnList = "email", unique = true) },

uniqueConstraints = {
    @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
    @UniqueConstraint(name = "uk_usuario_matricula", columnNames = "matricula")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Usuario SET borrado = true WHERE id = ?")
public class Usuario {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    private boolean borrado = false;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellido;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(unique = true, nullable = true)
	private Integer matricula;

	private Estado estado;

	@OneToMany(mappedBy = "creador", orphanRemoval = true)
	private List<Reporte> reportes = new ArrayList<Reporte>();

	@ManyToOne
	@JsonIdentityReference(alwaysAsId = false)
	private OrganizacionSocial organizacion;

	@ManyToMany
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	@JsonIdentityReference(alwaysAsId = false)
	private Set<Rol> roles = new HashSet<Rol>();

	@Column(nullable = false)
	private Boolean admin;

	// CONSTRUCTORES
	public Usuario() {
		this.estado = Estado.PENDIENTE;
	} // Constructor vacío obligatorio

	// CONSTRUCTOR TEMPLATE
	private Usuario(String nombre, String apellido, String password, String email) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.admin = false;
		this.password = password;
		this.email = email;
		this.estado = Estado.PENDIENTE;
		this.reportes = new ArrayList<Reporte>();
		this.roles = new HashSet<Rol>();
	}

	// CONSTRUCTOR para Miembro de Organización Social
	public Usuario(String nombre, String apellido, String password, String email, OrganizacionSocial organizacion) {
		this(nombre, apellido, password, email);
		this.admin = false;
		this.organizacion = organizacion;
		this.addRol(organizacion.getRol());
	}

	// CONSTRUCTOR para Personal de Salud
	public Usuario(String nombre, String apellido, String password, String email, Integer matricula) {
		this(nombre, apellido, password, email);
		this.admin = false;
		this.matricula = matricula;
		this.rolesPersonalSalud();
	}

	// CONSTRUCTOR para Personal de Salud Y Miembro de Org. Social
	public Usuario(String nombre, String apellido, String password, String email, Integer matricula,
			OrganizacionSocial organizacion) {
		this(nombre, apellido, password, email, matricula);
		this.organizacion = organizacion;
		this.admin = false;
		this.addRol(organizacion.getRol());
	}

	// CONSTRUCTOR para admin
	public Usuario(String nombre, String apellido, String password, String email, Boolean admin) {
		this(nombre, apellido, password, email);
		this.admin = admin;
		this.estado = Estado.HABILITADO;
		this.rolesAdmin();
	}

	// MÉTODOS PRIVADOS

	private void rolesAdmin() {
		// anotar los roles correspondientes a admin
		// this.addRol( Rol ); ...
	}

	private void rolesPersonalSalud() {
		// anotar los roles correspondientes a personal de salud
		// this.addRol( Rol ); ...
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	// MÉTODOS PÚBLICOS

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public OrganizacionSocial getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionSocial organizacion) {
		this.organizacion = organizacion;
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

	public List<Rol> getRoles() {
		return new ArrayList<Rol>(roles);
	}

	public void addRol(Rol rol) {
		this.roles.add(rol);
	}

	public void addRoles(List<Rol> roles) {
		this.roles.addAll(roles);
	}

	public void removeRol(Rol rol) {
		this.roles.remove(rol);
	}

	public void removeRoles(List<Rol> roles) {
		this.roles.removeAll(roles);
	}
}
