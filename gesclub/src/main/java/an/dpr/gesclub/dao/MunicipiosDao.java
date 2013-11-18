package an.dpr.gesclub.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.domain.Municipio;
import an.dpr.gesclub.jpa.repository.MunicipiosRepository;

/**
 * Dao de municipios ine
 * @author rsaez
 *
 */
public class MunicipiosDao {
    
    private static final Logger log = Logger.getLogger(MunicipiosDao.class);

    @Autowired 
    private MunicipiosRepository repo;
    
    public Municipio findByIneCode(String ineCode){
	log.debug("inicio");
	return repo.findByIneCode(ineCode);
    }
    
    public List<Municipio> findByProvincia(String ineCode){
	log.debug("inicio");
	return repo.findByIneCodeLike(ineCode+"%");
    }
    
    public void insert(Municipio municipio){
	repo.save(municipio);
    }
    
    public MunicipiosRepository getRepo() {
	return repo;
    }
    
    public void setRepo(MunicipiosRepository repo) {
	this.repo = repo;
    }

}
