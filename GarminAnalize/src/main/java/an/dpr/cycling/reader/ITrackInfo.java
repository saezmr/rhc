package an.dpr.cycling.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.AltitudBean;
import an.dpr.cycling.beans.HRBean;

/**
 * Interface para obtener informacion necesaria para el programa de los
 * diferentes formatos de tracks gps/ciclodeportivos
 * @author rsaez
 *
 */
public interface ITrackInfo {
    
    public List<HRBean> getHRList(String cadena);

    public Set<AltimetryPoint> getAltimetrySet(String cadena);
    
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException;

}
