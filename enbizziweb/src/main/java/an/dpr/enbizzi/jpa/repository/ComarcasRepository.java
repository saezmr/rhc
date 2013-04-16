package an.dpr.enbizzi.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Comarca;

/**
 * crud repository de comarcas
 * @author rsaez
 */
public interface ComarcasRepository extends CrudRepository<Comarca, Long>{

    public Comarca findByNombre(String nombre);

}
