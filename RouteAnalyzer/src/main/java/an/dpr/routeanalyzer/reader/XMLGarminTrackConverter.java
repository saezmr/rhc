package an.dpr.routeanalyzer.reader;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import an.dpr.routeanalyzer.bean.AltitudBean;
import an.dpr.routeanalyzer.bean.HRBean;


public class XMLGarminTrackConverter  implements GPXReader{

    private static final Logger log = Logger
	    .getLogger(XMLGarminTrackConverter.class);
    
    public static final String nameSpace = null;
    
//    public static void main(String[] args) throws IOException, XmlPullParserException{
//	File f = new File("c:\\home\\ganalize\\activity_396370338.gpx");
//	FileReader fr = new FileReader(f);
//	BufferedReader br = new BufferedReader(fr);
//	StringBuffer xml = new StringBuffer();
//	String line = null;
//	while((line = br.readLine())!= null){
//	    xml.append(line);
//	}
////	List<HRBean> listado = getHRList(xml.toString());
////	log.debug(listado);
//	List<AltitudBean> listado2 = getAltitudList(xml.toString());
//	Double metros = 0.0;
//	for(AltitudBean ab : listado2){
//	    metros += ab.getMetrosAvanzados();
//	    log.debug(metros+"-"+ab.getAltitud());
//	}
////	log.debug(listado2);
//	
//    }
    
    /**
     * Constructor vacio for spring
     */
    public XMLGarminTrackConverter(){}
    
    public List<HRBean> getHRList(String xml) {
	List<HRBean> list = null;
	try {
	    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    factory.setNamespaceAware(true);
	    XmlPullParser parser = factory.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(new StringReader(xml));// in, null);
	    parser.nextTag();
	    list = readHRTracks(parser);
	} catch (XmlPullParserException e) {
	    log.error("Error leyendo xml", e);
	} catch (IOException e) {
	    log.error("Error leyendo xml", e);
	}
	return list;
    }


    private static List<HRBean> readHRTracks(XmlPullParser parser) 
	    throws XmlPullParserException, IOException {
	log.debug("inicio");
	List<HRBean> list = new ArrayList<HRBean>();

	parser.require(XmlPullParser.START_TAG, nameSpace,
		GPXTags.gpx.name());
	while (parser.next() != XmlPullParser.END_TAG) {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
		continue;
	    }
	    String name = parser.getName();
	    // Starts by looking for the entry tag
	    if (name.equals(GPXTags.trkpt.name())) {
		list.add(getHRTrackItem(parser));
	    } else if (
		    !name.equals(GPXTags.trkseg.name())
		    && !name.equals(GPXTags.trk.name()) ) {
		skip(parser);
	    }
	}
//	log.debug(list.toString());
	return list;
    }

    private static HRBean getHRTrackItem(XmlPullParser parser)
	    throws XmlPullParserException, IOException {
	HRBean bean = new HRBean();
	parser.require(XmlPullParser.START_TAG, nameSpace,
		GPXTags.trkpt.name());
	boolean continuar = true;
	do {
	    int next = parser.next();
//	    log.debug(parser.getName());
	    GPXTags tag = null;
	    if (parser.getName() != null){
		tag = GPXTags.getValueOf(parser.getName());
	    }
	    if (tag != null 
		    && next == XmlPullParser.END_TAG
		    && GPXTags.trkpt.equals(tag)) {
		continuar = false;
	    } else if (tag == null 
		    || next != XmlPullParser.START_TAG){
		continue;
	    } else if( tag.equals(GPXTags.extensions)
		    || tag.equals(GPXTags.gpxtpx_TrackPointExtension)
		    || tag.equals(GPXTags.gpxtpx_atemp)
		    || tag.equals(GPXTags.gpxtpx_cad)
		    ) {
		continue;
	    } else {
		String valor = readTag(parser, tag);
		if (valor != null) {
		    switch (tag) {
		    case time:
			bean.setTime(parseaFecha(valor));
			break;
		    case gpxtpx_hr:
			bean.setHr(Integer.valueOf(valor));
			break;
		    default:
			break;
		    }
		}
	    }
	} while (continuar);

	return bean;
    }
    
    public List<AltitudBean> getAltitudList(String xml) {
	List<AltitudBean> list = null;
	try {
	    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    factory.setNamespaceAware(true);
	    XmlPullParser parser = factory.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(new StringReader(xml));// in, null);
	    parser.nextTag();
	    list = readAltitudTracks(parser);
	} catch (XmlPullParserException e) {
	    log.error("Error leyendo xml", e);
	} catch (IOException e) {
	    log.error("Error leyendo xml", e);
	}
	return list;
    }
    
    private static List<AltitudBean> readAltitudTracks(XmlPullParser parser) 
	    throws XmlPullParserException, IOException {
	log.debug("inicio");
	List<AltitudBean> list = new ArrayList<AltitudBean>();
	
	parser.require(XmlPullParser.START_TAG, nameSpace,
		GPXTags.gpx.name());
	AltitudBean bean = null;
	while (parser.next() != XmlPullParser.END_TAG) {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
		continue;
	    }
	    String name = parser.getName();
	    // Starts by looking for the entry tag
	    if (name.equals(GPXTags.trkpt.name())) {
		bean = getAltitudTrackItem(parser, bean);
		list.add(bean);
	    } else if (
		    !name.equals(GPXTags.trkseg.name())
		    && !name.equals(GPXTags.trk.name()) ) {
		skip(parser);
	    }
	}
