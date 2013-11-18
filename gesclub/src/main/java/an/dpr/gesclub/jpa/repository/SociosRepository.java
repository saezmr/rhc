package an.dpr.gesclub.jpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.gesclub.domain.Socio;

public interface SociosRepository extends CrudRepository<Socio, Long> {

    Socio findByIdSocio(Long idSocio);
    
    Set<Socio> findAll();
    
    @Query("FROM Socio s WHERE s.fechaBaja = null and s.club.id=:clubId")
    List<Socio> findActivos(@Param("clubId") Long clubId);

    Socio findByUsername(String username);

    Socio findByNumSocio(Integer numSocio);

    // clasificacion salidas
    // select row_number() over (order by count(ss.salidas_idsalida) desc),
    // s.nombre, count(ss.salidas_idsalida) from socios s inner join
    // salidas_socios ss on ss.socios_idsocio = s.idsocio group by s.idsocio;

}
