package an.dpr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import an.dpr.cycling.altimetria.AltimetriaImagen;
import an.dpr.cycling.altimetria.AltimetryBL;
import an.dpr.cycling.altimetria.AltimetryBLImpl;
import an.dpr.cycling.altimetria.ConfiguracionAltimetria;
import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.Climb;
import an.dpr.cycling.beans.GPXAltimetriaPoint;
import an.dpr.garmin.analize.AltitudBean;
import an.dpr.garmin.analize.XMLGraminTrackConverter;

public class GenAltApp {
    
    private static final Logger log = Logger.getLogger(GenAltApp.class);

    public static void main(String[] args){
	try {
	    generarAltimetria();
	} catch (Exception e) {
	    log.error(" ", e);
	}
    }
    
    public static void generarAltimetria() throws Exception {
	File f = new File("/home/rsaez/Documents/GarminActivities/PUERTOS_ESCALONA.gpx");
//	File f = new File("c:\\var\\ganalize\\ReconocimientoPR200.gpx");
	FileReader fr = new FileReader(f);
	BufferedReader br = new BufferedReader(fr);
	StringBuffer xml = new StringBuffer();
	String line = null;
	while((line = br.readLine())!= null){
	    xml.append(line);
	}
	List<AltitudBean> list = XMLGraminTrackConverter.getAltitudList(xml.toString());
	
	int index = 1;
	Set<AltimetryPoint> data = getSetAltimetria(list);
	
	//climbs
	AltimetryBL altBL = new AltimetryBLImpl();//TODO spring!!
	((AltimetryBLImpl)altBL).init();//TODO spring!!
	
	Collection<AltimetryPoint> climbData;

	Climb climb = ((AltimetryBLImpl)altBL).getClimbByKm(data, (double)7100.0, (double)18300.0,false);
	climbData = altBL.getClimbData(climb, data);
	String rutaFile = "/home/rsaez/Documents/ganalize/altimetria_km.png";
	new AltimetriaImagen(climbData, 
		ConfiguracionAltimetria.getConfiguracion(),
		rutaFile );
	
//	List<Climb> climbs = altBL.getClimbs(data);
//	for (Climb climb : climbs) {
//	    climbData = altBL.getClimbData(climb, data);
//	    String rutaFile = "/home/rsaez/Documents/ganalize/altimetria" + index + ".png";
////	    String rutaFile = "/var/ganalize/altimetria" + index + ".png";
//	    new AltimetriaImagen(climb.getKmIni(), climbData, 
//		    ConfiguracionAltimetria.getConfiguracion(), rutaFile);
//	    index++;
//
//	}
//	//unica ruta
//	String rutaFile = "/home/rsaez/Documents/ganalize/altimetria.png";
////	String rutaFile = "/var/ganalize/altimetria.png";
//	new AltimetriaImagen(data, 
//		ConfiguracionAltimetria.getConfiguracion(),
//		rutaFile );
    }


    private static Set<AltimetryPoint> getSetAltimetria(List<AltitudBean> list) {
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
