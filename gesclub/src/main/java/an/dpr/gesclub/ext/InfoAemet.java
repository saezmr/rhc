package an.dpr.gesclub.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.services.beans.AemetEstadoCielo;
import an.dpr.gesclub.services.beans.AemetHora;
import an.dpr.gesclub.services.beans.AemetLocalidadTags;
import an.dpr.gesclub.services.beans.AemetPeriodo;
import an.dpr.gesclub.services.beans.AemetViento;
import an.dpr.gesclub.services.beans.Orache;
import an.dpr.gesclub.services.beans.PrediccionAemet;
import an.dpr.gesclub.util.UtilFecha;

public class InfoAemet {

    private static final Logger log = Logger.getLogger(InfoAemet.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
	    "yyyy-MM-dd");
    //TODO acordarse de cambiar ruta aemet de localhost a url real 
     private static final String AEMET_URL_BASE_1 = 
     "http://www.aemet.es/xml/municipios/localidad_";
//    private static final String AEMET_URL_BASE_1 = "http://localhost:8080/EnbizziAppWeb/localidad_";
    private static final String AEMET_URL_BASE_2 = ".xml";

    public InfoAemet() {
    }

    public Orache getPrediccion(Salida salida) {
	Orache orache = null;
	if (oracheEnFechas(salida)) {
	    try {
		PrediccionAemet pa;
		pa = getPrediccion(salida.getAemetCode(), salida.getDate());
		if (pa != null) {
		    orache = Orache.getOrache(pa);
		}
	    } catch (XmlPullParserException e) {
		log.error("Error parseando info aemet", e);
	    } catch (IOException e) {
		log.error("Error obteniendo xml info aemet", e);
	    }
	}
	return orache;
    }

    private boolean oracheEnFechas(Salida salida) {
	boolean ret = false;
	Date hoy = UtilFecha.quitarHora(Calendar.getInstance().getTime());
	if (salida.getDate()==null){
	    ret = false;
	}else if (salida.getDate().before(hoy)){
	    ret = false;
	} else if (salida.getDate().after(UtilFecha.sumaDias(hoy, 7))){
	    ret = false;
	} else {
	    ret = true;
	}
	return ret;
    }

    public PrediccionAemet getPrediccion(String aemetCode, Date fecha)
	    throws XmlPullParserException, IOException {
	log.debug("-init-");
	PrediccionAemet ret = null;
	String provincia = null;
	String localidad = null;
	String xml = leerXML(aemetCode);
	try {
	    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    factory.setNamespaceAware(true);
	    XmlPullParser xpp = factory.newPullParser();
	    xpp.setInput(new StringReader(xml));
	    int eventType = xpp.getEventType();
	    do {
		if (eventType == XmlPullParser.START_TAG) {
		    if (provincia == null) {
			provincia = getTagValue(xpp,
				AemetLocalidadTags.tag_provincia);
		    }
		    if (localidad == null) {
			localidad = getTagValue(xpp,
				AemetLocalidadTags.tag_nombre);
		    }
		    if (isDia(xpp, fecha)) {
			ret = leerPrediccion(xpp);
		    }
		}
		eventType = xpp.next();
	    } while (eventType != XmlPullParser.END_DOCUMENT && ret == null);
	    if (ret != null) {
		ret.setDia(fecha);
		ret.setLocalidad(localidad);
		ret.setProvincia(provincia);
	    }
	} catch (Exception e) {
	    log.error("", e);
	}
	return ret;
    }

    private String getTagValue(XmlPullParser xpp, AemetLocalidadTags pTag)
	    throws XmlPullParserException, IOException {
	String ret = null;
	if (xpp != null && pTag != null) {
	    AemetLocalidadTags actualTag = AemetLocalidadTags
		    .get(xpp.getName());
	    if (pTag.equals(actualTag)) {
		xpp.next();
		ret = xpp.getText();
		log.debug("Estamos en tag " + pTag.name() + " con ret = " + ret);
	    }
	}
	return ret;
    }

