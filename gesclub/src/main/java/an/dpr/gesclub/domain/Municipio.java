package an.dpr.gesclub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Municipios españolas, para la obtencion del tiempo de aemet
 * 
 * @author rsaez
 * 
 */
@Entity
@Table(name="municipios")
public class Municipio {

    private Integer idMunicipio;
    private String ineCode;
    private String nombre;
    
    public Municipio(){}
    
    public Municipio(String ineCode, String nombre){
	this.ineCode = ineCode;
	this.nombre = nombre;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Integer getIdMunicipio() {
	return idMunicipio;
    }
    
    public void setIdMunicipio(Integer idMunicipio) {
	this.idMunicipio = idMunicipio;
    }

    @Column(nullable = false)
    public String getIneCode() {
	return ineCode;
    }

    public void setIneCode(String ineCode) {
	this.ineCode = ineCode;
    }

    @Column(nullable=false)
    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    @Override
    public String toString() {
	return "Municipio [ineCode=" + ineCode + ", nombre=" + nombre + "]";
    }


}
