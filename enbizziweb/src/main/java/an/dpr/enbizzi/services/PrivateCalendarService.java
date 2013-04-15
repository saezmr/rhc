package an.dpr.enbizzi.services;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
@Path("/private/calendarWS/")
public class PrivateCalendarService {

    private static final Logger log = Logger
	    .getLogger(PrivateCalendarService.class);

    @Autowired XMLCalendarConverter xmlCalendarConverter;
    @Autowired SalidasDAO dao;

    @POST
    @Path("/admin/cargarCalendario/")
    public String cargarCalendario(@FormParam("xmlCalendar") String xmlCalendar) {
	StringBuilder result = new StringBuilder();
	try {
	    List<Salida> list = xmlCalendarConverter
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

}
