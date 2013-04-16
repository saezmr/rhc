package an.dpr.enbizzi.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Fluvi;

/**
 * Repositorio de fluvis
 * @author rsaez
 *
 */
public interface FluvisRepository extends CrudRepository<Fluvi, Long>{

    @Query("SELECT f FROM Fluvi f WHERE f.fecha BETWEEN :ini AND :fin")
    List<Fluvi> findByDates(Date ini, Date fin);

    
}
