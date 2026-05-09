package modelos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Area")
@SQLRestriction("borrado = false")
@SQLDelete(sql = "UPDATE Area SET borrado = true WHERE id = ?")
public class Area {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    private boolean borrado = false;

    @OneToMany(mappedBy="area", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ubicacion> puntos;

    //CONSTRUCTORES
    public Area() {
        this.puntos = new ArrayList<Ubicacion>();
    } //constructor vacío obligatorio

    public Area(ArrayList<Ubicacion> puntos) {
        this();
        puntos.forEach(p -> p.setArea(this));
        this.addPuntos(puntos);
    }

    public Area(List<Area> lista) {
        this();
        for(Area a : lista) {
            for(Ubicacion p : a.getPuntos()) {
            	p.setArea(this);
                this.addPunto(p);
            }
        }
    }

    //MÉTODOS
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ubicacion> getPuntos(){
        return new ArrayList<Ubicacion>(puntos);
    }

    public void addPunto(Ubicacion punto) {
        punto.setArea(this);
        this.puntos.add(punto);
    }

    public void addPuntos(List<Ubicacion> puntos){
        puntos.forEach(punto -> this.addPunto(punto));
    }

    public void removePunto(Ubicacion punto) {
        this.puntos.remove(punto);
    }

    public void removePuntos(List<Ubicacion> puntos){
        this.puntos.removeAll(puntos);
    }
}
