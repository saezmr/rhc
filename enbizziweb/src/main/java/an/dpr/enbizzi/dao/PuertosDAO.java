package an.dpr.enbizzi.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.Puerto;
import an.dpr.enbizzi.jparepository.PuertosRepository;

public class PuertosDAO {
    
    private static final Logger log = Logger.getLogger(PuertosDAO.class);

    @Autowired
    PuertosRepository repo;
    
    public PuertosDAO(){
    }
    
    public Puerto save(Puerto p){
	return repo.save(p);
    }
    
    public List<Puerto> findByDureza(Integer porcentajeMinimo){
	List<Puerto> ret = null;
	//TODO implementar
	return ret;
    }
    
    
    public List<Puerto> findByProvincia(String provincia){
	return repo.findByProvincia(provincia);
    }
    
    public List<Puerto> findLast(){
	Calendar c = Calendar.getInstance();
	List<Puerto> ret = null;
	//TODO implementar
	return ret;
    }
    
    
    
    
}
