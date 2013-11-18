package an.dpr.gesclub.beans;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.NavigableSet;
import java.util.TreeSet;

import an.dpr.gesclub.domain.Socio;

/**
 * Clasificacion de salidas
 * 
 * @author rsaez
 * 
 */
public class ClasificacionSalidas {

    private SortedSet<ItemClasificacionSalidas> clasificacion;
    private TipoSalida tipo;
    private int year;
    private Long clubId;

    public SortedSet<ItemClasificacionSalidas> getClasificacion() {
	if (clasificacion == null){
	    clasificacion = new TreeSet<ItemClasificacionSalidas>();
	} else {
	    int index = 1;
	    for(ItemClasificacionSalidas item : clasificacion){
		item.setPosicion(index++);
	    }
	}
	
	return clasificacion;
    }

    public void setClasificacion(SortedSet<ItemClasificacionSalidas> clasificacion) {
	//TODO hacer el setter en condiciones para evitar errores de memoria y cosas no esperadas
	this.clasificacion = clasificacion;
    }

    public TipoSalida getTipo() {
	return tipo;
    }

    public void setTipo(TipoSalida tipo) {
	this.tipo = tipo;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public Long getClubId() {
	return clubId;
    }

    public void setClubId(Long clubId) {
	this.clubId = clubId;
    }

    public void addItem(ItemClasificacionSalidas item) {
	if (item == null){
	    throw new IllegalArgumentException("parametros incorrectos, item no puede ser nulo");
	}
	if (clasificacion == null){
	    clasificacion = new TreeSet<ItemClasificacionSalidas>();
	}
	clasificacion.add(item);
    }
    
   

}
