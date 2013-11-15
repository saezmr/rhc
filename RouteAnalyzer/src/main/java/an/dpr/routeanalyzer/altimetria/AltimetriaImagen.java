
package an.dpr.routeanalyzer.altimetria;

import java.awt.Image;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import an.dpr.routeanalyzer.beans.AltimetryPoint;
import an.dpr.routeanalyzer.beans.ConfiguracionAltimetriaBean;

/**
 * Historial CVS de $Id: AltimetriaImagen.java $
 * $Log: AltimetriaImagen.java,v $
 *
 */
/**
 * AltimetriaImagen
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class AltimetriaImagen extends JLabel{
    
    private AltimetriaCanvas canvas = null;
    private Image img;

    public AltimetriaImagen(Collection<AltimetryPoint> data,String nombre){
        this(data, nombre, null, null, null, true);
    }

    public AltimetriaImagen(double kmIni, Collection<AltimetryPoint> data,ConfiguracionAltimetriaBean conf
	    , String rutaFile){
	this(kmIni, data, conf.getNombre(), conf, rutaFile);
    }
    
    public AltimetriaImagen(Collection<AltimetryPoint> data,ConfiguracionAltimetriaBean conf
	    , String rutaFile){
        this(0.0, data, conf.getNombre(), conf, rutaFile);
    }

    public AltimetriaImagen(Double kmIni, Collection<AltimetryPoint> data, String nombre,
	    ConfiguracionAltimetriaBean conf, String rutaFile) {
	canvas = new AltimetriaCanvas(data, kmIni, nombre, conf);
	this.img = canvas.getImagen();
	try {
	    this.guardarImagen(rutaFile);
	} catch (IOException ex) {
	    Logger.getLogger(AltimetriaImagen.class.getName()).log(
		    Level.SEVERE, null, ex);
	}
    }

    public AltimetriaImagen(Collection<AltimetryPoint> data, String nombre, Integer reductor, 
            Integer porcentajeCadaMetros, Double mostrarRampasMayores, boolean voltearAuto){
        canvas = new AltimetriaCanvas(data, 0.0, nombre, reductor, porcentajeCadaMetros, mostrarRampasMayores, voltearAuto);
        this.img = canvas.getImagen();
        this.setIcon(new ImageIcon(img));
    }

    /**
     * Guarda la imagen cargada en la ruta indicada
     * @param ruta
     * @throws java.io.IOException
     */
    public void guardarImagen(String ruta) throws IOException{
        canvas.guardarImagen(ruta);
    }

}
