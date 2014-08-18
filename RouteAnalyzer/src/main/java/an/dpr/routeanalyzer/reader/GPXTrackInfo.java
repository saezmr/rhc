package an.dpr.routeanalyzer.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.AltitudBean;
import an.dpr.routeanalyzer.bean.GPXAltimetriaPoint;
import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;
import an.dpr.routeanalyzer.domain.Activity;
import an.dpr.routeanalyzer.util.IOUtil;

public class GPXTrackInfo implements ITrackInfo {
    
    private static final Logger log = Logger.getLogger(GPXTrackInfo.class);
    private GPXReader reader;

    @Override
    public List<HRBean> getHRList(File file) throws IOException {
	String cadena = IOUtil.readFile(file);
	return getHRList(cadena);
    }

    @Override
    public List<HRBean> getHRList(String cadena) {
	List<HRBean> listado = reader.getHRList(cadena);
	listado = TrackInfoUtil.completarTiempos(listado);
	return listado;
    }
    
    @Override
    public HRZones getHRZones(String cadena) {
	List<HRBean> listado = getHRList(cadena);
	return TrackInfoUtil.getHRZones(listado);
    }
    
    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	Set<AltimetryPoint> data = null;
	List<AltitudBean> list = reader.getAltitudList(cadena);
	data = getSetAltimetria(list);
	return data;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException {
	Set<AltimetryPoint> data = null;
	String xml = getXMLString(file);
	data = getAltimetrySet(xml);
	return data;
    }

    private String getXMLString(File file) throws IOException {
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	StringBuffer xml = new StringBuffer();
	String line = null;
	while ((line = br.readLine()) != null) {
	    xml.append(line);
	}
	return xml.toString();
    }

    private Set<AltimetryPoint> getSetAltimetria(List<AltitudBean> list) {
	TreeSet<AltimetryPoint> set = new TreeSet<AltimetryPoint>();
	GPXAltimetriaPoint ap = null;
	for (AltitudBean ab : list) {
	    ap = new GPXAltimetriaPoint();
	    ap.setTime(ab.getTime());
	    ap.setAltitud(ab.getAltitud());
	    ap.setMetros(ab.getMetrosAvanzados());
	    set.add(ap);
	}
	return set;
    }

    public GPXReader getReader() {
        return reader;
    }

    public void setReader(GPXReader reader) {
        this.reader = reader;
    }

    @Override
    public Activity getActivityInfo(File file) {
	// TODO Auto-generated method stub
	return null;
    }
}
