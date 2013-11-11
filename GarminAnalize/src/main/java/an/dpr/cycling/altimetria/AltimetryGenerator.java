package an.dpr.cycling.altimetria;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import an.dpr.cycling.beans.AltimetryPoint;

/**
 * Interface de los generadores de datos de altimetria
 * @author rsaez
 *
 */
public interface AltimetryGenerator {

    public Set<AltimetryPoint> getAltimetrySet(String cadena);
    
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException;
}
