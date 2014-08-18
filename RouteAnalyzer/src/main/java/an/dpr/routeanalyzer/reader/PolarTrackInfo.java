package an.dpr.routeanalyzer.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import an.dpr.routeanalyzer.Configuracion;
import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.ConfiguracionBean;
import an.dpr.routeanalyzer.bean.DatosPolarItem;
import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;
import an.dpr.routeanalyzer.domain.Activity;
import an.dpr.routeanalyzer.util.IOUtil;

public class PolarTrackInfo implements ITrackInfo {
    
    private static final Logger log = Logger.getLogger(PolarTrackInfo.class);

    @Override
    public List<HRBean> getHRList(String cadena) {
	List<HRBean> data = new ArrayList<HRBean>();
	String[] tokens = cadena.split("\n");
	DatosPolarItem item = null;
	ConfiguracionBean conf = Configuracion.getConfiguracion();
	Integer count = 0;
	long ms = Calendar.getInstance().getTimeInMillis();
	for(int i = 0; i<tokens.length; i++){
	    String linea = tokens[i];
	    if (linea != null && !linea.trim().equals("")) {
		item = new DatosPolarItem();
		item.loadDatos(count++, linea, conf.getSegundosIntervalo());
		HRBean hr = new HRBean();
		hr.setHr(item.getPulsaciones());
		hr.setTime(ms);
		ms += conf.getSegundosIntervalo()*1000;
		data.add(hr);
	    }
	}
	data = TrackInfoUtil.completarTiempos(data);
	return data;
    }
    
    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	Set<AltimetryPoint> data = new TreeSet<AltimetryPoint>();
	String[] tokens = cadena.split("\n");
	DatosPolarItem item = null;
	ConfiguracionBean conf = Configuracion.getConfiguracion();
	Integer count = 0;
	for(int i = 0; i<tokens.length; i++){
	    String linea = tokens[i];
	    if (linea != null && !linea.trim().equals("")) {
		item = new DatosPolarItem();
		item.loadDatos(count++, linea, conf.getSegundosIntervalo());
		data.add(item);
	    }
	    
	}
	return data;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException {
	Set<AltimetryPoint> data = new TreeSet<AltimetryPoint>();
	Integer count = 0;
	BufferedReader in = new BufferedReader(new FileReader(file));
	ConfiguracionBean conf = Configuracion.getConfiguracion();
	DatosPolarItem item = null;
	if (in != null) {
	    String linea;
	    do {
		linea = in.readLine();
		if (linea != null && !linea.trim().equals("")) {
		    item = new DatosPolarItem();
		    item.loadDatos(count++, linea, conf.getSegundosIntervalo());
		    data.add(item);
		}
	    } while (linea != null);
	    in.close();
	}
	return data;
    }


    @Override
    public HRZones getHRZones(String cadena) {
	throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public List<HRBean> getHRList(File file) throws IOException {
	String cadena = IOUtil.readFile(file);
	return getHRList(cadena);
    }

    @Override
    public Activity getActivityInfo(File file) {
	// TODO Auto-generated method stub
	return null;
    }

}
