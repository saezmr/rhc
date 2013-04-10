package an.dpr.enbizzi.jparepository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.CalendarVersion;

/**
 * @author rsaez
 */
public interface CalendarVersionRespository extends
	CrudRepository<CalendarVersion, Long> {
    
    public CalendarVersion findByActive(Boolean active);

}
