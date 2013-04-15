package an.dpr.enbizzi.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.Fluvi;
import an.dpr.enbizzi.jpa.repository.FluvisRepository;

/**
 * DAO para los fluvis
 * @author rsaez
 *
 */
public class FluvisDAO {

    @Autowired FluvisRepository repo;
    
    public FluvisDAO(){}

    public Fluvi getFluvi(Long id) {
	return repo.findOne(id);
    }

    public List<Fluvi> getFluvis(Integer anyo) {
	Calendar cal = Calendar.getInstance();
	cal.set(anyo, 0, 1, 0, 0);
	Date ini = cal.getTime();
	cal.set(anyo+1, 0, 1, 0, 0);
	Date fin = cal.getTime();
	return repo.findByDates(ini, fin);
    }
    
    
}
