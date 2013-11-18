package an.dpr.gesclub.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilFecha {
    
    public static final String FORMATO_FECHA_HORA_DEFECTO = "dd/MM/yyyy HH:mm";
    private static final SimpleDateFormat sdf= new SimpleDateFormat(FORMATO_FECHA_HORA_DEFECTO);

    public static final Date quitarHora(Date fecha){
	Calendar cal = Calendar.getInstance();
	cal.setTime(fecha);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
    }

    public static Date sumaDias(Date fecha, int i) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(fecha);
	cal.add(Calendar.DAY_OF_YEAR, i);
	return cal.getTime();
    }
    
    public static String format(Date fecha){
	return sdf.format(fecha);
    }

    
    
}
