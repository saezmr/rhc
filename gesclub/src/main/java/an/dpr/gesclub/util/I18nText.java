package an.dpr.gesclub.util;

import java.util.ResourceBundle;

/**
 * Clase util de internacionalizacion
 * @author rsaez
 *
 */
public class I18nText {


    private static ResourceBundle bundle;

    static {
	bundle = ResourceBundle.getBundle("i18n");
    }
    
    public static ResourceBundle getBundle(){
	return bundle;
    }
}
