package an.dpr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class UtilFecha {
    private static final Logger log = Logger.getLogger(UtilFecha.class);
    public static final SimpleDateFormat SDF = 
	    new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat SDF_FH = 
	    new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final SimpleDateFormat SDF_H = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat SDF_JSON = 
	    new SimpleDateFormat("yyyy-MM-ddHH:mm:ssZ");
    public static final SimpleDateFormat SDF_JSON_MS = 
	    new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
    
    public static Date parseFechaJson(String fecha){
	Date date = null;
	if (fecha != null){
	    fecha = fecha.replace("T","");
	    
	    //eliminamos los dos puntos ultimos
	    int imas = fecha.indexOf("+");
	    int idospuntos = fecha.indexOf(":", imas);
	    fecha = fecha.substring(0,idospuntos)+fecha.substring(idospuntos+1);
	    if (imas==18){
		date = parseaFecha(SDF_JSON, fecha);
	    } else {
		date = parseaFecha(SDF_JSON_MS, fecha);
	    }
	}
	return date;
	
    }
    

    /**
     * formato dd/MM/yyyy
     * 
     * @param fecha
     * @return
     */
    public static String formatFecha(Date fecha) {
	return formatFecha(SDF, fecha);
    }

    /**
     * formato dd/MM/yyyy
     * 
     * @param fecha
     * @return
     */
    public static Date parseFecha(String fecha) {
	return parseaFecha(SDF, fecha);
    }

    /**
     * formato dd/MM/yyyy HH:mm
     * 
     * @param fecha
     * @return
     */
    public static String verFechaHora(Date fecha) {
	return formatFecha(SDF_FH, fecha);
    }

    public static Date getFechaHora(String fecha) throws ParseException {
	return SDF_FH.parse(fecha);
    }

    /**
     * formato HH:mm
     * 
     * @param fecha
     * @return
     */
    public static String verHora(Date fecha) {
	return formatFecha(SDF_H, fecha);
    }

    private static String formatFecha(SimpleDateFormat sdf, Date fecha) {
	return fecha != null ? sdf.format(fecha) : "-";
    }

    private static Date parseaFecha(SimpleDateFormat sdf, String fecha) {
	try {
	    return fecha != null ? sdf.parse(fecha) : null;
	} catch (ParseException e) {
	    log.error("", e);
	    return null;
	}
    }

    /**
     * Devuelve la fecha con hora 00:00:00.000
     * 
     * @param fecha
     * @return
     */
    public static Date quitarHora(Date fecha) {
	if (fecha != null) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(fecha);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	} else {
	    return null;
	}
    }

    /**
     * formato dd/MM/yyyy
     * 
     * @param fecha
     * @return
     */
    public static String verFecha(Date fecha) {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return formateaFecha(sdf, fecha);
    }

    private static String formateaFecha(SimpleDateFormat sdf, Date fecha) {
	return fecha != null ? sdf.format(fecha) : "-";
    }

    /**
     * Suma o resta dias a una fecha
     * 
     * @param fecha
     *            fecha sobre la que sumar dias
     * @param dias
     *            si es positivo, suma los dias, si es negativo los resta
     * @return
     */
    public static Date sumaDias(Date fecha, Integer dias) {
	Date retValue = null;
	if (fecha != null && dias != null) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(fecha);
	    cal.add(Calendar.DAY_OF_MONTH, dias.intValue());
	    retValue = cal.getTime();
	}
	return retValue;
    }
    
    /**
     * Determina si ambas fechas son el mismo dia
     * 
     * @param fechaUno
     * @param fechaDos
     * @return true si son el mismo dia, false si no
     */
    public static boolean mismoDia(Date fechaUno, Date fechaDos) {
	String f1 = verFecha(fechaUno);
	String f2 = verFecha(fechaDos);
	return f1.equals(f2);
    }

    /**
     * Valida que la cadena tenga el format dd/mm/yyyy
     * 
     * @param fecha
     * @return
     */
    public static boolean esFecha(String fecha) {
	StringBuffer regex = new StringBuffer();
	regex.append("^")// inicio
		.append("(0[1-9]|[12][0-9]|3[01])")// dd
		.append("[- /.]")// separador
		.append("(0[1-9]|1[012])")// mm
		.append("[- /.]") // separador
		.append("(19|20)\\d\\d")// a�o
		.append("$");// fin
	Pattern pat = Pattern.compile(regex.toString());
	Matcher m = pat.matcher(fecha);
	if (m.find()) {
	    return true;
	} else {
	    return false;
	}
    }
    
    public static Integer getAnyoActual(){
	Integer anyo = null;
	anyo = Calendar.getInstance().get(Calendar.YEAR);
	return anyo;
    }

}
