package an.dpr.cycling.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.AltitudBean;
import an.dpr.cycling.beans.GPXAltimetriaPoint;
import an.dpr.cycling.beans.HRBean;

public class GPXTrackInfo implements ITrackInfo {
    
    private static final Logger log = Logger.getLogger(GPXTrackInfo.class);
    private GPXReader reader;

    @Override
    public List<HRBean> getHRList(String cadena) {
	List<HRBean> listado = reader.getHRList(cadena);
	listado = completarTiempos(listado);
	return listado;
    }
    
    /**
     * Completa el mapa segundo a segundo
     */
    private List<HRBean> completarTiempos(List<HRBean> listado) {
	List<HRBean> list = new ArrayList<HRBean>();
	HRBean antBean = null;
	for (HRBean bean : listado) {
	    if (bean.getTime() == null || bean.getHr() == null){
		continue;
	    }
	    list.add(bean);
	    // log.debug(bean);
	    if (antBean != null) {
		long secf = (bean.getTime() - antBean.getTime()) / 1000;
		int difhr = bean.getHr() - antBean.getHr();
		// log.debug(secf+"/"+difhr);
		for (int i = 1; i < secf; i++) {
		    HRBean newBean = new HRBean();
		    newBean.setTime(antBean.getTime() + (i * 1000));
		    newBean.setHr(antBean.getHr() + (int) (difhr / secf));
		    // log.debug(newBean);
		    list.add(newBean);
		}
	    }
	    antBean = bean;
	}
	return list;
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
