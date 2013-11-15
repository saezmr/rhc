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

import an.dpr.cycling.altimetria.ConfiguracionAltimetria;
import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.AltitudBean;
import an.dpr.cycling.beans.ConfiguracionAltimetriaBean;
import an.dpr.cycling.beans.DatosPolarItem;
import an.dpr.cycling.beans.GPXAltimetriaPoint;
import an.dpr.cycling.beans.HRBean;

public class PolarTrackInfo implements ITrackInfo {
    
    private static final Logger log = Logger.getLogger(PolarTrackInfo.class);

    @Override
    public List<HRBean> getHRList(String cadena) {
	log.error("getHRList not yet implemented");
	return null;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	Set<AltimetryPoint> data = new TreeSet<AltimetryPoint>();
	String[] tokens = cadena.split("\n");
	DatosPolarItem item = null;
	ConfiguracionAltimetriaBean conf = ConfiguracionAltimetria.getConfiguracion();
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
	ConfiguracionAltimetriaBean conf = ConfiguracionAltimetria.getConfiguracion();
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

}
