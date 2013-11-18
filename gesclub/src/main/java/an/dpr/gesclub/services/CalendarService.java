package an.dpr.gesclub.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.dao.SalidasDao;
import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.ext.InfoAemet;
import an.dpr.gesclub.services.beans.Orache;
import an.dpr.gesclub.services.beans.SalidaInfo;

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
    @Autowired SalidasDao dao;
    @Autowired InfoAemet aemet;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * cita del dia indicado (formato yyyymmdd)
     * 
     * @param id
     * @return
     */
    @GET
    @Produces("application/xml")
    @Path("/salida/{id}")
    public SalidaInfo getSalida(@PathParam("id") String id) {
	SalidaInfo salida = obtenerSalida(id);
	return salida;
    }

    @GET
    @Produces("application/json")
    @Path("/salidajson/{id}")
    public SalidaInfo getSalidaJSon(@PathParam("id") String id) {
	SalidaInfo salida = obtenerSalida(id);
	return salida;
    }
    
    private SalidaInfo obtenerSalida(String idSalida){
	SalidaInfo si = null;
	if (idSalida!= null) {
	    try{
		Salida salida = dao.findOne(Long.valueOf(idSalida));
		if (salida != null){
		    si = SalidaInfo.getBean(salida);
		    Orache orache = aemet.getPrediccion(salida);
		    if (orache != null){
			si.setOrache(orache);
		    }
		}
	    } catch(NumberFormatException e){
		log.error(idSalida+" no es un id de tipo Long valido", e);
	    }
	}
	return si != null ? si : new SalidaInfo();
    }

    /**
     * calendario del mes indicado
     * 
     * @param id
     * @return
     */
    @GET
    @Produces("application/xml")
    @Path("/mes/{month}, {tipo}, {club}")
    public List<SalidaInfo> getCalendarioMes(
	    @PathParam("month") String month,
	    @PathParam("tipo") String tipo,
	    @PathParam("club") String club
	    ) {
	log.debug("init");
	List<Salida> ret = dao.findByMonth(getMes(month), getAnyo(month),
		getTipo(tipo), Long.valueOf(club));
	log.debug(ret);
	return SalidaInfo.getList(ret);
    }
    
    public Integer getMes(String param){
	Integer ret = null;
	try{
	    if (param != null){
		String mes = param.length() >= 2 
			? param.substring(0,2)
			: param;
		ret = Integer.valueOf(mes)-1;
	    }
	} catch(NumberFormatException e){
	    log.error(param+" no contiene un mes valido", e);
	}
	return ret;
    }

    public Integer getAnyo(String param){
	Integer ret = null;
	try{
	    if (param != null 
		    & Integer.valueOf(6).equals(Integer.valueOf(param.length()))
		    ){
		String anyo= param.substring(2);
		ret = Integer.valueOf(anyo);
	    } else {
		ret = Calendar.getInstance().get(Calendar.YEAR);
	    }
	} catch(NumberFormatException e){
	    log.error(param+" no contiene un anyo valido", e);
	}
	return ret;
    }

    @GET
    @Produces("application/xml")
    @Path("/mes/{month},{year}")
    public List<SalidaInfo> getCalendarioMesJson(
	    @PathParam("month") String month,
	    @PathParam("tipo") String tipo,
	    @PathParam("club") String club) {
	return getCalendarioMes(month, tipo, club);
    }
    /**
     * proximas citas, de los proximos 7 dias
     * 
     * @return
     */
    @GET
    @Produces("application/xml")
    @Path("/proximas/{tipo}")
    public List<SalidaInfo> getProximasCitas(
	    @PathParam("tipo") String tipo,
	    @PathParam("club") String club
	    ) {
	log.debug("init, params: tipo="+tipo);
	List<Salida> ret = null;
	try{
	    TipoSalida type = getTipo(tipo);
	    ret = dao.findNext(type, Long.valueOf(club));
	} catch(NumberFormatException e){
	    log.error(club+" no es un idClub valido de tipo Long", e);
	}
	log.debug(ret);
	return SalidaInfo.getList(ret);
    }

    @GET
    @Produces("application/json")
    @Path("/proximasjson/")
    public List<SalidaInfo> getProximasCitasJson(
	    @PathParam("tipo") String tipo,
	    @PathParam("club") String club) {
	log.debug("init");
	return getProximasCitas(tipo, club);
    }

    
    private TipoSalida getTipo(String tipo) {
	TipoSalida type = null;
	if (tipo != null){
	    try{
		type = TipoSalida.valueOf(tipo);
	    } catch(IllegalArgumentException e){
		log.info(tipo+" no es un tipo de ciclismo valido");
	    }
	}
	return type;
    }
}
