
package an.dpr.cycling.beans;

import an.dpr.cycling.altimetria.*;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;

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
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class GPXAltimetriaPoint implements AltimetryPoint{

    private Long time;
    private Double altitud;
    private Double metros;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public int compareTo(AltimetryPoint o) {
	if (time != null && ((GPXAltimetriaPoint)o).getTime() != null){
	    return time.compareTo(((GPXAltimetriaPoint)o).getTime());
	} else {
	    return 1;
	}
    }

    @Override
    public Double getAltitud() {
	return altitud;
    }

    @Override
    public Double getMetros() {
	return metros;
    }

    public void setAltitud(Double altitud) {
        this.altitud = altitud;
    }

    public void setMetros(Double metros) {
        this.metros = metros;
    }
    
    public Date getDate(){
	if (time != null){
	    return new Date(time);
	} else {
	    return new Date();
	}
    }

}
