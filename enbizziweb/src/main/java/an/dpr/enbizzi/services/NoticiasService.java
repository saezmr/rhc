package an.dpr.enbizzi.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.NoticiasDAO;
import an.dpr.enbizzi.domain.Noticia;

/**
 * Servicio REST de noticias de enbizzi
 * 
 * @author rsaez
 */
@Path("/noticiasws/")
public class NoticiasService {

    private static final Logger log = Logger.getLogger(NoticiasService.class);

    @Autowired
    NoticiasDAO dao;

    public NoticiasService() {
	log.debug("new instance");
    }

    @GET
    @Path("/noticia/{id}")
    public Noticia getNoticia(@PathParam("id") String id) {
	log.debug("init");
	Noticia ret = dao.findOne(Long.parseLong(id));
	log.debug(ret);
	return ret;
    }

    @GET
    @Path("/noticia/mes/{month},{year}")
    public List<Noticia> getNoticiaMes(@PathParam("month") String month,
	    @PathParam("year") String year) {
	log.debug("init");
	List<Noticia> ret = dao.findByMonth(Integer.valueOf(month),
		Integer.valueOf(year));
	log.debug(ret);
	return ret;
    }

    @Produces("application/json")
    @GET
    @Path("/noticiajson/{id}")
    public Noticia getNoticiaJson(@PathParam("id") String id) {
	log.debug("init");
	Noticia ret = dao.findOne(Long.parseLong(id));
	log.debug(ret);
	return ret;
    }

}
