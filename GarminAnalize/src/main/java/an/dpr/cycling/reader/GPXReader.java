package an.dpr.cycling.reader;

import java.util.List;

import an.dpr.cycling.beans.AltitudBean;
import an.dpr.cycling.beans.HRBean;

/**
 * Extrae info de un gpx
 * @author rsaez
 *
 */
public interface GPXReader {

    /**
     * Obtiene el listado de beans de HeartRate
     * @param xml
     * @return
     */
    public List<HRBean> getHRList(String xml);
    
    /**
     * Estrae el listado de beans de altitud
     * @param xml
     * @return
     */
    public List<AltitudBean> getAltitudList(String xml);
}
