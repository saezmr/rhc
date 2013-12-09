package an.dpr.routeanalyzer;

import java.awt.Color;

import javax.swing.ImageIcon;

public class ConfiguracionContracts {
    
    public enum TipoDatos {
	GPX, POLAR_TXT, FIT;
    }
    
    public static final String RUTA_FICHERO = "/confAltimetria.properties";
    
    public static final String COLOR_KM = "colorKm";
    public static final String PUERTOS_MIN_DESNIVEL_INICIAL = "puertos.minDesnivelInicial";
    public static final String PUERTOS_KM_INICIALESL = "puertos.kmInicialesl";
    public static final String PUERTOS_MIN_DESNIVEL = "puertos.minDesnivel";
    public static final String PUERTOS_MIN_METROS = "puertos.minMetros";
    public static final String PUERTOS_CORTE_NEGATIVO = "puertos.corteNegativo";
    public static final String RUTA_IMG = "rutaImg";
    public static final String COLOR_CINCO = "colorCinco";
    public static final String COLOR_CUATRO = "colorCuatro";
    public static final String COLOR_TRES = "colorTres";
    public static final String COLOR_DOS = "colorDos";
    public static final String COLOR_UNO = "colorUno";
    public static final String MULTIPLICADOR = "multiplicador";
    public static final String OFFSET_Y = "offsetY";
    public static final String OFFSET_X = "offsetX";
    public static final String ICONO_MARCA = "iconoMarca";
    public static final String COLOR_TEXTO = "colorTexto";
    public static final String COLOR_LINEAS_SUAVES = "colorLineasSuaves";
    public static final String COLOR_LINEAS = "colorLineas";
    public static final String COLOR_KM_IMPAR = "colorKmImpar";
    public static final String COLOR_KM_PAR2 = "colorKmPar";
    public static final String COLOR_HORIZONTE = "colorHorizonte";
    public static final String FACTOR_CORRECCION_ALTURA = "factorCorreccionAltura";
    public static final String VOLTEAR_AUTO = "voltearAuto";
    public static final String REDUCTOR_Y = "reductorY";
    public static final String REDUCTOR_X = "reductorX";
    public static final String PORCENTAJE_CADA_METROS = "porcentajeCadaMetros";
    public static final String NOMBRE = "nombre";
    public static final String KM_CINCO = "KmCinco";
    public static final String KM_CUATRO = "kmCuatro";
    public static final String KM_TRES = "kmTres";
    public static final String KM_DOS = "kmDos";
    public static final String KM_UNO = "kmUno";
    public static final String MOSTRAR_RAMPAS_MAYORES = "mostrarRampasMayores";
    public static final String IDIOMA = "idioma";
    public static final String SEGUNDOS_INTERVALO = "segundosIntervalo";
    public static final String COLOR_KM_RAMPA ="RAMPA";
    public static final String COLOR_KM_PAR ="PAR";

    public static final String HR_ZONA_0 = "hr.zona.0";
    public static final String HR_ZONA_1 = "hr.zona.1";
    public static final String HR_ZONA_2 = "hr.zona.2";
    public static final String HR_ZONA_3 = "hr.zona.3";
    public static final String HR_ZONA_4 = "hr.zona.4";
    public static final String HR_ZONA_5 = "hr.zona.5";
    
    // valores por defecto
    public static final String V_NOMBRE = "Desconocido";
    public static final Integer V_REDUCTOR_X = 10;
    public static final Integer V_REDUCTOR_Y = 1;
    public static final Integer V_PORCENTAJE_CADA_METROS = 100;
    public static final Double V_MOSTRAR_RAMPAS_MAYORES = 5.0;
    public static final Boolean V_VOLTEAR_AUTO = true;
    public static final String V_IDIOMA = "es_ES";
    public static final Color V_COLOR_LINEAS = Color.BLACK;
    public static final Color V_COLOR_LINEAS_SUAVES = Color.LIGHT_GRAY;
    public static final Color V_COLOR_TEXTO = Color.BLACK;
    public static final Color V_COLOR_KM_IMPAR = new Color(0xf5e6e1);
    public static final Color V_COLOR_KM_PAR = new Color(0xfcfbf5);
    public static final Color V_COLOR_HORIZONTE = Color.WHITE;
    public static final ImageIcon V_ICONO_MARCA = null;
    public static final Integer V_SEGUNDOS_INTERVALO = 5;
    public static final Integer V_FACTOR_CORRECCION_ALTURA = 0;
    public static final Integer V_OFFSET_X = 100;
    public static final Integer V_OFFSET_Y = 100;
    public static final Integer V_MULTIPLICADOR = 1;
    public static final String V_RUTA_IMG = "/home/rsaez/Documents/ganalize/altimetria.png";

}
