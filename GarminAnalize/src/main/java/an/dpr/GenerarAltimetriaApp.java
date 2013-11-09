//package an.dpr;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.apache.log4j.Logger;
//
//import an.dpr.cycling.altimetria.AltimetriaImagen;
//import an.dpr.cycling.altimetria.ConfiguracionAltimetria;
//import an.dpr.cycling.beans.AltimetriaPoint;
//import an.dpr.cycling.beans.GPXAltimetriaPoint;
//import an.dpr.garmin.analize.AltitudBean;
//import an.dpr.garmin.analize.XMLGraminTrackConverter;
//
//public class GenerarAltimetriaApp {
//    
//    private static final Logger log = Logger.getLogger(GenerarAltimetriaApp.class);
//
//    public static void main(String[] args){
//	try {
//	    generarAltimetria();
//	} catch (Exception e) {
//	    log.error(" ", e);
//	}
//    }
//    
//    public static void generarAltimetria() throws Exception {
//	File f = new File("/home/rsaez/Documents/GarminActivities/AltoCaracol.gpx");
//	FileReader fr = new FileReader(f);
//	BufferedReader br = new BufferedReader(fr);
//	StringBuffer xml = new StringBuffer();
//	String line = null;
//	while((line = br.readLine())!= null){
//	    xml.append(line);
//	}
//	List<AltitudBean> list = XMLGraminTrackConverter.getAltitudList(xml.toString());
//	Set<AltimetriaPoint> data = getSetAltimetria(list);
//		
//	AltimetriaImagen ai = new AltimetriaImagen(
//		data, 
//		ConfiguracionAltimetria.getConfiguracion());
//    }
//
//    private static Set<AltimetriaPoint> getSetAltimetria(List<AltitudBean> list) {
//	TreeSet<AltimetriaPoint> set = new TreeSet<AltimetriaPoint>();
//	GPXAltimetriaPoint ap = null;
//	for(AltitudBean ab : list){
//	    ap = new GPXAltimetriaPoint();
//	    ap.setTime(ab.getTime());
//	    ap.setAltitud(ab.getAltitud());
//	    ap.setMetros(ab.getMetrosAvanzados());
//	    set.add(ap);
//	}
//	return set;
//    }
//    
//}
