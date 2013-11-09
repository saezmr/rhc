package an.dpr.cycling.beans;

import java.io.Serializable;

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
public interface AltimetriaPoint extends Serializable, Comparable<AltimetriaPoint> {

    public Double getAltitud();

    public Double getMetros();

}
