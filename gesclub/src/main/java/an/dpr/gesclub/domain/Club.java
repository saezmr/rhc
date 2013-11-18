package an.dpr.gesclub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Club
 * 
 * @author rsaez
 * 
 */
@Entity
@Table(name = "clubes")
public class Club {

    private Long id;
    private String nombre;
    private String descripcion;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Column
    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    @Column
    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }
}
