package an.dpr.enbizzi.domain;

import java.io.Serializable;
import java.text.ParseException;
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
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import an.dpr.enbizzi.beans.Orache;
import an.dpr.enbizzi.domain.CyclingType;
import an.dpr.enbizzi.domain.Difficulty;
import an.dpr.util.UtilFecha;

@Entity
@Table(name = "salidas")
@XmlRootElement(name = "Salida")
public class Salida implements Serializable {

    private static final Logger log = Logger.getLogger(Salida.class);

    /**
     * FOR SERLIALIZABLE
     */
    private static final long serialVersionUID = 1L;

    private Long idSalida;
    private CalendarVersion calendarVersion;
    private Date date; // fecha/hora
    private String route;
    private String returnRoute;
    private String stop;
    private Float km;
    private Integer elevationGain;
    private Difficulty difficulty;
    private CyclingType type;
    private Integer aemetStart;
    private Integer aemetStop;
    private Orache oracheStart;
    private Orache oracheStop;
    private List<Puerto> puertos;

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
    @Column
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
    @Column
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
    @Column
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
    @OneToOne
    public Difficulty getDifficulty() {
	return difficulty;
    }

    /**
     * @param dificulty
     *            the dificulty to set
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

    @Override
    public String toString() {
	return "BikeCalendar [id=" + idSalida + ", date=" + date + ", route=" + route
		+ ", returnRoute=" + returnRoute + ", stop=" + stop + ", km="
		+ km + ", elevationGain=" + elevationGain + ", difficulty="
		+ difficulty + ", type=" + type + ", aemetCodeSalida="
		+ aemetStart + ", aemetCodeDestino=" + aemetStop + "]";
    }

    @Column
    public Integer getElevationGain() {
	return elevationGain;
    }

    public void setElevationGain(Integer elevationGain) {
	this.elevationGain = elevationGain;
    }

    public void setDate(String fecha) {
	log.debug("fecha:" + fecha);
	try {
	    this.date = UtilFecha.quitarHora(UtilFecha.getFechaHora(fecha));
	} catch (ParseException e) {
	    log.error("Fecha en formato incorrecto:" + fecha);
	}
    }

    @Column
    public Integer getAemetStart() {
	return aemetStart;
    }

    public void setAemetStart(Integer aemetStart) {
	this.aemetStart = aemetStart;
    }

    @Column
    public Integer getAemetStop() {
	return aemetStop;
    }

    public void setAemetStop(Integer aemetStop) {
	this.aemetStop = aemetStop;
    }

    /**
     * @return the calendarVersion
     */
    @OneToOne
    public CalendarVersion getCalendarVersion() {
	return calendarVersion;
    }

    /**
     * @param calendarVersion
     *            the calendarVersion to set
     */
    public void setCalendarVersion(CalendarVersion calendarVersion) {
	this.calendarVersion = calendarVersion;
    }

    /**
     * @return the oracheStart
     */
    @Transient
    public Orache getOracheStart() {
	return oracheStart;
    }

    /**
     * @param oracheStart
     *            the oracheStart to set
     */
    public void setOracheStart(Orache oracheStart) {
	this.oracheStart = oracheStart;
    }

    /**
     * @return the oracheStop
     */
    @Transient
    public Orache getOracheStop() {
	return oracheStop;
    }

    /**
     * @param oracheStop
     *            the oracheStop to set
     */
    public void setOracheStop(Orache oracheStop) {
	this.oracheStop = oracheStop;
    }

    /**
     * Metodo para limpiar datos no necesarios en la comunicacion con los
     * clientes y evitar kbytes de mas
     */
    public void limpiarInfo() {
	aemetStop = null;
	aemetStart = null;
	calendarVersion = null;
	idSalida = null;
    }

    /**
     * @return the puertos
     */
    @OneToMany
    public List<Puerto> getPuertos() {
        return puertos;
    }

    /**
     * @param puertos the puertos to set
     */
    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
    }

}
