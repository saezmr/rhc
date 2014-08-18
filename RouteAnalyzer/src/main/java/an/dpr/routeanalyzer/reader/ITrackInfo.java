package an.dpr.routeanalyzer.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;
import an.dpr.routeanalyzer.domain.Activity;

/**
 * Interface para obtener informacion necesaria para el programa de los
 * diferentes formatos de tracks gps/ciclodeportivos
 * @author rsaez
 *
 */
public interface ITrackInfo {

    public Activity getActivityInfo(File file);

    public List<HRBean> getHRList(File file) throws IOException;

    public List<HRBean> getHRList(String cadena);

    public HRZones getHRZones(String cadena);

    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException;

    public Set<AltimetryPoint> getAltimetrySet(String cadena);

}