//	log.debug(list.toString());
	return list;
    }
    
    private static AltitudBean getAltitudTrackItem(XmlPullParser parser, AltitudBean anterior)
	    throws XmlPullParserException, IOException {
	AltitudBean bean = new AltitudBean();
	bean = setCoordenadasGPS(parser, bean, anterior);
	parser.require(XmlPullParser.START_TAG, nameSpace,
		GPXTags.trkpt.name());
	boolean continuar = true;
	do {
	    int next = parser.next();
//	    log.debug(parser.getName());
	    GPXTags tag = null;
	    if (parser.getName() != null){
		tag = GPXTags.getValueOf(parser.getName());
	    }
	    if (tag != null 
		    && next == XmlPullParser.END_TAG
		    && GPXTags.trkpt.equals(tag)
		    ) {
		continuar = false;
	    } else if (tag == null 
		    || next != XmlPullParser.START_TAG){
		continue;
	    } else if( tag.equals(GPXTags.extensions)
		    || tag.equals(GPXTags.gpxtpx_TrackPointExtension)
		    || tag.equals(GPXTags.gpxtpx_atemp)
		    || tag.equals(GPXTags.gpxtpx_cad)
		    ) {
		continue;
	    } else {
		String valor = readTag(parser, tag);
		if (valor != null) {
		    switch (tag) {
		    case ele:
			bean.setAltitud(Double.valueOf(valor));
			break;
		    case time:
			bean.setTime(parseaFecha(valor));
			break;
		    default:
			break;
		    }
		}
	    }
	} while (continuar);
	return bean;
    }
    

    private static AltitudBean setCoordenadasGPS(XmlPullParser parser,
	    AltitudBean bean, AltitudBean anterior) {
	bean.setLon1(anterior != null ? anterior.getLon2() : null);
	bean.setLat1(anterior != null ? anterior.getLat2() : null);
	bean.setLon2(Double.valueOf(parser.getAttributeValue(0)));
	bean.setLat2(Double.valueOf(parser.getAttributeValue(1)));
	return bean;
    }

    private static Long parseaFecha(String fecha) {
	Long ret = Long.valueOf(0);
	if (fecha != null) {
	    fecha = fecha.replace("T", "").replace("Z", "");
	    SimpleDateFormat sdf = new SimpleDateFormat(
		    "yyyy-MM-ddHH:mm:ss.SSS");
	    try {
		Date date = sdf.parse(fecha);
		ret = date.getTime();
	    } catch (ParseException e) {
		log.error("error parseando fecha", e);
	    }
	}
	return ret;
    }
    
    private static String readTag(XmlPullParser parser, GPXTags tag)
	    throws IOException, XmlPullParserException {
	parser.require(XmlPullParser.START_TAG, nameSpace, tag.getTag());
	String valor = null;
	// Log.d(TAG + ".readTag", tag.name());
	if (parser.next() == XmlPullParser.TEXT) {
	    valor = parser.getText();
	    parser.nextTag();
	}
	parser.require(XmlPullParser.END_TAG, nameSpace, tag.getTag());
	return valor;
    }

    private static String readTagAttribute(XmlPullParser parser, GPXTags tag, int index)
	    throws IOException, XmlPullParserException {
	parser.require(XmlPullParser.START_TAG, nameSpace, tag.getTag());
	String valor = null;
	// Log.d(TAG + ".readTag", tag.name());
	if (parser.next() == XmlPullParser.TEXT) {
	    valor = parser.getAttributeValue(index);
	    parser.nextTag();
	}
	parser.require(XmlPullParser.END_TAG, nameSpace, tag.getTag());
	return valor;
    }

    /** va saltando tags */
    private static void skip(XmlPullParser parser)
	    throws XmlPullParserException, IOException {
	if (parser.getEventType() != XmlPullParser.START_TAG) {
	    throw new IllegalStateException();
	}
	int depth = 1;
	while (depth != 0) {
	    switch (parser.next()) {
	    case XmlPullParser.END_TAG:
		depth--;
		break;
	    case XmlPullParser.START_TAG:
		depth++;
		break;
	    }
	}
    }
    
//    public static void main(String[] args){
//	double lon1=-0.9300573170185089;
//	double lat1=41.619857316836715;
//	double lon2=-0.9928177390247583; 
//	double lat2=41.63073937408626;
//	System.out.println(getMeters(lat1, lon1, lat2, lon2));
//    }
    
    public static double getMeters(double lat1, double lon1, double lat2, double lon2) {
	   int R = 6371; // earth's mean radius in km
	   double dLat = lat2-lat1;
	   double dLon = lon2-lon1;
	   double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	   Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon/2) * Math.sin(dLon/2);
	   double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	   double d = R * c;

	   return d;
	}

}
