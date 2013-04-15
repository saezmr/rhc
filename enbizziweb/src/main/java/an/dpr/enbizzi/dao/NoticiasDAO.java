package an.dpr.enbizzi.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.Noticia;
import an.dpr.enbizzi.jpa.repository.NoticiasRespository;
import an.dpr.util.UtilFecha;

/**
 * 
 * @author rsaez
 */
public class NoticiasDAO {

    private static final Logger log = Logger.getLogger(NoticiasDAO.class);

    @Autowired
    NoticiasRespository repo;

    public NoticiasDAO() {
	log.debug("new instance" + repo);
    }

    public Noticia save(Noticia noticia) {
	return repo.save(noticia);
    }

    public List<Noticia> findByMonth(Integer month, Integer year) {
	Date[] fechas = getFechasBusquedaMes(month, year);
	return repo.findBetweenDates(fechas[0], fechas[1]);
    }

    public List<Noticia> findLast(Integer month, Integer year) {
	Calendar cal = Calendar.getInstance();
	Date fin = cal.getTime();
	Date ini = UtilFecha.sumaDias(fin, -7);
	return repo.findBetweenDates(ini, fin);
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

    public Noticia findOne(Long id) {
	return repo.findOne(id);
    }
}