    private String leerXML(String aemetCode) throws IOException {
	StringBuilder ret = new StringBuilder();
	try {
	    HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(getAemetUrl(aemetCode));
	    HttpResponse response = client.execute(post);
	    InputStreamReader isr = new InputStreamReader(response.getEntity()
		    .getContent(), "cp1252");
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null) {
		ret.append(line);
	    }
	} catch (ClientProtocolException e) {
	    log.error("error leyendo info meteo", e);
	} catch (IOException e) {
	    log.error("error leyendo info meteo", e);
	}
	return ret.toString();
    }

    private String getAemetUrl(String aemetCode) {
	StringBuilder sb = new StringBuilder();
	sb.append(AEMET_URL_BASE_1);
	sb.append(aemetCode);
	sb.append(AEMET_URL_BASE_2);
	return sb.toString();
    }

    private boolean isDia(XmlPullParser xpp, Date fecha) {
	boolean ret = false;
	if (xpp != null && fecha != null) {
	    AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	    if (AemetLocalidadTags.tag_dia.equals(tag)) {
		log.debug("Estamos en tag DIA");
		String pFecha = getAttributeValue(xpp,
			AemetLocalidadTags.param_fecha.getValue());
		log.debug("FECHA:" + pFecha);
		if (sdf.format(fecha).equals(pFecha)) {
		    ret = true;
		}
	    }
	}
	return ret;
    }

    private String getAttributeValue(XmlPullParser parser, String clave) {
	String retValue = null;
	if (parser != null && clave != null) {
	    for (int i = 0; i < parser.getAttributeCount(); i++) {
		if (parser.getAttributeName(i).equals(clave)) {
		    retValue = parser.getAttributeValue(i);
		}
	    }
	}
	return retValue;
    }

    private PrediccionAemet leerPrediccion(XmlPullParser xpp)
	    throws XmlPullParserException, IOException, ParseException {
	log.debug("-init-");
	int eventType = xpp.getEventType();
	PrediccionAemet prediccion = new PrediccionAemet();
	prediccion = getDia(xpp, prediccion);
	do {
	    AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	    if (tag != null && eventType == XmlPullParser.START_TAG) {
		switch (tag) {
		case tag_estado_cielo:
		    prediccion = addEstadoCielo(prediccion, xpp);
		    break;
		case tag_temperatura:
		    prediccion = addTemperatura(prediccion, xpp);
		    break;
		case tag_viento:
		    prediccion = addViento(prediccion, xpp);
		    break;
		case tag_prob_precipitacion:
		    prediccion = addProbPrecipitacion(prediccion, xpp);
		    break;
		case tag_racha_max:
		    prediccion = addRachaMax(prediccion, xpp);
		    break;
		default:
		    break;
		}
	    } else if (eventType == XmlPullParser.END_TAG
		    && AemetLocalidadTags.tag_dia.equals(tag)) {
		log.debug("fin prediccion, salimos");
		break;
	    }
	    eventType = xpp.next();
	} while (eventType != XmlPullParser.END_DOCUMENT);
	log.debug(prediccion.toString());
	return prediccion;
    }

    // <racha_max periodo="00-12"></racha_max>
    private PrediccionAemet addRachaMax(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	int eventType = xpp.getEventType();
	if (AemetLocalidadTags.tag_racha_max.equals(tag)
		&& eventType == XmlPullParser.START_TAG) {
	    String sPer = getAttributeValue(xpp,
		    AemetLocalidadTags.param_periodo.getValue());
	    AemetPeriodo periodo = AemetPeriodo.getPeriodo(sPer);
	    xpp.next();
	    // comprobamos si esta en tag cierre o contenido
	    tag = AemetLocalidadTags.get(xpp.getName());
	    if (tag == null) {
		Integer racha = Integer.valueOf(xpp.getText());
		prediccion.getRachaMax().put(periodo, racha);
		xpp.next();// puntero a tag cierre
	    } else {
		// no hay valor de racha, asignamos 0
		prediccion.getRachaMax().put(periodo, 0);
	    }
	}
	return prediccion;
    }

    // <prob_precipitacion periodo="00-12">5</prob_precipitacion>
    private PrediccionAemet addProbPrecipitacion(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	int eventType = xpp.getEventType();
	if (AemetLocalidadTags.tag_prob_precipitacion.equals(tag)
		&& eventType == XmlPullParser.START_TAG) {
	    AemetPeriodo periodo = AemetPeriodo.getPeriodo(getAttributeValue(
		    xpp, AemetLocalidadTags.param_periodo.getValue()));
	    xpp.next();
	    Integer prob = Integer.valueOf(xpp.getText());
	    prediccion.getProbPrecipitacion().put(periodo, prob);
	    xpp.next();// colocamos en tag de cierre
	}
	return prediccion;
    }

    private PrediccionAemet getDia(XmlPullParser xpp, PrediccionAemet prediccion)
	    throws ParseException, XmlPullParserException {
	AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	int eventType = xpp.getEventType();
	if (eventType == XmlPullParser.START_TAG
		&& AemetLocalidadTags.tag_dia.equals(tag)) {
	    String pFecha = getAttributeValue(xpp,
		    AemetLocalidadTags.param_fecha.getValue());
	    prediccion.setDia(sdf.parse(pFecha));
	}
	return prediccion;
    }

    // <estado_cielo periodo="00-12" descripcion="Nuboso">14</estado_cielo>
    private PrediccionAemet addEstadoCielo(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	log.debug("-init-");
	int eventType = xpp.getEventType();
	AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
	if (AemetLocalidadTags.tag_estado_cielo.equals(tag)
		&& eventType == XmlPullParser.START_TAG) {
	    String periodo = getAttributeValue(xpp,
		    AemetLocalidadTags.param_periodo.getValue());
	    String descripcion = getAttributeValue(xpp,
		    AemetLocalidadTags.param_descripcion.getValue());
	    // pasamos al contenido
	    xpp.next();
	    String estado = xpp.getText();
	    AemetEstadoCielo aec = new AemetEstadoCielo();
	    aec.setPeriodo(AemetPeriodo.getPeriodo(periodo));
	    aec.setCode(estado);
	    aec.setDescripcion(descripcion);
	    prediccion.getEstadoCielo().add(aec);
	    // dejamos el puntero en el tag de cierre
	    xpp.next();
	}
	return prediccion;
    }

    private PrediccionAemet addTemperatura(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	log.debug("-init-");
	boolean continuar = true;
	int eventType = 0;
	AemetLocalidadTags tag;
	do {
	    eventType = xpp.getEventType();
	    tag = AemetLocalidadTags.get(xpp.getName());
	    if (tag != null) {
		switch (tag) {
		case tag_maxima:
		    prediccion = addMaxTemperatura(prediccion, xpp);
		    break;
		case tag_minima:
		    prediccion = addMinTemperatura(prediccion, xpp);
		    break;
		case tag_dato:
		    prediccion = addDatoTemperatura(prediccion, xpp);
		    break;
		case tag_temperatura:
		    if (eventType == XmlPullParser.END_TAG) {
			continuar = false;
		    }
		    break;
		}
	    }
	    if (continuar) {
		xpp.next();
	    }
	} while (continuar);
	return prediccion;
    }

    private PrediccionAemet addMaxTemperatura(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	log.debug("-init-");
	if (xpp != null && xpp.getEventType() == XmlPullParser.START_TAG) {
	    xpp.next();
	    String maxima = xpp.getText();
	    prediccion.setMaxTemperatura(Integer.valueOf(maxima));
	}
	return prediccion;
    }

    private PrediccionAemet addMinTemperatura(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	log.debug("-init-");
	if (xpp != null && xpp.getEventType() == XmlPullParser.START_TAG) {
	    xpp.next();
	    String minima = xpp.getText();
	    prediccion.setMinTemperatura(Integer.valueOf(minima));
	}
	return prediccion;
    }

    private PrediccionAemet addDatoTemperatura(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	log.debug("-init-");
	if (xpp != null && xpp.getEventType() == XmlPullParser.START_TAG) {
	    String hora = getAttributeValue(xpp,
		    AemetLocalidadTags.param_hora.getValue());
	    xpp.next();
	    String temp = xpp.getText();
	    prediccion.getHoraTemperatura().put(AemetHora.getPeriodo(hora),
		    Integer.valueOf(temp));
	}
	return prediccion;
    }

    private PrediccionAemet addViento(PrediccionAemet prediccion,
	    XmlPullParser xpp) throws XmlPullParserException, IOException {
	int eventType = 0;
	AemetLocalidadTags tag;
	boolean continuar = true;
	AemetViento av = new AemetViento();
	do {
	    tag = AemetLocalidadTags.get(xpp.getName());
	    eventType = xpp.getEventType();
	    if (tag != null) {
		switch (tag) {
		case tag_viento:
		    if (eventType == XmlPullParser.START_TAG) {
			String periodo = getAttributeValue(xpp,
				AemetLocalidadTags.param_periodo.getValue());
			av.setPeriodo(AemetPeriodo.getPeriodo(periodo));
		    } else {
			continuar = false;
		    }
		    break;
		case tag_direccion:
		    if (eventType == XmlPullParser.START_TAG) {
			xpp.next();
			av.setDireccion(xpp.getText());
		    }
		    break;
		case tag_velocidad:
		    if (eventType == XmlPullParser.START_TAG) {
			xpp.next();
			av.setVelocidad(Integer.valueOf(xpp.getText()));
		    }
		    break;
		}
	    }
	    if (continuar) {
		xpp.next();
	    }
	} while (continuar);
	prediccion.getViento().add(av);
	return prediccion;
    }

}
