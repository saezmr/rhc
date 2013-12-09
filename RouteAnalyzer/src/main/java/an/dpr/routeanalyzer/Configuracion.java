package an.dpr.routeanalyzer;

import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_CINCO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_CUATRO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_DOS;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_HORIZONTE;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_KM;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_KM_IMPAR;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_KM_PAR2;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_LINEAS;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_LINEAS_SUAVES;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_TEXTO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_TRES;
import static an.dpr.routeanalyzer.ConfiguracionContracts.COLOR_UNO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.FACTOR_CORRECCION_ALTURA;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_0;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_1;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_2;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_3;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_4;
import static an.dpr.routeanalyzer.ConfiguracionContracts.HR_ZONA_5;
import static an.dpr.routeanalyzer.ConfiguracionContracts.ICONO_MARCA;
import static an.dpr.routeanalyzer.ConfiguracionContracts.IDIOMA;
import static an.dpr.routeanalyzer.ConfiguracionContracts.KM_CINCO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.KM_CUATRO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.KM_DOS;
import static an.dpr.routeanalyzer.ConfiguracionContracts.KM_TRES;
import static an.dpr.routeanalyzer.ConfiguracionContracts.KM_UNO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.MOSTRAR_RAMPAS_MAYORES;
import static an.dpr.routeanalyzer.ConfiguracionContracts.MULTIPLICADOR;
import static an.dpr.routeanalyzer.ConfiguracionContracts.NOMBRE;
import static an.dpr.routeanalyzer.ConfiguracionContracts.OFFSET_X;
import static an.dpr.routeanalyzer.ConfiguracionContracts.OFFSET_Y;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PORCENTAJE_CADA_METROS;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PUERTOS_CORTE_NEGATIVO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PUERTOS_KM_INICIALESL;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PUERTOS_MIN_DESNIVEL;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PUERTOS_MIN_DESNIVEL_INICIAL;
import static an.dpr.routeanalyzer.ConfiguracionContracts.PUERTOS_MIN_METROS;
import static an.dpr.routeanalyzer.ConfiguracionContracts.REDUCTOR_X;
import static an.dpr.routeanalyzer.ConfiguracionContracts.REDUCTOR_Y;
import static an.dpr.routeanalyzer.ConfiguracionContracts.RUTA_FICHERO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.RUTA_IMG;
import static an.dpr.routeanalyzer.ConfiguracionContracts.SEGUNDOS_INTERVALO;
import static an.dpr.routeanalyzer.ConfiguracionContracts.VOLTEAR_AUTO;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import an.dpr.routeanalyzer.bean.ConfiguracionBean;

/**
 * ConfiguracionAltimetria
 * 
 * @author rsaez
 * @version 
 */
public class Configuracion {


    private static final Logger log = Logger
	    .getLogger(Configuracion.class);
    private static ConfiguracionBean bean;

    private Configuracion() {
    }

    @Deprecated
    private Properties generarFicheroPropiedades()
	    throws FileNotFoundException, IOException {
	Properties properties = new Properties();

	properties.setProperty(IDIOMA, "an_ES");
	properties.setProperty(MOSTRAR_RAMPAS_MAYORES, "5.0");
	properties.setProperty(NOMBRE, "Sin Nombre");
	properties.setProperty(PORCENTAJE_CADA_METROS, "100");
	properties.setProperty(REDUCTOR_X, "10");
	properties.setProperty(REDUCTOR_Y, "1");
	properties.setProperty(VOLTEAR_AUTO, "true");
	properties.setProperty(COLOR_HORIZONTE, "FFFFFF");
	properties.setProperty(COLOR_KM_PAR2, "fcfbf5");
	properties.setProperty(COLOR_KM_IMPAR, "f5e6e1");
	properties.setProperty(COLOR_LINEAS, "000000");
	properties.setProperty(COLOR_LINEAS_SUAVES, "888888");
	properties.setProperty(COLOR_TEXTO, "000000");
	properties.setProperty(ICONO_MARCA, "");
	properties.setProperty(SEGUNDOS_INTERVALO, "5");
	properties.setProperty(FACTOR_CORRECCION_ALTURA, "0");
	properties.setProperty(OFFSET_X, "100");
	properties.setProperty(OFFSET_Y, "100");
	properties.setProperty(MULTIPLICADOR, "1");

	FileOutputStream fos = new FileOutputStream(new File(RUTA_FICHERO));
	properties.store(fos, "propiedades de las altimetrias");

	return properties;
    }

