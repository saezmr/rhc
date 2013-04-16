package an.dpr.enbizzi.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Difficulty;

/**
 * @author rsaez
 */
public interface DifficultyRepository  extends CrudRepository<Difficulty, Long> {

    public Difficulty findByName(String name);
}
