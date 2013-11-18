package an.dpr.gesclub.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.domain.Club;
import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.jpa.repository.SalidasRepository;
import an.dpr.gesclub.util.UtilFecha;

/**
 * DAO de salidas
 * 
 * @author rsaez
 * 
 */
public class SalidasDao {

    private static final Logger log = Logger.getLogger(SalidasDao.class);
    @Autowired
    SalidasRepository repo;

    public Salida save(Salida salida) {
	return repo.save(salida);
    }
    
    public boolean delete(Salida salida) {
	boolean ret = false;
	try{
	    Salida sfd = repo.findByIdSalida(salida.getIdSalida());
	    if (sfd != null){
		repo.delete(sfd);
		ret = true;
	    }
	} catch(Exception e){
	    log.error("Error borrando salida"+salida.getIdSalida(), e);
	}
	return ret;
    }

    public Salida findOne(Long id) {
	return repo.findOne(id);
    }

    public List<Salida> findByDate(Date fechaIni, Date fechaFin, Long clubId) {
	return repo.findBetweenDates(fechaIni, fechaFin, clubId);
    }
    
    public List<Salida> findByDateAndTipo(Date fechaIni, Date fechaFin, String tipo, Long clubId) {
	log.debug("params: fechaIni "+fechaIni+", fechaFin "+fechaFin+", tipo "+tipo+", clubId "+clubId);
	return repo.findBetweenDatesType(fechaIni, fechaFin, tipo, clubId);
    }

    public List<Salida> findByMonth(Integer month, Integer year,
	    TipoSalida tipo, Long clubId) {
	Date[] fechas = getFechasBusquedaMes(month, year);
	return repo.findBetweenDates(fechas[0], fechas[1], clubId);
    }

    public List<Salida> findNext(TipoSalida tipo, Long clubId) {
	List<Salida> list = null;
	Date hoy = Calendar.getInstance().getTime();
	Date f7d = UtilFecha.sumaDias(hoy, 7);
	if (tipo == null) {
	    list = repo.findBetweenDates(UtilFecha.quitarHora(hoy),
		    UtilFecha.quitarHora(f7d), clubId);
	} else {
	    list = repo.findBetweenDatesType(UtilFecha.quitarHora(hoy),
		    UtilFecha.quitarHora(f7d), tipo.name(), clubId);
	}
	return list;
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

    public List<Salida> findAll(Club club) {
	return (List<Salida>) repo.findByClub(club);
    }

    public List<Salida> findByTipo(String tipo,Club club) {
	return (List<Salida>) repo.findByTipoAndClub(tipo, club);
    }

}
