package an.dpr.enbizzi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.beans.CyclingType;
import an.dpr.enbizzi.beans.Orache;
import an.dpr.enbizzi.beans.SalidaInfo;
import an.dpr.enbizzi.dao.SalidasDAO;
import an.dpr.enbizzi.domain.Salida;
import an.dpr.enbizzi.external.InfoAemet;

/**
 * Servicio REST de calendario de enbizzi varios WS todos GET, solo es de
 * consulta
 * 
 * TODO no se ha contemplado el calendario BTT!!
 * 
 * @author rsaez
 */
@Path("/calendarWS/")
public class CalendarService {

    private static final Logger log = Logger.getLogger(CalendarService.class);
    @Autowired SalidasDAO dao;
    @Autowired InfoAemet aemet;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * cita del dia indicado (formato yyyymmdd)
     * 
     * @param id
     * @return
     */
    @GET
    @Path("/salida/{dia}")
    public SalidaInfo getSalida(@PathParam("dia") String dia) {
	Salida salida = obtenerSalida(dia);
	return SalidaInfo.getBean(salida);
    }

    @GET
    @Produces("application/json")
    @Path("/salidajson/{dia}")
    public SalidaInfo getSalidaJSon(@PathParam("dia") String dia) {
	Salida salida = obtenerSalida(dia);
	return SalidaInfo.getBean(salida);
    }
    
    private Salida obtenerSalida(String dia){
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
    public List<SalidaInfo> getCalendarioMes(@PathParam("month") String month,
	    @PathParam("year") String year) {
	log.debug("init");
	List<Salida> ret = dao.findByMonth(Integer.valueOf(month),
		Integer.valueOf(year));
	for(Salida s : ret){
	    s.limpiarInfo();
	}
	log.debug(ret);
	return SalidaInfo.getList(ret);
    }

    @GET
    @Path("/mes/{month},{year}")
    public List<SalidaInfo> getCalendarioMesJson(
	    @PathParam("month") String month,
	    @PathParam("year") String year) {
	return getCalendarioMes(month, year);
    }
    /**
     * proximas citas, de los proximos 7 dias
     * 
     * @return
     */
    @GET
    @Path("/proximas/{tipo}")
    public List<SalidaInfo> getProximasCitas(@PathParam("tipo") String tipo) {
	log.debug("init, params: tipo="+tipo);
	CyclingType type = getTipo(tipo);
	List<Salida> ret = dao.findNext(type);
	log.debug(ret);
	return SalidaInfo.getList(ret);
    }

    @GET
    @Produces("application/json")
    @Path("/proximasjson/")
    public List<SalidaInfo> getProximasCitasJson(@PathParam("tipo") String tipo) {
	log.debug("init");
	return getProximasCitas(tipo);
    }

    
    private CyclingType getTipo(String tipo) {
	CyclingType type = null;
	if (tipo != null){
	    try{
		type = CyclingType.valueOf(tipo);
	    } catch(IllegalArgumentException e){
		log.info(tipo+" no es un tipo de ciclismo valido");
	    }
	}
	return type;
    }
}
