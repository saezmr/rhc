package an.dpr.cycling.altimetria;

import java.io.File;

import org.apache.log4j.Logger;

import an.dpr.cycling.altimetria.AltimetryContratcs.TipoDatos;

/**
 * TODO Spring bean factory!!!
 * @author rsaez
 *
 */
@Deprecated
public class AltimetryGeneratorFactory {
    
    private static final Logger log = Logger.getLogger(AltimetryGeneratorFactory.class);
    
    public static AltimetryGenerator getInstance(File file){
	AltimetryGenerator gen = null;
	if (file != null){
	    String name = file.getName();
	    if (name.contains(".gpx")){
		gen = getInstance(TipoDatos.GPX);
	    } else if (name.contains(".txt")){
		gen = getInstance(TipoDatos.POLAR_TXT);
	    }
	}
	return gen;
    }

    public static AltimetryGenerator getInstance(TipoDatos tipoDatos){
	AltimetryGenerator instance = null;
	if (TipoDatos.GPX.equals(tipoDatos)){
	    instance = new GPXAltGenerator();
	    log.debug("generator "+tipoDatos.name());
	} else if (TipoDatos.POLAR_TXT.equals(tipoDatos)){
	    instance = new PolarAltGenerator();
	    log.debug("generator "+tipoDatos.name());
	}
	return instance;
    }
}
