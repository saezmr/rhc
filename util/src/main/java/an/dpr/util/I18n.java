package an.dpr.util;

import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n{

    private static final String REPLACE_CHAR = "%param";

    private static final String URL_MESSAGES = "locale.messages";

    private ResourceBundle bundle;
    private static I18n instance;


    private I18n(ResourceBundle rs) {
        bundle = rs;
    }

    public static void createInstance() {
	ResourceBundle rs = ResourceBundle.getBundle(URL_MESSAGES);
	createInstance(rs);
    }
    
    public static void createInstance(ResourceBundle rs) {
        instance = new I18n(rs);
    }

    public static String getText(String clave) {
	init();
        if (instance != null) {
            return instance.getCadena(clave, (String[]) null);
        } else {
            return clave;
        }
    }

    public static String getText(String clave, String... parametros) {
	init();
        if (instance != null) {
            return instance.getCadena(clave, parametros);
        } else {
            return clave;
        }
    }

    /**
     * Busca la clave y reemplaza los parametros indicados
     * 
     * @param clave
     * @param parametros
     *            HashMap<String, String> clave a sustituir - valor por el que
     *            sustituir
     * @return
     */
    public static String getText(String clave, HashMap<String, String> parametros){
	init();
	if (instance != null) {
	    return instance.getCadena(clave, null, parametros);
	} else {
	    return clave;
	}
    }

    /**
     * Busca la clave y reemplaza los parametros indicados
     * 
     * @param clave
     * @param parametros
     *            String[][2] donde [0] es la clave a sustituir y [1] el varlo
     * @return
     */
    public static String getText(String clave, String[][] parametros){
	init();
	if (instance != null) {
	    return instance.getCadena(clave, parametros, null);
	} else {
	    return clave;
	}
    }
    
    private static void init() {
	if (instance == null){
	    createInstance();
	}
    }

    private String getCadena(String clave, String... parametros){
	String texto = clave;
	try{
	    texto = bundle.getString(clave);
	    if (parametros != null){
		for(String param: parametros){
		    texto = texto.replaceFirst(REPLACE_CHAR, param);
		}
	    }
	    texto = texto.replaceAll(REPLACE_CHAR, "");
	} catch(MissingResourceException e){
	    texto = clave;
	}
	return texto;
    }
    
    private String getCadena(String clave, String[][] paramArray, HashMap<String, String> paramMap){
	String texto = clave;
	try{
	    texto = bundle.getString(clave);
	    texto = reemplazaArray(texto, paramArray);
	    texto = reemplazaHashMap(texto, paramMap);
	} catch(Exception e){
	    texto = clave;
	}
	return texto;
    }
    
    private String reemplazaArray(String texto, String[][] paramArray){
	if (paramArray != null){
	    for(String[] param: paramArray){
		texto = texto.replaceAll(param[0], param[1]);
	    }
	    texto = texto.replaceAll(REPLACE_CHAR, "");
	}
	return texto;
    }
    
    private String reemplazaHashMap(String texto, HashMap<String, String> param){
	if (param!= null){
	    for(String clave: param.keySet()){
		String replaceText = param.get(clave);
		texto = texto.replaceAll(clave, replaceText != null ? replaceText : "");
	    }
	}
	texto = texto.replaceAll(REPLACE_CHAR, "");
	return texto;
    }
}
