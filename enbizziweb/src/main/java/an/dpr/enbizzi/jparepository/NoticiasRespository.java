package an.dpr.enbizzi.jparepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.enbizzi.domain.Noticia;

/**
 * @author rsaez
 */
public interface NoticiasRespository  extends CrudRepository<Noticia, Long> {

    @Query("SELECT n FROM Noticia n " +
    	"WHERE n.fechaPublicacion BETWEEN :ini AND :fin")
    List<Noticia> findBetweenDates(
	    @Param("ini") Date ini, 
	    @Param("fin") Date fin);

}
