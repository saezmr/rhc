package an.dpr.gesclub.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import an.dpr.gesclub.beans.Difficulty;
import an.dpr.gesclub.beans.TipoSalida;

@Entity
@Table(name = "salidas")
public class Salida implements Serializable {

    private static final Logger log = Logger.getLogger(Salida.class);

    /**
     * FOR SERLIALIZABLE
     */
    private static final long serialVersionUID = 1L;

    private Long idSalida;
    private Date date; // fecha/hora
    private String route;
    private String returnRoute;
    private String stop;
    private Float km;
    private Integer elevationGain;
    private String difficulty;
    private String tipo;
    private Club club;
    private String aemetCode;
    private Set<Socio> socios = new HashSet<Socio>();

    @ManyToOne
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getIdSalida() {
	return idSalida;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setIdSalida(Long id) {
	this.idSalida = id;
    }

    /**
     * @return the date
     */
    @Column
    public Date getDate() {
	return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the route
     */
    @Column(length=100)
    public String getRoute() {
	return route;
    }

    /**
     * @param route
     *            the route to set
     */
    public void setRoute(String route) {
	this.route = route;
    }

    /**
     * @return the returnRoute
     */
    @Column(length=100)
    public String getReturnRoute() {
	return returnRoute;
    }

    /**
     * @param returnRoute
     *            the returnRoute to set
     */
    public void setReturnRoute(String returnRoute) {
	this.returnRoute = returnRoute;
    }

    /**
     * @return the stop
     */
    @Column(length=100)
    public String getStop() {
	return stop;
    }

    /**
     * @param stop
     *            the stop to set
     */
    public void setStop(String stop) {
	this.stop = stop;
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
     * @return the dificulty
     */
    @Column(length=50)
    public String getDifficulty() {
	return difficulty;
    }

    /**
     * @param dificulty
     *            the dificulty to set
     */
    public void setDifficulty(String difficulty) {
	this.difficulty = difficulty;
    }


    @Column
    public Integer getElevationGain() {
	return elevationGain;
    }

    public void setElevationGain(Integer elevationGain) {
	this.elevationGain = elevationGain;
    }

    @Column(length=50)
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
	return "Salida [idSalida=" + idSalida + ", date=" + date + ", route="
		+ route + ", returnRoute=" + returnRoute + ", stop=" + stop
		+ ", km=" + km + ", elevationGain=" + elevationGain
		+ ", difficulty=" + difficulty + ", tipo=" + tipo + ", club="
		+ club + ", aemetCode="+aemetCode+"]";
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Socio> getSocios() {
	return socios;
    }

    public void setSocios(Set<Socio> socios) {
	this.socios = socios;
    }
    
    @Transient
    public String getTipoPresentacion(){
	String ret = null;
	if (tipo != null){
	    TipoSalida ts = TipoSalida.valueOf(tipo);
	    ret = ts.toString();
	}
	return ret;
    }
    
    @Transient
    public String getDifficultyPresentacion(){
	String ret = null;
	if (difficulty != null){
	    Difficulty df = Difficulty.valueOf(difficulty);
	    ret = df.toString();
	}
	return ret;
    }

    @Column
    public String getAemetCode() {
        return aemetCode;
    }

    public void setAemetCode(String aemetCode) {
        this.aemetCode = aemetCode;
    }



}