    private static void leerConfiguracion(Properties properties) {
	bean = new ConfiguracionBean();
	if (properties != null) {
	    bean.setSegundosIntervalo(properties
		    .getProperty(SEGUNDOS_INTERVALO) != null ? Integer
		    .valueOf(properties.getProperty(SEGUNDOS_INTERVALO))
		    : null);
	    bean.setIdioma(properties.getProperty(IDIOMA) != null ? properties
		    .getProperty(IDIOMA) : null);
	    
	    bean.setMostrarRampasMayores(properties
		    .getProperty(MOSTRAR_RAMPAS_MAYORES) != null ? new Double(
		    properties.getProperty(MOSTRAR_RAMPAS_MAYORES)) : null);
	    
	    bean.setKmUno(properties.getProperty(KM_UNO) != null ? new Double(
		    properties.getProperty(KM_UNO)) : null);
	    bean.setKmDos(properties.getProperty(KM_DOS) != null ? new Double(
		    properties.getProperty(KM_DOS)) : null);
	    bean.setKmTres(properties.getProperty(KM_TRES) != null ? new Double(
		    properties.getProperty(KM_TRES)) : null);
	    bean.setKmCuatro(properties.getProperty(KM_CUATRO) != null ? new Double(
		    properties.getProperty(KM_CUATRO)) : null);
	    bean.setKmCinco(properties.getProperty(KM_CINCO) != null ? new Double(
		    properties.getProperty(KM_CINCO)) : null);
	    
	    bean.setNombre(properties.getProperty(NOMBRE) != null ? properties
		    .getProperty(NOMBRE) : null);
	    bean.setPorcentajeCadaMetros(properties
		    .getProperty(PORCENTAJE_CADA_METROS) != null ? Integer
		    .valueOf(properties.getProperty(PORCENTAJE_CADA_METROS))
		    : null);
	    bean.setReductorX(properties.getProperty(REDUCTOR_X) != null ? Integer
		    .valueOf(properties.getProperty(REDUCTOR_X)) : null);
	    bean.setReductorY(properties.getProperty(REDUCTOR_Y) != null ? Integer
		    .valueOf(properties.getProperty(REDUCTOR_Y)) : null);
	    bean.setVoltearAuto(properties.getProperty(VOLTEAR_AUTO) != null ? Boolean
		    .valueOf(properties.getProperty(VOLTEAR_AUTO)) : null);
	    bean.setFactorCorreccionAltura(properties
		    .getProperty(FACTOR_CORRECCION_ALTURA) != null ? Integer
		    .valueOf(properties.getProperty(FACTOR_CORRECCION_ALTURA))
		    : null);
	
	    bean.setColorHorizonte(properties.getProperty(COLOR_HORIZONTE) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_HORIZONTE))
		    : null);
	    bean.setColorKmPar(properties.getProperty(COLOR_KM_PAR2) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_KM_PAR2)) : null);
	    bean.setColorKmImpar(properties.getProperty(COLOR_KM_IMPAR) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_KM_IMPAR))
		    : null);
	    bean.setColorLineas(properties.getProperty(COLOR_LINEAS) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_LINEAS))
		    : null);
	    bean.setColorLineasSuaves(properties
		    .getProperty(COLOR_LINEAS_SUAVES) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_LINEAS_SUAVES))
		    : null);
	    bean.setColorTexto(properties.getProperty(COLOR_TEXTO) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_TEXTO)) : null);
	    
	    bean.setColorUno(properties.getProperty(COLOR_UNO) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_UNO)) : null);
	    bean.setColorDos(properties.getProperty(COLOR_DOS) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_DOS)) : null);
	    bean.setColorTres(properties.getProperty(COLOR_TRES) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_TRES)) : null);
	    bean.setColorCuatro(properties.getProperty(COLOR_CUATRO) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_CUATRO)) : null);
	    bean.setColorCinco(properties.getProperty(COLOR_CINCO) != null ? Color
		    .decode("0x" + properties.getProperty(COLOR_CINCO)) : null);
	    
	    
	    bean.setIconoMarca(properties.getProperty(ICONO_MARCA) == null
		    || properties.getProperty(ICONO_MARCA).equals("") ? null : new ImageIcon(
		    Configuracion.class.getResource(properties.getProperty(ICONO_MARCA))));
	    bean.setOffsetX(properties.getProperty(OFFSET_X) != null ? Integer
		    .valueOf(properties.getProperty(OFFSET_X)) : null);
	    bean.setOffsetY(properties.getProperty(OFFSET_Y) != null ? Integer
		    .valueOf(properties.getProperty(OFFSET_Y)) : null);
	    bean.setMultiplicador(properties.getProperty(MULTIPLICADOR) != null ? Integer
		    .valueOf(properties.getProperty(MULTIPLICADOR)) : null);
	    bean.setRutaImg(properties.getProperty(RUTA_IMG));
	    bean.setCorteNegativoPuertos(Double.valueOf(properties
		    .getProperty(PUERTOS_CORTE_NEGATIVO)));
	    bean.setMinMetrosPuertos(Double.valueOf(properties
		    .getProperty(PUERTOS_MIN_METROS)));
	    bean.setMinDesnivelPuertos(Double.valueOf(properties
		    .getProperty(PUERTOS_MIN_DESNIVEL)));
	    bean.setKmInicialesPuertos(Double.valueOf(properties
		    .getProperty(PUERTOS_KM_INICIALESL)));
	    bean.setMinDesnivelInicialPuertos(Double.valueOf(properties
		    .getProperty(PUERTOS_MIN_DESNIVEL_INICIAL)));
	    bean.setColorKm(properties.getProperty(COLOR_KM));
	    //HR ZONES
	    bean.setHrZona0(Integer.valueOf(properties.getProperty(HR_ZONA_0)));
	    bean.setHrZona1(Integer.valueOf(properties.getProperty(HR_ZONA_1)));
	    bean.setHrZona2(Integer.valueOf(properties.getProperty(HR_ZONA_2)));
	    bean.setHrZona3(Integer.valueOf(properties.getProperty(HR_ZONA_3)));
	    bean.setHrZona4(Integer.valueOf(properties.getProperty(HR_ZONA_4)));
	    bean.setHrZona5(Integer.valueOf(properties.getProperty(HR_ZONA_5)));
	}
    }

    public static ConfiguracionBean getConfiguracion() {
	if (bean == null) {
	    bean = getBean();
	}
	return bean;
    }

    public static ConfiguracionBean getBean() {
	Properties properties = new Properties();
	InputStream is = Configuracion.class
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
