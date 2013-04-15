package an.dpr.enbizzi.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.FluvisDAO;
import an.dpr.enbizzi.domain.Fluvi;
import an.dpr.util.UtilFecha;

/**
 * Servicio de informacion de fluvs de enbizzi
 * 
 * @author rsaez
 * 
 */
@Path("/fluvisWS/")
public class FluvisService {

    private static final Logger log = Logger.getLogger(FluvisService.class);
    
    @Autowired FluvisDAO dao;

    @GET
    @Path("/getFluvi/{id}")
    public Fluvi getFluvi(@PathParam("id") String id) {
	Fluvi ret = null;
	try {
	    ret = dao.getFluvi(Long.valueOf(id));
	} catch (Exception e) {
	    log.error("Error solicitando info de fluvi", e);
	    ret = new Fluvi();
	}
	return ret;
    }

    @GET
    @Path("/getFluvis/{year}")
    public List<Fluvi> getFluvis(@PathParam("year") String year) {
	List<Fluvi> ret = null;
	try {
	    Integer anyo = getAnyo(year);
	    ret = dao.getFluvis(anyo);
	    
	} catch (Exception e) {
	    log.error("Error solicitando info de fluvi", e);
	    ret = new ArrayList<Fluvi>();
	}
	return ret;
    }

    private Integer getAnyo(String year) {
	Integer anyo = null;
	if (year != null) {
	    try{
		anyo = Integer.valueOf(year);
	    } catch(NumberFormatException e){
		log.error("error formato a�o", e);
	    }
	}
	if (anyo == null) {
	    anyo = UtilFecha.getAnyoActual();
	}
	return anyo;
    }

}
