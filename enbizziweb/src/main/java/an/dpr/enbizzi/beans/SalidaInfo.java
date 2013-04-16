package an.dpr.enbizzi.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import an.dpr.enbizzi.domain.Puerto;
import an.dpr.enbizzi.domain.Salida;

/**
 * Bean para informacion de Salidas al exterior
 * @author rsaez
 *
 */
@XmlRootElement(name = "Salida")
public class SalidaInfo {
    
    private static final Logger log = Logger.getLogger(SalidaInfo.class);
    
    private Date date; // fecha/hora
    private String route;
    private String returnRoute;
    private String stop;
    private Float km;
    private Integer elevationGain;
    private String difficulty;
    private String type;
    private Orache oracheStart;
    private Orache oracheStop;
    private List<PuertoInfo> puertos;

    public static SalidaInfo getBean(Salida salida) {
	SalidaInfo info = null;
	if (salida != null){
	    log.debug("salida info "+salida);
	    info = new SalidaInfo();
	    info.setDate(salida.getDate());
	    info.setRoute(salida.getRoute());
	    info.setReturnRoute(salida.getReturnRoute());
	    info.setStop(salida.getStop());
	    info.setKm(salida.getKm());
	    info.setElevationGain(salida.getElevationGain());
	    info.setDifficulty(salida.getDifficulty().getName());
	    info.setType(salida.getType().getName());
	    info.setOracheStart(salida.getOracheStart());
	    info.setOracheStop(salida.getOracheStop());
	    List<PuertoInfo> listPuertos = new ArrayList<PuertoInfo>();
	    for(Puerto p : salida.getPuertos()){
		listPuertos.add(PuertoInfo.getBean(p));
	    }
	    info.setPuertos(listPuertos);
	}
	log.debug("return salidaInfo:"+info);
	return info;
    }

    public static List<SalidaInfo> getList(List<Salida> salidas) {
	List<SalidaInfo> list = new ArrayList<SalidaInfo>();
	for(Salida salida : salidas) {
	    list.add(SalidaInfo.getBean(salida));
	}
	return list;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the route
     */
    public String getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * @return the returnRoute
     */
    public String getReturnRoute() {
        return returnRoute;
    }

    /**
     * @param returnRoute the returnRoute to set
     */
    public void setReturnRoute(String returnRoute) {
        this.returnRoute = returnRoute;
    }

    /**
     * @return the stop
     */
    public String getStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(String stop) {
        this.stop = stop;
    }

    /**
     * @return the km
     */
    public Float getKm() {
        return km;
    }

    /**
     * @param km the km to set
     */
    public void setKm(Float km) {
        this.km = km;
    }

    /**
     * @return the elevationGain
     */
    public Integer getElevationGain() {
        return elevationGain;
    }

    /**
     * @param elevationGain the elevationGain to set
     */
    public void setElevationGain(Integer elevationGain) {
        this.elevationGain = elevationGain;
    }

    /**
     * @return the difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the oracheStart
     */
    public Orache getOracheStart() {
        return oracheStart;
    }

    /**
     * @param oracheStart the oracheStart to set
     */
    public void setOracheStart(Orache oracheStart) {
        this.oracheStart = oracheStart;
    }

    /**
     * @return the oracheStop
     */
    public Orache getOracheStop() {
        return oracheStop;
    }

    /**
     * @param oracheStop the oracheStop to set
     */
    public void setOracheStop(Orache oracheStop) {
        this.oracheStop = oracheStop;
    }

    /**
     * @return the puertos
     */
    public List<PuertoInfo> getPuertos() {
        return puertos;
    }

    /**
     * @param puertos the puertos to set
     */
    public void setPuertos(List<PuertoInfo> puertos) {
        this.puertos = puertos;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SalidaInfo [date=" + date + ", route=" + route
		+ ", returnRoute=" + returnRoute + ", stop=" + stop + ", km="
		+ km + ", elevationGain=" + elevationGain + ", difficulty="
		+ difficulty + ", type=" + type + ", oracheStart="
		+ oracheStart + ", oracheStop=" + oracheStop + ", puertos="
		+ puertos + "]";
    }

}
