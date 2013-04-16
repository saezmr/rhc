package an.dpr.enbizzi.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.enbizzi.domain.CyclingType;
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
    public List<Salida> findBetweenDates(@Param("ini") Date ini, @Param("fin") Date fin);
    
    @Query(" SELECT s FROM Salida s " +
	    " WHERE s.date BETWEEN :ini AND :fin" +
	    " AND s.type = :tipo" +
	    " AND s.calendarVersion.active=true")
    public List<Salida> findBetweenDatesType(
	    @Param("ini") Date ini, 
	    @Param("fin") Date fin,
	    @Param("tipo") CyclingType tipo);
    
    public Page<Salida> findAll(Pageable pageable);
}
