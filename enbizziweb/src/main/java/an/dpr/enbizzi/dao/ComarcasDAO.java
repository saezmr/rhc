package an.dpr.enbizzi.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.Comarca;
import an.dpr.enbizzi.jpa.repository.ComarcasRepository;
import an.dpr.enbizzi.util.ComarcasContract;
import an.dpr.enbizzi.util.ComarcasContract.Paises;
import an.dpr.enbizzi.util.ComarcasContract.Provincias;

public class ComarcasDAO {

    private static final Logger log = Logger.getLogger(ComarcasDAO.class);

    @Autowired
    ComarcasRepository repo;

    public ComarcasDAO() {
    }

    public Comarca findByNombre(String nombre) {
	log.debug("inicio");
	return repo.findByNombre(nombre);
    }

    public void createComarcas() {
	log.debug("inicio");
	List<Comarca> lc = ComarcasContract.getComarcasAragon();
	for(Comarca c : lc){
	    Comarca fc = repo.findByNombre(c.getNombre());
	    if (fc == null){
		Provincias p = Provincias.getById(c.getCodProvincia());
		c.setProvincia(p!= null ? p.name() : null);
		Paises ps = Paises.getById(34);
		c.setPais(ps!= null ? ps.name() : null);
		repo.save(c);
	    }
	}
    }

}