package an.dpr.gesclub.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.gesclub.domain.Club;
import an.dpr.gesclub.domain.Salida;

/**
 * @author rsaez
 */
public interface SalidasRepository extends CrudRepository<Salida, Long> {

    @Query("FROM Salida s " + " WHERE s.date = :date AND s.club.id=:clubId")
    public Salida findByDate(@Param("date") Date date,
	    @Param("clubId") Long clubId);

    @Query("FROM Salida s " + " WHERE s.date BETWEEN :ini AND :fin AND s.club.id=:clubId")
    public List<Salida> findBetweenDates(@Param("ini") Date ini,
	    @Param("fin") Date fin, @Param("clubId") Long clubId);

    @Query("FROM Salida s " + " WHERE s.date BETWEEN :ini AND :fin"
	    + " AND s.tipo = :tipo AND s.club.id=:clubId")
    public List<Salida> findBetweenDatesType(@Param("ini") Date ini,
	    @Param("fin") Date fin, @Param("tipo") String tipo,
	    @Param("clubId") Long clubId);

    public Page<Salida> findAll(Pageable pageable);

    public List<Salida> findByClub(Club club);

    public List<Salida> findByTipoAndClub(String tipo, Club club);

    Salida findByIdSalida(Long idSalida);
}
