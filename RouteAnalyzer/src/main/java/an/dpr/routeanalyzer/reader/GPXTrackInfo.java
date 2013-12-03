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

public class GPXTrackInfo implements ITrackInfo {
    
    private static final Logger log = Logger.getLogger(GPXTrackInfo.class);
    private int z0 = 121;// TODO A CONF
    private int z1 = 135;// TODO A CONF
    private int z2 = 149;// TODO A CONF
    private int z3 = 163;// TODO A CONF
    private int z4 = 176;// TODO A CONF
    private int z5 = 190;// TODO A CONF
    private GPXReader reader;

    @Override
    public List<HRBean> getHRList(String cadena) {
	List<HRBean> listado = reader.getHRList(cadena);
	listado = TrackInfoUtil.completarTiempos(listado);
	return listado;
    }
    
    @Override
    public HRZones getHRZones(String cadena) {
	HRZones hrzones = new HRZones();
	double[] hr = new double[10];
	List<HRBean> listado = getHRList(cadena);
	hr[8] = listado.size();
	for (HRBean bean : listado) {
	    hr[7] += bean.getHr();
	    if (bean.getHr() < z0) {
		hr[0]++;
	    } else if (bean.getHr() < z1) {
		hr[1]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z2) {
		hr[2]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z3) {
		hr[3]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z4) {
		hr[4]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z5) {
		hr[5]++;
		hr[9] += bean.getHr();
	    } else {
		hr[6]++;
		hr[9] += bean.getHr();
	    }
	}
	hrzones.setMedia(hr[7] / hr[8]);
	hrzones.setMedia16((hr[9] / (hr[8] - hr[0])));
	hrzones.setZona0((hr[0] / hr[8]) * 100);
	hrzones.setZona1((hr[1] / hr[8]) * 100);
	hrzones.setZona2((hr[2] / hr[8]) * 100);
	hrzones.setZona3((hr[3] / hr[8]) * 100);
	hrzones.setZona4((hr[4] / hr[8]) * 100);
	hrzones.setZona5((hr[5] / hr[8]) * 100);
	hrzones.setZona6((hr[6] / hr[8]) * 100);
	return hrzones;
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
}
