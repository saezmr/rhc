package an.dpr.enbizzi.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import an.dpr.enbizzi.domain.Comarca;
import an.dpr.enbizzi.domain.Puerto;
import an.dpr.enbizzi.jpa.repository.PuertosRepository;

public class PuertosDAO {
    
    private static final Logger log = Logger.getLogger(PuertosDAO.class);

    private static final Integer LAST_RESULTS_DEFAULT = 5;

    @Autowired
    PuertosRepository repo;
    
    public PuertosDAO(){
    }
    
    public Puerto save(Puerto p){
	Puerto ret;
	try{
	    ret = repo.save(p);
	} catch(Exception e){
	    ret = null;
	    log.error("Error guardando puerto "+p, e);
	}
	return ret;
    }

    public Puerto udpate(Puerto p) {
	Puerto ret;
	try{
	    Puerto pb = repo.findByNombre(p.getNombre());
	    if (pb != null){
		p.setIdPuerto(pb.getIdPuerto());
		ret = repo.save(p);
	    } else {
		ret = null;
	    }
	} catch(Exception e){
	    ret = null;
	    log.error("Error guardando puerto "+p, e);
	}
	return ret;
    }
    
    /**
     * 
     * @param porcentajeMinimo desnivel en % en centesimas (se dividira por 100)
     * 	para buscar por desniveles mayores de 4,5% el valor seria 450
     * @return
     */
    public List<Puerto> findByDureza(Integer porcentajeMinimo){
	log.debug("inicio");
	List<Puerto> ret = null;
	Float desnivelMedio = new Float(porcentajeMinimo/100);
	ret = repo.findByDesnivelMedio(desnivelMedio);
	return ret;
    }
    
    
    /**
     * Buscar por comarca donde esta el puerto
     * @param provincia
     * @return
     */
    public List<Puerto> findByComarca(Comarca comarca){
	log.debug("inicio");
	return repo.findByComarca(comarca);
    }

    /**
     * Buscar por provincia donde esta el puerto
     * @param provincia
     * @return
     */
    public List<Puerto> findByProvincia(Integer codProvincia){
	log.debug("inicio");
	return repo.findByProvincia(codProvincia);
    }
    
    
    /**
     * Devuelve la info de los ultimos puertos subidos
     * @param numResultados
     * @return
     */
    public List<Puerto> findLast(){
	return this.findLast(LAST_RESULTS_DEFAULT);
    }
    
    /**
     * Devuelve la info de los ultimos puertos subidos
     * @param numResultados
     * @return
     */
    public List<Puerto> findLast(Integer numResultados){
	log.debug("inicio");
	Sort sort = new Sort(Sort.Direction.DESC, "idPuerto");
	Pageable pag = new PageRequest(0,numResultados, sort);
	Page<Puerto> page = repo.findAll(pag);
	return page.getContent();
    }

    /**
     * Busca puerto por la clave unica nombre
     * @param nombre
     * @return
     */
    public Puerto findByNombre(String nombre) {
	return repo.findByNombre(nombre);
    }
    
}
