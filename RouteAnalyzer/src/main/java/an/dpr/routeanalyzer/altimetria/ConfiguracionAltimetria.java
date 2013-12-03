package an.dpr.routeanalyzer.altimetria;

import an.dpr.routeanalyzer.bean.ConfiguracionAltimetriaBean;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import javax.swing.ImageIcon;

/**
 * Historial CVS de $Id: ConfiguracionAltimetria.java $
 * $Log: ConfiguracionAltimetria.java,v $
 *
 */
/**
 * ConfiguracionAltimetria
 * 
 * @author ricardo.saez, Ultima modificacion: $Author: $
 * @version $Revision: $ $Date: $
 */
public class ConfiguracionAltimetria {

    private static final Logger log = Logger
	    .getLogger(ConfiguracionAltimetria.class);
    private static ConfiguracionAltimetria instance;
    private ConfiguracionAltimetriaBean bean;
    private String RUTA_FICHERO = "/confAltimetria.properties";
    public static final String COLOR_KM_RAMPA ="RAMPA";
    public static final String COLOR_KM_PAR ="PAR";

    private ConfiguracionAltimetria() {
    }

    public static void main(String[] args) {

	System.out.println(ConfiguracionAltimetria.getConfiguracion());
    }

    private Properties generarFicheroPropiedades()
	    throws FileNotFoundException, IOException {
	Properties properties = new Properties();

	properties.setProperty("idioma", "an_ES");
	properties.setProperty("mostrarRampasMayores", "5.0");
	properties.setProperty("nombre", "Sin Nombre");
	properties.setProperty("porcentajeCadaMetros", "100");
	properties.setProperty("reductorX", "10");
	properties.setProperty("reductorY", "1");
	properties.setProperty("voltearAuto", "true");
	properties.setProperty("colorHorizonte", "FFFFFF");
	properties.setProperty("colorKmPar", "fcfbf5");
	properties.setProperty("colorKmImpar", "f5e6e1");
	properties.setProperty("colorLineas", "000000");
	properties.setProperty("colorLineasSuaves", "888888");
	properties.setProperty("colorTexto", "000000");
	properties.setProperty("iconoMarca", "");
	properties.setProperty("segundosIntervalo", "5");
	properties.setProperty("factorCorreccionAltura", "0");
	properties.setProperty("offsetX", "100");
	properties.setProperty("offsetY", "100");
	properties.setProperty("multiplicador", "1");

	FileOutputStream fos = new FileOutputStream(new File(RUTA_FICHERO));
	properties.store(fos, "propiedades de las altimetrias");

	return properties;
    }

