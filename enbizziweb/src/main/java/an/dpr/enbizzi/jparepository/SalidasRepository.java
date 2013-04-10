package an.dpr.enbizzi.jparepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.enbizzi.domain.Noticia;
import an.dpr.enbizzi.domain.Salida;

/**
 * @author rsaez
 */
public interface SalidasRepository extends CrudRepository<Salida, Long> {

    @Query(" SELECT s FROM Salida s " +
		" WHERE s.date = :date" +
		" AND s.calendarVersion.active=true")
    public Salida findByDate(@Param("date") Date date);

    @Query(" SELECT s FROM Salida s " +
    		" WHERE s.date BETWEEN :ini AND :fin" +
    		" AND s.calendarVersion.active=true")
    List<Salida> findBetweenDates(@Param("ini") Date ini, @Param("fin") Date fin);
}
