package an.dpr.routeanalyzer.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Historial CVS de $Id: DatosPolarItem.java $
 * $Log: DatosPolarItem.java,v $
 *
 */
/**
 * DatosPolarItem
 * 
 * Bean de los datos a exportar de polar.
 * 
 * @author ricardo.saez, Ultima modificacion: $Author: $
 * @version $Revision: $ $Date: $
 *
 */
public interface AltimetryPoint extends Serializable, Comparable<AltimetryPoint> {

    public Date getDate();
    
    public Double getAltitud();

    public Double getMetros();

}
