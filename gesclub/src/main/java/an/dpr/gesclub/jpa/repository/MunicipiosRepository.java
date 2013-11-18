package an.dpr.gesclub.jpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import an.dpr.gesclub.domain.Municipio;

/**
 * Repository Spring JPA Data for municipios
 * @author rsaez
 *
 */
public interface MunicipiosRepository extends CrudRepository<Municipio, String>{

    public Municipio findByIneCode(String ineCode);
    
    public List<Municipio> findByIneCodeLike(String ineCode);
}
