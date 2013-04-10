package an.dpr.enbizzi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import an.dpr.enbizzi.beans.Orache;
import an.dpr.enbizzi.dao.SalidasDAO;
import an.dpr.enbizzi.domain.Salida;
import an.dpr.enbizzi.external.InfoAemet;

/**
 * Servicio REST de calendario de enbizzi varios WS todos GET, solo es de
 * consulta
 * 
 * @author rsaez
 */
@Path("/calendarWS/")
public class CalendarService {

    private static final Logger log = Logger.getLogger(CalendarService.class);
    private SalidasDAO dao;
    private InfoAemet aemet;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * cita del dia indicado (formato yyyymmdd)
     * 
     * @param id
     * @return
     */
    @GET
    @Path("/salida/{dia}")
    public Salida getSalida(@PathParam("dia") String dia) {
	Salida bcal = new Salida();
	Date fecha = parseFecha(dia);
	if (fecha != null) {
	    bcal = dao.findByDate(fecha);
	    if (bcal != null){
		Orache[] orache = aemet.getPrediccion(bcal);
		if (orache != null){
		    bcal.setOracheStart(orache[0]);
		    bcal.setOracheStop(orache[1]);
		}
		bcal.limpiarInfo();
	    }
	}
	return bcal != null ? bcal : new Salida();
    }

    private Date parseFecha(String fecha) {
	try {
	    return fecha != null ? sdf.parse(fecha) : null;
	} catch (ParseException e) {
	    log.error("", e);
	    return null;
	}
    }

    /**
     * calendario del mes indicado
     * 
     * @param id
     * @return
     */
    @GET
    @Path("/mes/{month},{year}")
    public List<Salida> getCalendarioMes(@PathParam("month") String month,
	    @PathParam("year") String year) {
	log.debug("init");
	List<Salida> ret = dao.findByMonth(Integer.valueOf(month),
		Integer.valueOf(year));
	for(Salida s : ret){
	    s.limpiarInfo();
	}
	log.debug(ret);
	return ret;
    }

    /**
     * proximas citas, de los proximos 7 dias
     * 
     * @return
     */
    @GET
    @Path("/proximas/")
    public List<Salida> getProximasCitas() {
	log.debug("init");
	List<Salida> ret = dao.findNext();
	for(Salida s : ret){
	    s.limpiarInfo();
	}
	log.debug(ret);
	return ret;
    }

    /**
     * @return the dao
     */
    public SalidasDAO getDao() {
	return dao;
    }

    /**
     * @param dao
     *            the dao to set
     */
    public void setDao(SalidasDAO dao) {
	this.dao = dao;
    }

    /**
     * @return the aemet
     */
    public InfoAemet getAemet() {
        return aemet;
    }

    /**
     * @param aemet the aemet to set
     */
    public void setAemet(InfoAemet aemet) {
        this.aemet = aemet;
    }
}
