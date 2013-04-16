package an.dpr.enbizzi.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Ciclistas de enbizzi
 * 
 * @author rsaez
 * 
 */
@Entity
@Table(name = "socios")
public class Socio {

    private Integer numSocio;
    private String nombre;
    private String apellidos;
    private Set<Salida> salidas;

    /**
     * @return the numSocio
     */
    @Id
    @Column(nullable = false)
    public Integer getNumSocio() {
	return numSocio;
    }

    /**
     * @param numSocio
     *            the numSocio to set
     */
    public void setNumSocio(Integer numSocio) {
	this.numSocio = numSocio;
    }

    /**
     * @return the nombre
     */
    @Column(nullable = false)
    public String getNombre() {
	return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    @Column(nullable = false)
    public String getApellidos() {
	return apellidos;
    }

    /**
     * @param apellidos
     *            the apellidos to set
     */
    public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
    }

    /**
     * @return the salidas
     */
    @ManyToMany(mappedBy="participantes", fetch = FetchType.EAGER)
    public Set<Salida> getSalidas() {
	return salidas;
    }

    /**
     * @param salidas
     *            the salidas to set
     */
    public void setSalidas(Set<Salida> salidas) {
	this.salidas = salidas;
    }
}
