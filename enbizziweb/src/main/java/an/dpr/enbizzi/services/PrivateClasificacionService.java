package an.dpr.enbizzi.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.ClasificacionSalidasDAO;

/**
 * Clasificacion de salidas WS
 * @author rsaez
 *
 */
@Path("/private/clasificacionWS/")
public class PrivateClasificacionService {

    @Autowired ClasificacionSalidasDAO dao;
    
    @POST
    @Path("/altaSalidaSocio/")
    public boolean nuevaSalidaSocio(
	    @FormParam(value = "numSocio") String numSocio,
	    @FormParam(value = "idSalida") String isSalida
	    ) {
	//TODO implemntar
	return false;
    }
}
