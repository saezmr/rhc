package an.dpr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import an.dpr.routeanalyzer.Configuracion;
import an.dpr.routeanalyzer.altimetria.AltimetriaCanvas;
import an.dpr.routeanalyzer.altimetria.AltimetryBL;
import an.dpr.routeanalyzer.altimetria.AltimetryBLImpl;
import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.Climb;
import an.dpr.routeanalyzer.reader.ITrackInfo;
import an.dpr.routeanalyzer.util.UtilSwing;

public class GenAltApp {
    
    private static final Logger log = Logger.getLogger(GenAltApp.class);

    public static void main(String[] args){
	try {
	    generarAltimetria();
	} catch (Exception e) {
	    log.error(" ", e);
	}
    }
    
    private static void generarAltimetria() throws Exception {
	File file = getFile();
	if (file != null){
	    ITrackInfo tinfo =  getTrackInfoBean(file);
	    Set<AltimetryPoint> data = tinfo.getAltimetrySet(file);
	    
	    
	    getPuertos(data);
//	    rutaPorKm(data, 25000.0,30000.0, false);
//	    rutaEntera(data);
	}
    }
    
    private static ITrackInfo getTrackInfoBean(File file) {
	ITrackInfo ret;
	ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	String fileName = file.getName();
	String trackInfoBean = null;
	if (fileName != null){
	    if (fileName.contains(".gpx")){
		trackInfoBean = "gpxTrackInfo";
	    } else if (fileName.contains(".txt")){
		trackInfoBean = "polarTrackInfo";
	    } else if (fileName.contains(".fit")){
	    	trackInfoBean = "fitTrackInfo";
	    }  
	} 
	 ret = (ITrackInfo) ctx.getBean(trackInfoBean);
	return ret;
    }

    private static File getFile(){
	return UtilSwing.getFile("tjxt","gpx","fit");
    }
    
    private static void getPuertos(Set<AltimetryPoint> data){
	int index = 1;
	AltimetryBL altBL = new AltimetryBLImpl();// TODO spring!!
	((AltimetryBLImpl) altBL).init();// TODO spring!!
	Collection<AltimetryPoint> climbData;

	 List<Climb> climbs = altBL.getClimbs(data);
	 for (Climb climb : climbs) {
	 climbData = altBL.getClimbData(climb, data);
	 String rutaFile = "/home/rsaez/Documents/ganalize/altimetria" + index	 + ".png";
//	  String rutaFile = "/var/ganalize/altimetria" + index + ".png";
	 AltimetriaCanvas canvas = new AltimetriaCanvas();
	 BufferedImage img = canvas.getImagen(climbData, climb.getKmIni(), "nombre",
		 Configuracion.getConfiguracion());
	    guardarImagen(rutaFile, img);
	    index++;
	 }
    }

    private static void rutaPorKm(Set<AltimetryPoint> data, Double kmIni,
	    Double kmFin, boolean finalAlto) {
	// climbs
	AltimetryBL altBL = new AltimetryBLImpl();// TODO spring!!
	((AltimetryBLImpl) altBL).init();// TODO spring!!
	Collection<AltimetryPoint> kmData;

	Climb climb = ((AltimetryBLImpl) altBL).getClimbByKm(data, kmIni,
		kmFin, finalAlto);
	kmData = altBL.getClimbData(climb, data);
	String rutaFile = "/home/rsaez/Documents/ganalize/altimetria_km.png";
//	String rutaFile = "/var/ganalize/altimetria_km.png";
	 AltimetriaCanvas canvas = new AltimetriaCanvas();
	 BufferedImage img = canvas.getImagen(kmData, (double)0, "nombre",
		 Configuracion.getConfiguracion());
	 guardarImagen(rutaFile, img);
    }

    private static void rutaEntera(Collection<AltimetryPoint> data) {
	// unica ruta
	String rutaFile = "/home/rsaez/Documents/ganalize/altimetria.png";
//	 String rutaFile = "/var/ganalize/altimetria.png";
	 AltimetriaCanvas canvas = new AltimetriaCanvas();
	 BufferedImage img = canvas.getImagen(data, (double)0, "nombre",
		 Configuracion.getConfiguracion());
	 guardarImagen(rutaFile, img);

    }

    /**
     * Guarda la imagen cargada en la ruta indicada
     * 
     * @param ruta
     * @throws java.io.IOException
     */
    private static void guardarImagen(String ruta, BufferedImage img) {
	try {
	    File file = null;
	    file = new File(ruta);
	    if (ruta.contains("jpg")) {
		ImageIO.write(img, "jpg", file);
	    } else if (ruta.contains("png")) {
		ImageIO.write(img, "png", file);
	    } else if (ruta.contains("gif")) {
		ImageIO.write(img, "gif", file);
	    }
	    log.info("archivoGuardadoCorrecto " + ruta);// "Archivo guardado correctamente");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
