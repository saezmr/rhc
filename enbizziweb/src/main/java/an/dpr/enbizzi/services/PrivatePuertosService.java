package an.dpr.enbizzi.services;

import java.util.Calendar;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.ComarcasDAO;
import an.dpr.enbizzi.dao.PuertosDAO;
import an.dpr.enbizzi.domain.Puerto;

@Path("/private/puertosWS/")
public class PrivatePuertosService {

    private static final Logger log = Logger
	    .getLogger(PrivatePuertosService.class);

    @Autowired PuertosDAO dao;
    @Autowired ComarcasDAO comarcasDao;
    
    @GET
    @Path("/lastPuertos/")
    public List<Puerto> getPuertos(){
	return dao.findLast();
    }

    @POST
    @Path("/puerto/")
    public Puerto newPuerto(
	    @FormParam("nombre") String nombre,
	    @FormParam("nombreExtendido") String nombreExtendido,
	    @FormParam("descripcion") String descripcion,
	    @FormParam("comarca") String comarca,
	    @FormParam("km") String km,
	    @FormParam("desnivelMetros") String desnivelMetros,
	    @FormParam("desnivelMedio") String desnivelMedio,
	    @FormParam("altitud") String altitud,
	    @FormParam("imagen") String imagen,
	    @FormParam("revisado") String revisado
	    ) {
	log.debug("inicio");
	Puerto p = getPuerto(nombre, nombreExtendido, descripcion, comarca,
		km, desnivelMetros, desnivelMedio, altitud, imagen, revisado);
	p = dao.save(p);
	return p;
    }


    @PUT
    @Path("/puerto/")
    public Puerto updatePuerto(@FormParam("nombre") String nombre,
	    @FormParam("descripcion") String descripcion,
	    @FormParam("nombreExtendido") String nombreExtendido,
	    @FormParam("comarca") String comarca,
	    @FormParam("km") String km,
	    @FormParam("desnivelMetros") String desnivelMetros,
	    @FormParam("desnivelMedio") String desnivelMedio,
	    @FormParam("altitud") String altitud,
	    @FormParam("imagen") String imagen,
	    @FormParam("revisado") String revisado
	    ) {
	log.debug("inicio");
	Puerto p = getPuerto(nombre, nombreExtendido, descripcion, comarca,
		km, desnivelMetros, desnivelMedio, altitud, imagen, revisado);
	p = dao.udpate(p);
	return p;
    }
    
    private Puerto getPuerto(String nombre, String nombreExtendido,
	    String descripcion, String comarca, String km,
	    String desnivelMetros, String desnivelMedio, String altitud,
	    String imagen, String revisado) {
	//TODO comprobar numeros formatos
	Puerto p = new Puerto();
	p.setNombre(nombre);
	p.setNombreExtendido(nombreExtendido);
	p.setDescripcion(descripcion);
	if (comarca != null && comarca.length()>0){
	    p.setComarca(comarcasDao.findByNombre(comarca));
	}
	if (km != null && km.length()>0){
	    try{
		p.setKm(Float.valueOf(km) / 100);
	    } catch(NumberFormatException e){
		log.error(km+" no es un numero");
	    }
	}
	if (desnivelMetros != null && desnivelMetros.length()>0){
	    try{
		p.setDesnivelMetros(Integer.valueOf(desnivelMetros));
	    } catch(NumberFormatException e){
		log.error(desnivelMetros+" no es un numero");
	    }
	}
	if (desnivelMedio != null && desnivelMedio.length()>0){
	    try{
		p.setDesnivelMedio(Float.valueOf(desnivelMedio) / 100);
	    } catch(NumberFormatException e){
		log.error(desnivelMedio+" no es un numero");
	    }
	}
	if (altitud != null && altitud.length()>0){
	    try{
		p.setAltitud(Integer.valueOf(altitud));
	    } catch(NumberFormatException e){
		log.error(altitud+" no es un numero");
	    }
	}
	if (imagen != null) {
	    byte[] img = Base64.decodeBase64(imagen.getBytes());
	    p.setImagen(img);
	}
	p.setRevisado(Boolean.TRUE.toString().equals(revisado));
	p.setFechaAlta(Calendar.getInstance().getTime());
	return p;
    }

    @DELETE
    @Path("/puerto/{id}")
    public boolean deletePuerto(@PathParam("id") String id) {
	log.debug("inicio");
	log.error("La funcion eliminar puerto no esta implementada");
	// TODO implementar
	return false;
    }

}
