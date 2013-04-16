package an.dpr.enbizzi.util;

import java.util.Date;

import org.apache.geronimo.mail.util.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import an.dpr.enbizzi.beans.Orache;
import an.dpr.enbizzi.domain.Comarca;
import an.dpr.enbizzi.domain.CyclingType;
import an.dpr.enbizzi.domain.Difficulty;
import an.dpr.enbizzi.domain.Puerto;
import an.dpr.enbizzi.domain.Salida;
import an.dpr.util.UtilFecha;

public class BeanFactory {

    private static final Logger log = Logger.getLogger(BeanFactory.class);

    public static Salida getSalidaFromJSON(String txtJson) {
	log.debug("inicio");
	Salida ret = null;
	JSONObject json;
	try {
	    json = new JSONObject(txtJson);
	    JSONObject jsSalida = json.getJSONObject("Salida");
	    if (jsSalida != null) {
		ret = new Salida();
		ret = getDatosGeneralesSalidaJSON(jsSalida, ret);
		ret = getDatosOracheSalidaJSON(jsSalida, ret);
		ret = getPuertosSalidaJSON(jsSalida, ret);
		ret = getTipoCiclismoSalidaJSON(jsSalida, ret);
		ret = getDificultadSalidaJSON(jsSalida, ret);
	    }
	} catch (JSONException e) {
	    log.error(
		    "Error obteniendo el objeto salida de la informacion json",
		    e);
	}
	return ret;
    }

    private static Salida getDificultadSalidaJSON(JSONObject salida, Salida ret)
	    throws JSONException {
	log.debug("inicio");
	Difficulty difficulty = new Difficulty();
	JSONObject dif = salida.getJSONObject("difficulty");
	difficulty.setDifficultyId(getJSLong(dif,"difficultyId"));
	difficulty.setName(getJSString(dif,"name"));
	ret.setDifficulty(difficulty);
	return ret;
    }

    private static Salida getTipoCiclismoSalidaJSON(JSONObject salida,
	    Salida ret) throws JSONException {
	log.debug("inicio");
	CyclingType ct = new CyclingType();
	JSONObject type = salida.getJSONObject("type");
	ct.setCyclingTypeId(getJSLong(type,"cyclingTypeId"));
	ct.setName(getJSString(type,"name"));
	ret.setType(ct);
	return ret;
    }

    private static Salida getPuertosSalidaJSON(JSONObject salida, Salida ret)
	    throws JSONException {
	log.debug("inicio");
	JSONObject[] puertos = null;
	Object obj = salida.get("puertos");
	if (obj instanceof JSONArray) {
	    JSONArray arr = (JSONArray) obj;
	    puertos = new JSONObject[arr.length()];
	    for (int i = 0; i < arr.length(); i++) {
		puertos[i] = arr.getJSONObject(i);
	    }
	} else {
	    puertos = new JSONObject[] { (JSONObject) obj };
	}
	for (JSONObject jo : puertos) {
	    ret.addPuerto(getPuertoJson(jo));
	}
	return ret;
    }

    private static Puerto getPuertoJson(JSONObject jo) throws JSONException {
	log.debug("inicio");
	Puerto puerto = new Puerto();
	puerto.setAltitud(getJSInt(jo,"altitud"));
	puerto.setDescripcion(getJSString(jo,"descripcion"));
	puerto.setDesnivelMedio(Float.valueOf((float) jo
		.getDouble("desnivelMedio")));
	puerto.setDesnivelMetros(getJSInt(jo,"desnivelMetros"));
	String fechaAlta = getJSString(jo,"fechaAlta");
	puerto.setFechaAlta(UtilFecha.parseFechaJson(fechaAlta));
	puerto.setIdPuerto(getJSLong(jo,"idPuerto"));
	String imgb64 = getJSString(jo,"imagen");
	puerto.setImagen(Base64.decode(imgb64));
	puerto.setKm(getJSDouble(jo,"km").floatValue());
	puerto.setNombre(getJSString(jo,"nombre"));
	puerto.setNombreExtendido(getJSString(jo,"nombreExtendido"));
	puerto.setRevisado(getJSBoolean(jo,"revisado"));

	JSONObject comarca = jo.getJSONObject("comarca");
	Comarca c = new Comarca();
	c.setCodProvincia(comarca.getInt("codProvincia"));
	c.setIdComarca(comarca.getLong("idComarca"));
	c.setNombre(comarca.getString("nombre"));
	c.setPais(comarca.getString("pais"));
	c.setProvincia(comarca.getString("provincia"));
	puerto.setComarca(c);

	return puerto;
    }

    private static Salida getDatosOracheSalidaJSON(JSONObject jsObj,
	    Salida salida) throws JSONException {
	log.debug("inicio");
	JSONObject jsOrache = jsObj.getJSONObject("oracheStart");
	Orache orache = new Orache();
	orache.setFecha(UtilFecha.formatFecha(salida.getDate()));
	orache.setMaxTemp(getJSString(jsOrache,"maxTemp"));
	orache.setMinTemp(getJSString(jsOrache,"minTemp"));
	orache.setMnyProbPre(getJSString(jsOrache,"mnyProbPre"));
	orache.setTardeProbPre(getJSString(jsOrache,"tardeProbPre"));
	orache.setMnyViento(getJSString(jsOrache,"mnyViento"));
	orache.setTardeViento(getJSString(jsOrache,"tardeViento"));
	salida.setOracheStart(orache);
	return salida;
    }

    private static Salida getDatosGeneralesSalidaJSON(JSONObject jsObj,
	    Salida salida) throws JSONException {
	log.debug("inicio");
	String fecha = getJSString(jsObj, "date");
	Date date = UtilFecha.parseFechaJson(fecha);
	salida.setDate(date);
	salida.setKm(getJSDouble(jsObj,"km").floatValue());
	salida.setReturnRoute(getJSString(jsObj, "returnRoute"));
	salida.setRoute(getJSString(jsObj, "route"));
	salida.setStop(getJSString(jsObj, "stop"));
	salida.setElevationGain(getJSInt(jsObj,"elevationGain"));

	return salida;
    }
    
    private static Integer getJSInt(JSONObject jso, String key){
	Integer ret = null;
	try{
	    ret = jso.getInt(key);
	} catch(JSONException e){
	    log.error("",e);
	}
	return ret;
    }
    
    private static Long getJSLong(JSONObject jso, String key){
	Long ret = null;
	try{
	    ret = jso.getLong(key);
	} catch(JSONException e){
	    log.error("",e);
	}
	return ret;
    }

    private static Boolean getJSBoolean(JSONObject jso, String key){
	Boolean ret = null;
	try{
	    ret = jso.getBoolean(key);
	} catch(JSONException e){
	    log.error("",e);
	}
	return ret;
    }

    private static Double getJSDouble(JSONObject jso, String key){
	Double ret = null;
	try{
	    ret = jso.getDouble(key);
	} catch(JSONException e){
	    log.error("",e);
	}
	return ret;
    }
    
    private static String getJSString (JSONObject jso, String key){
	String ret = null;
	try{
	    ret = jso.getString(key);
	} catch(JSONException e){
	    log.error("",e);
	}
	return ret;
    }
}
