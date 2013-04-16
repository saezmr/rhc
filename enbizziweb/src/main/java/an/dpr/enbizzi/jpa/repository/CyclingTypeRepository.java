package an.dpr.enbizzi.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.CyclingType;

/**
 * @author rsaez
 */
public interface CyclingTypeRepository  extends CrudRepository<CyclingType, Long> {

    public CyclingType findByName(String name);
}
