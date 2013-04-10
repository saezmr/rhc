package an.dpr.enbizzi.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.CalendarVersion;
import an.dpr.enbizzi.domain.Noticia;
import an.dpr.enbizzi.domain.Salida;
import an.dpr.enbizzi.jparepository.CalendarVersionRespository;
import an.dpr.enbizzi.jparepository.SalidasRepository;
import an.dpr.util.UtilFecha;

/**
 * 
 * @author rsaez
 */
public class SalidasDAO {

    private static final Logger log = Logger.getLogger(SalidasDAO.class);

    @Autowired
    SalidasRepository repo;
    @Autowired
    CalendarVersionRespository cvRepo;

    public SalidasDAO() {
	log.debug("new instance" + repo);
    }

    public Salida save(Salida noticia) {
	return repo.save(noticia);
    }

    public Salida findOne(Long id) {
	return repo.findOne(id);
    }

    public Salida findByDate(Date fecha) {
	return repo.findByDate(UtilFecha.quitarHora(fecha));
    }

    public List<Salida> findByMonth(Integer month, Integer year) {
	Date[] fechas = getFechasBusquedaMes(month, year);
	return repo.findBetweenDates(fechas[0], fechas[1]);
    }

    public List<Salida> findNext() {
	Date hoy = Calendar.getInstance().getTime();
	Date f7d = UtilFecha.sumaDias(hoy, 7);
	return repo.findBetweenDates(hoy, f7d);
    }

    private static Date[] getFechasBusquedaMes(Integer month, Integer year) {
	Date ini;
	Date fin;
	Calendar c = Calendar.getInstance();
	c.set(year, month - 1, 1, 0, 0, 0);
	ini = c.getTime();
	c.add(Calendar.MONTH, 1);
	fin = c.getTime();
	return new Date[] { ini, fin };
    }

    public CalendarVersion newCalendarVersion() {
	CalendarVersion cv = cvRepo.findByActive(true);
	if (cv != null) {
	    cv.setActive(false);
	    cvRepo.save(cv);
	}
	cv = new CalendarVersion();
	cv.setActive(true);
	return cvRepo.save(cv);
    }

    // public Iterable<Salida> findByMonth(Integer id){
    // return repo.findAll(predicateByMonth(id));
    // }
    //
    // private Predicate predicateByMonth(Integer id){
    // return null;
    // }
}
