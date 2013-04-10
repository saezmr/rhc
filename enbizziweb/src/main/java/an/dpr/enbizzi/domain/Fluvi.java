package an.dpr.enbizzi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import an.dpr.enbizzi.beans.Orache;

@Entity
@Table(name = "fluvis")
public class Fluvi {

    private Long idFluvi;
    private Date fecha;
    private Integer aemetCode;
    private String salida;
    private String ruta;
    private String descripcionRuta;
    private String comoLlegar;
    private Float km;
    private Integer elevationGain;
    private Difficulty difficulty;
    private CyclingType type;
    private Orache orache;
    private List<Puerto> puertos;

    /**
     * @return the idFluvi
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getIdFluvi() {
	return idFluvi;
    }

    /**
     * @param idFluvi
     *            the idFluvi to set
     */
    public void setIdFluvi(Long idFluvi) {
	this.idFluvi = idFluvi;
    }

    /**
     * @return the fecha
     */
    @Column
    public Date getFecha() {
	return fecha;
    }

    /**
     * @param fecha
     *            the fecha to set
     */
    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    /**
     * @return the aemetCode
     */
    @Column
    public Integer getAemetCode() {
	return aemetCode;
    }

    /**
     * @param aemetCode
     *            the aemetCode to set
     */
    public void setAemetCode(Integer aemetCode) {
	this.aemetCode = aemetCode;
    }

    /**
     * @return the salida
     */
    @Column
    public String getSalida() {
	return salida;
    }

    /**
     * @param salida
     *            the salida to set
     */
    public void setSalida(String salida) {
	this.salida = salida;
    }

    /**
     * @return the ruta
     */
    @Column
    public String getRuta() {
	return ruta;
    }

    /**
     * @param ruta
     *            the ruta to set
     */
    public void setRuta(String ruta) {
	this.ruta = ruta;
    }

    /**
     * @return the descripcionRuta
     */
    @Column
    public String getDescripcionRuta() {
	return descripcionRuta;
    }

    /**
     * @param descripcionRuta
     *            the descripcionRuta to set
     */
    public void setDescripcionRuta(String descripcionRuta) {
	this.descripcionRuta = descripcionRuta;
    }

    /**
     * @return the comoLlegar
     */
    @Column
    public String getComoLlegar() {
	return comoLlegar;
    }

    /**
     * @param comoLlegar
     *            the comoLlegar to set
     */
    public void setComoLlegar(String comoLlegar) {
	this.comoLlegar = comoLlegar;
    }

    /**
     * @return the km
     */
    @Column
    public Float getKm() {
	return km;
    }

    /**
     * @param km
     *            the km to set
     */
    public void setKm(Float km) {
	this.km = km;
    }

    /**
     * @return the elevationGain
     */
    @Column
    public Integer getElevationGain() {
	return elevationGain;
    }

    /**
     * @param elevationGain
     *            the elevationGain to set
     */
    public void setElevationGain(Integer elevationGain) {
	this.elevationGain = elevationGain;
    }

    /**
     * @return the difficulty
     */
    @OneToOne
    public Difficulty getDifficulty() {
	return difficulty;
    }

    /**
     * @param difficulty
     *            the difficulty to set
     */
    public void setDifficulty(Difficulty difficulty) {
	this.difficulty = difficulty;
    }

    /**
     * @return the type
     */
    @OneToOne
    public CyclingType getType() {
	return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(CyclingType type) {
	this.type = type;
    }

    /**
     * @return the orache
     */
    @Transient
    public Orache getOrache() {
	return orache;
    }

    /**
     * @param orache
     *            the orache to set
     */
    public void setOrache(Orache orache) {
	this.orache = orache;
    }

    /**
     * @return the puertos
     */
    @OneToMany
    public List<Puerto> getPuertos() {
	return puertos;
    }

    /**
     * @param puertos
     *            the puertos to set
     */
    public void setPuertos(List<Puerto> puertos) {
	this.puertos = puertos;
    }
}
