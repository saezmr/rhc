package an.dpr.enbizzi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comarcas")
public class Comarca {

    private Long idComarca;
    private String nombre;
    private Integer codProvincia;
    private String provincia;
    private String pais;
    
    public Comarca(){}

    public Comarca(String nombre, Integer codProvincia) {
	this.nombre = nombre;
	this.codProvincia = codProvincia;
    }

    /**
     * @return the idComarca
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getIdComarca() {
	return idComarca;
    }

    /**
     * @param idComarca
     *            the idComarca to set
     */
    public void setIdComarca(Long idComarca) {
	this.idComarca = idComarca;
    }

    /**
     * @return the nombre
     */
    @Column(unique=true)
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
     * @return the codProvincia
     */
    @Column
    public Integer getCodProvincia() {
	return codProvincia;
    }

    /**
     * @param codProvincia
     *            the codProvincia to set
     */
    public void setCodProvincia(Integer codProvincia) {
	this.codProvincia = codProvincia;
    }

    /**
     * @return the provincia
     */
    @Column
    public String getProvincia() {
	return provincia;
    }

    /**
     * @param provincia
     *            the provincia to set
     */
    public void setProvincia(String provincia) {
	this.provincia = provincia;
    }

    /**
     * @return the pais
     */
    @Column
    public String getPais() {
	return pais;
    }

    /**
     * @param pais
     *            the pais to set
     */
    public void setPais(String pais) {
	this.pais = pais;
    }

}
