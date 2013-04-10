package an.dpr.enbizzi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import an.dpr.enbizzi.dao.NoticiasDAO;
import an.dpr.enbizzi.domain.Noticia;
import an.dpr.enbizzi.jparepository.NoticiasRespository;

/**
 * Servicio REST de noticias de enbizzi
 * 
 * @author rsaez
 */
@Path("/privatenoticiasws/")
public class PrivateNoticiasService {

	private static final Logger log = Logger.getLogger(PrivateNoticiasService.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy");
	
	private NoticiasDAO dao;

	public PrivateNoticiasService() {
		log.debug("new instance");
	}

	private NoticiasDAO getDAO() {
//		ApplicationContext context = new FileSystemXmlApplicationContext(
//				new String[] { "C:\\workspace\\andpr\\EnbizziAppWeb\\src\\main\\webapp\\WEB-INF\\spring-context.xml" });
//		NoticiasDAO dao = (NoticiasDAO) context.getBean("noticiasDAO");
		return dao;
	}
	
	@GET
	@Path("/noticia/{id}")
	public Noticia getNoticia(@PathParam("id") String id) {
		log.debug("init");
		Noticia ret = getDAO().findOne(Long.parseLong(id));
		log.debug(ret);
		return ret;
	}

	@Produces("application/json")
	@GET
	@Path("/noticiajson/{id}")
	public Noticia getNoticiaJson(@PathParam("id") String id) {
		log.debug("init");
		Noticia ret = getDAO().findOne(Long.parseLong(id));
		log.debug(ret);
		return ret;
	}

	@PUT
	@Path("/noticias/")
	public Response updateNoticia(@FormParam("idNoticia") String idNoticia,
			@FormParam("titulo") String titulo,
			@FormParam("entradilla") String entradilla,
			@FormParam("cuerpo") String cuerpo,
			@FormParam("fechaPublicacion") String fechaPublicacion,
			@FormParam("publicada") Boolean publicada) {
		log.debug("inicio");
		Response ret = null;
		// TODO implementar
		Date fechaCreacion = null;
		Noticia noticia = null;
		try {
			noticia = Noticia.createNoticia(Long.parseLong(idNoticia), titulo,
					entradilla, cuerpo, fechaCreacion,
					sdf.parse(fechaPublicacion), publicada);
			noticia = getDAO().save(noticia);
		} catch (NumberFormatException e) {
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		}
		log.debug(noticia);
		if (noticia != null) {
			ret = Response.ok().build();
		} else {
			ret = Response.notModified().build();
		}
		return ret;
	}

	@POST
	@Path("/noticias/")
	public Response addNoticia(
			@FormParam("titulo") String titulo,
			@FormParam("entradilla") String entradilla,
			@FormParam("cuerpo") String cuerpo,
			@FormParam("fechaPublicacion") String fechaPublicacion,
			@FormParam("publicada") Boolean publicada) {
		log.debug("inicio");
		Response ret = null;
		// TODO implementar
		Date fechaCreacion = Calendar.getInstance().getTime();
		Noticia noticia = null;
		try {
			noticia = Noticia.createNoticia(null, titulo, entradilla, cuerpo,
					fechaCreacion, sdf.parse(fechaPublicacion), publicada);
			noticia = getDAO().save(noticia);
		} catch (NumberFormatException e) {
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		}
		log.debug(noticia);
		ret = Response.ok(noticia).build();
		return ret;

	}

	@DELETE
	@Path("/noticias/{id}")
	public Response deleteNoticia(@PathParam("id") String id) {
		log.debug("inicio");
		Response ret = null;
		// TODO implementar
		return ret;

	}
	
	public NoticiasDAO getDao() {
		return dao;
	}

	@Autowired
	public void setDao(NoticiasDAO dao) {
		log.debug("setDAO("+dao+")");
		this.dao = dao;
	}


}