    private void leerConfiguracion(Properties properties) {
	bean = new ConfiguracionAltimetriaBean();
	if (properties != null) {
	    bean.setSegundosIntervalo(properties
		    .getProperty("segundosIntervalo") != null ? Integer
		    .valueOf(properties.getProperty("segundosIntervalo"))
		    : null);
	    bean.setIdioma(properties.getProperty("idioma") != null ? properties
		    .getProperty("idioma") : null);
	    
	    bean.setMostrarRampasMayores(properties
		    .getProperty("mostrarRampasMayores") != null ? new Double(
		    properties.getProperty("mostrarRampasMayores")) : null);
	    
	    bean.setKmUno(properties.getProperty("kmUno") != null ? new Double(
		    properties.getProperty("kmUno")) : null);
	    bean.setKmDos(properties.getProperty("kmDos") != null ? new Double(
		    properties.getProperty("kmDos")) : null);
	    bean.setKmTres(properties.getProperty("kmTres") != null ? new Double(
		    properties.getProperty("kmTres")) : null);
	    bean.setKmCuatro(properties.getProperty("kmCuatro") != null ? new Double(
		    properties.getProperty("kmCuatro")) : null);
	    bean.setKmCinco(properties.getProperty("KmCinco") != null ? new Double(
		    properties.getProperty("KmCinco")) : null);
	    
	    bean.setNombre(properties.getProperty("nombre") != null ? properties
		    .getProperty("nombre") : null);
	    bean.setPorcentajeCadaMetros(properties
		    .getProperty("porcentajeCadaMetros") != null ? Integer
		    .valueOf(properties.getProperty("porcentajeCadaMetros"))
		    : null);
	    bean.setReductorX(properties.getProperty("reductorX") != null ? Integer
		    .valueOf(properties.getProperty("reductorX")) : null);
	    bean.setReductorY(properties.getProperty("reductorY") != null ? Integer
		    .valueOf(properties.getProperty("reductorY")) : null);
	    bean.setVoltearAuto(properties.getProperty("voltearAuto") != null ? Boolean
		    .valueOf(properties.getProperty("voltearAuto")) : null);
	    bean.setFactorCorreccionAltura(properties
		    .getProperty("factorCorreccionAltura") != null ? Integer
		    .valueOf(properties.getProperty("factorCorreccionAltura"))
		    : null);
	
	    bean.setColorHorizonte(properties.getProperty("colorHorizonte") != null ? Color
		    .decode("0x" + properties.getProperty("colorHorizonte"))
		    : null);
	    bean.setColorKmPar(properties.getProperty("colorKmPar") != null ? Color
		    .decode("0x" + properties.getProperty("colorKmPar")) : null);
	    bean.setColorKmImpar(properties.getProperty("colorKmImpar") != null ? Color
		    .decode("0x" + properties.getProperty("colorKmImpar"))
		    : null);
	    bean.setColorLineas(properties.getProperty("colorLineas") != null ? Color
		    .decode("0x" + properties.getProperty("colorLineas"))
		    : null);
	    bean.setColorLineasSuaves(properties
		    .getProperty("colorLineasSuaves") != null ? Color
		    .decode("0x" + properties.getProperty("colorLineasSuaves"))
		    : null);
	    bean.setColorTexto(properties.getProperty("colorTexto") != null ? Color
		    .decode("0x" + properties.getProperty("colorTexto")) : null);
	    
	    bean.setColorUno(properties.getProperty("colorUno") != null ? Color
		    .decode("0x" + properties.getProperty("colorUno")) : null);
	    bean.setColorDos(properties.getProperty("colorDos") != null ? Color
		    .decode("0x" + properties.getProperty("colorDos")) : null);
	    bean.setColorTres(properties.getProperty("colorTres") != null ? Color
		    .decode("0x" + properties.getProperty("colorTres")) : null);
	    bean.setColorCuatro(properties.getProperty("colorCuatro") != null ? Color
		    .decode("0x" + properties.getProperty("colorCuatro")) : null);
	    bean.setColorCinco(properties.getProperty("colorCinco") != null ? Color
		    .decode("0x" + properties.getProperty("colorCinco")) : null);
	    
	    
	    bean.setIconoMarca(properties.getProperty("iconoMarca") == null
		    || properties.getProperty("iconoMarca").equals("") ? null
		    : new ImageIcon(getClass().getResource(
			    properties.getProperty("iconoMarca"))));
	    bean.setOffsetX(properties.getProperty("offsetX") != null ? Integer
		    .valueOf(properties.getProperty("offsetX")) : null);
	    bean.setOffsetY(properties.getProperty("offsetY") != null ? Integer
		    .valueOf(properties.getProperty("offsetY")) : null);
	    bean.setMultiplicador(properties.getProperty("multiplicador") != null ? Integer
		    .valueOf(properties.getProperty("multiplicador")) : null);
	    bean.setRutaImg(properties.getProperty("rutaImg"));
	    bean.setCorteNegativoPuertos(Double.valueOf(properties
		    .getProperty("puertos.corteNegativo")));
	    bean.setMinMetrosPuertos(Double.valueOf(properties
		    .getProperty("puertos.minMetros")));
	    bean.setMinDesnivelPuertos(Double.valueOf(properties
		    .getProperty("puertos.minDesnivel")));
	    bean.setKmInicialesPuertos(Double.valueOf(properties
		    .getProperty("puertos.kmInicialesl")));
	    bean.setMinDesnivelInicialPuertos(Double.valueOf(properties
		    .getProperty("puertos.minDesnivelInicial")));
	    bean.setColorKm(properties.getProperty("colorKm"));
	}
    }

    public static ConfiguracionAltimetriaBean getConfiguracion() {
	if (instance == null) {
	    instance = new ConfiguracionAltimetria();
	}
	return instance.getBean();
    }

    public ConfiguracionAltimetriaBean getBean() {
	Properties properties = new Properties();
	InputStream is = ConfiguracionAltimetria.class
		.getResourceAsStream(RUTA_FICHERO);
	try {
	    properties.load(is);
	    leerConfiguracion(properties);
	} catch (IOException e) {
	    log.error("Error leyendo configuracion");
	}
	return bean;
    }

}
