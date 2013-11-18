package an.dpr.gesclub.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.beans.ClasificacionSalidas;
import an.dpr.gesclub.beans.CyclingType;
import an.dpr.gesclub.beans.ItemClasificacionSalidas;
import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.domain.Club;
import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.jpa.repository.SociosRepository;
import an.dpr.gesclub.util.UtilFecha;

public class SociosDao {

    private static final Logger log = Logger.getLogger(SociosDao.class);
    @Autowired
    private SociosRepository repo;

    public SociosDao() {

    }

    public Socio save(Socio soci) {
	Socio ret;
	log.debug("save socio:"+soci);
	try {
	    ret = repo.save(soci);
	} catch (Exception e) {
	    ret = null;
	    log.error("Error guardando Socio " + soci, e);
	}
	return ret;
    }
    
    public ClasificacionSalidas getClasificacionSalidas(Long clubId, int year, TipoSalida tipo){
	ClasificacionSalidas ret = new ClasificacionSalidas();
	ret.setYear(year);
	ret.setTipo(tipo);
	ret.setClubId(clubId);
	List<Socio> listado = repo.findActivos(clubId);
	    ret = addSocios(ret, listado, year, tipo);
	return ret;
    }

    private ClasificacionSalidas addSocios(ClasificacionSalidas ret, List<Socio> listado, Integer year, TipoSalida tipo) {
	if (listado== null || ret == null || tipo == null)
	    throw new IllegalArgumentException("parametros incorrectos");
	for(Socio socio : listado){
	    ItemClasificacionSalidas item = new ItemClasificacionSalidas();
	    item.setSocio(socio.getNombreCompleto());
	    item.setNumSocio(socio.getNumSocio());
	    int numSalidas = 0;
	    for(Salida salida : socio.getSalidas()){
		Calendar cal = Calendar.getInstance();
		cal.setTime(salida.getDate());
		Integer yearSalida = cal.get(Calendar.YEAR);
		if (tipo.name().equals(salida.getTipo())
			&& yearSalida.equals(year)
			){
		    numSalidas++;
		}
	    }
	    item.setNumeroSalidas(numSalidas);
	    item.setPosicion(socio.getNumSocio());
	    ret.addItem(item);
	}
	
	return ret;
    }

    /**
     * Obtiene el listado de socios en activos
     * @return
     */
    public List<Socio> getSociosActivos(Long clubId){
	List<Socio> listado = repo.findActivos(clubId);
	return listado;
    }

    public SociosRepository getRepo() {
	return repo;
    }

    public void setRepo(SociosRepository repo) {
	this.repo = repo;
    }

    public boolean delete(Socio s) {
	boolean ret = false;
	try {
	    Socio pb = repo.findByIdSocio(s.getIdSocio());
	    if (pb != null) {
		repo.delete(pb);
		ret = true;
	    }
	} catch (Exception e) {
	    log.error("Error borrando Socio " + s, e);
	}
	return ret;
    }

    public Socio getSocioByUsername(String username) {
	return repo.findByUsername(username);
    }

    public Socio getSocioById(Long id){
	return repo.findByIdSocio(id);
    }

    public Socio getSocioByNumSocio(Integer numSocio) {
	return repo.findByNumSocio(numSocio);
    }
    
}
