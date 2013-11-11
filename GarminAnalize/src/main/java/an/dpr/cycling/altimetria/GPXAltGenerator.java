package an.dpr.cycling.altimetria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParserException;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.GPXAltimetriaPoint;
import an.dpr.garmin.analize.AltitudBean;
import an.dpr.garmin.analize.XMLGraminTrackConverter;

public class GPXAltGenerator implements AltimetryGenerator {
    
    private static final Logger log = Logger.getLogger(GPXAltGenerator.class);

    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	Set<AltimetryPoint> data = null;
	List<AltitudBean> list;
	try {
	    list = XMLGraminTrackConverter.getAltitudList(cadena);
	    data = getSetAltimetria(list);
	} catch (XmlPullParserException e) {
	    log.error("Error parseando gpx", e);
	}
	return data;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(File file)
	    throws IOException {
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
	while((line = br.readLine())!= null){
	    xml.append(line);
	}
	return xml.toString();
    }

    private Set<AltimetryPoint> getSetAltimetria(List<AltitudBean> list) {
   	TreeSet<AltimetryPoint> set = new TreeSet<AltimetryPoint>();
   	GPXAltimetriaPoint ap = null;
   	for(AltitudBean ab : list){
   	    ap = new GPXAltimetriaPoint();
   	    ap.setTime(ab.getTime());
   	    ap.setAltitud(ab.getAltitud());
   	    ap.setMetros(ab.getMetrosAvanzados());
   	    set.add(ap);
   	}
   	return set;
       }
}
