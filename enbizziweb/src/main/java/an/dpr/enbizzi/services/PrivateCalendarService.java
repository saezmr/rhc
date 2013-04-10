package an.dpr.enbizzi.services;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import an.dpr.enbizzi.calendar.XMLCalendarConverter;
import an.dpr.enbizzi.dao.SalidasDAO;
import an.dpr.enbizzi.domain.CalendarVersion;
import an.dpr.enbizzi.domain.Salida;

/**
 * Servicio REST de calendario de enbizzi varios WS todos GET, solo es de
 * consulta
 * 
 * @author rsaez
 */
@Path("/privateCalendarWS/")
public class PrivateCalendarService {

    private static final Logger log = Logger
	    .getLogger(PrivateCalendarService.class);

    private SalidasDAO dao;

    @POST
    @Path("/cargarCalendario/")
    public String cargarCalendario(@FormParam("xmlCalendar") String xmlCalendar) {
	StringBuilder result = new StringBuilder();
	try {
	    List<Salida> list = XMLCalendarConverter
		    .getCalendarViaNewPullParser(xmlCalendar);
	    CalendarVersion cv = dao.newCalendarVersion();
	    for (Salida cita : list) {
		cita.setCalendarVersion(cv);
		dao.save(cita);
		result.append("add ").append(cita.getDate()).append(",<br>");
	    }
	} catch (Exception e) {
	    log.error("Error!!", e);
	    result.append("Error cargando calendario ").append(e.getMessage());
	}
	return result.toString();
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
}
