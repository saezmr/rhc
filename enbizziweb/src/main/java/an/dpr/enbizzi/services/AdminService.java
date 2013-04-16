package an.dpr.enbizzi.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.dao.ComarcasDAO;
import an.dpr.enbizzi.dao.TablasAuxiliaresDAO;

@Path("/private/adminWS/")
public class AdminService {

    private static final Logger log = Logger.getLogger(AdminService.class);
    
    @Autowired TablasAuxiliaresDAO taDao;
    @Autowired ComarcasDAO comDao;
    
    @GET
    @Path("/admin/inicializa/")
    public String inicializa(){
	log.debug("inicio");
	StringBuilder ret = new StringBuilder();
	taDao.createCyclingTypeValues();
	taDao.createDifficultyValues();
	comDao.createComarcas();
	ret.append("ok");
	return ret.toString();
    }
}
