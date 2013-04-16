package an.dpr.enbizzi.services;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.PuertosDAO;
import an.dpr.enbizzi.domain.Puerto;

@Path("/puertosWS/")
public class PuertosService {
    
    private static final Logger log = Logger.getLogger(PuertosService.class);
    @Autowired PuertosDAO dao;
    
    @GET
    @Path("/lastPuertos/")
    public List<Puerto> getPuertos(){
	return dao.findLast();
    }

    @GET
    @Path("/puerto/{nombre}")
    public Puerto getPuerto(@PathParam("nombre") String nombre){
	log.debug("inicio");
	return dao.findByNombre(nombre);
    }

    @POST
    @Path("/puerto/")
    public Puerto newPuerto(
	    @FormParam("idPuerto") String idPuerto,
	    @FormParam("nombre") String nombre,
	    @FormParam("descripcion") String descripcion,
	    @FormParam("idComarca") String idComarca,
	    @FormParam("km") String km,
	    @FormParam("desnivelMetros") String desnivelMetros,
	    @FormParam("desnivelMedio") String desnivelMedio,
	    @FormParam("altitud") String altitud,
	    @FormParam("imagen") String imagen,
	    @FormParam("revisado") String revisado,
	    @FormParam("fechaAlta") String fechaAlta	    
	    ){
	log.debug("inicio");
	//TODO implementar
	return null;
    }

}