package an.dpr.enbizzi.jparepository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Difficulty;
import an.dpr.enbizzi.domain.Noticia;

/**
 * @author rsaez
 */
public interface DifficultyRespository  extends CrudRepository<Difficulty, Long> {

}
