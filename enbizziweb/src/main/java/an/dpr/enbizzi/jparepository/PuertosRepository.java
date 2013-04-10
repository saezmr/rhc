package an.dpr.enbizzi.jparepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Puerto;

/**
 * Crud repositorio de puertos
 * @author rsaez
 *
 */
public interface PuertosRepository extends CrudRepository<Puerto, Long>{

    public List<Puerto> findByProvincia(String provincia);
    
    @Query("SELECT p FROM Puerto p WHERE p.fechaAlta BETWEEN :ini AND :fin")
    public List<Puerto> findByFechaAlta(Date ini, Date fin);
    
    @Query("SELECT p FROM Puerto p WHERE p.desnivelMedio > :min")
    public List<Puerto> findByDesnivelMedio(Float min);

    @Query("SELECT p FROM Puerto p WHERE p.desnivelMetros > :min")
    public List<Puerto> findByDesnivel(Integer min);
    
}
