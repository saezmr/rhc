package an.dpr.enbizzi.calendar;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import an.dpr.enbizzi.beans.CyclingType;
import an.dpr.enbizzi.beans.Difficulty;
import an.dpr.enbizzi.dao.PuertosDAO;
import an.dpr.enbizzi.domain.Salida;

public class XMLCalendarConverter {

    @Autowired PuertosDAO puertosDAO;
    private static final Logger log = Logger
	    .getLogger(XMLCalendarConverter.class);
    
    public static final String nameSpace = null;
    
    /**
     * Constructor vacio for spring
     */
    public XMLCalendarConverter(){}

    public List<Salida> getCalendarViaNewPullParser(String xml)
	    throws XmlPullParserException, IOException {
	List<Salida> rv = null;
	try {
	    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	    factory.setNamespaceAware(true);
	    XmlPullParser parser = factory.newPullParser();
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    parser.setInput(new StringReader(xml));// in, null);
	    parser.nextTag();
	    rv = readCalendar(parser);
	} catch (IOException e) {
	    log.error("Error leyendo xml calendar", e);
	}
	return rv;
    }

    private List<Salida> readCalendar(XmlPullParser parser)
	    throws XmlPullParserException, IOException {
	log.debug("readCalendar");
	List<Salida> list = new ArrayList<Salida>();

	parser.require(XmlPullParser.START_TAG, nameSpace,
		SalidaXMLTags.calendar.name());
	while (parser.next() != XmlPullParser.END_TAG) {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
		continue;
	    }
	    String name = parser.getName();
	    // Starts by looking for the entry tag
	    if (name.equals(SalidaXMLTags.event.name())) {
		list.add(getCalendarItem(parser));
	    } else if (!name.equals(SalidaXMLTags.events.name())) {
		skip(parser);
	    }
	}
	log.debug(list.toString());
	return list;
    }

    private Salida getCalendarItem(XmlPullParser parser)
	    throws XmlPullParserException, IOException {
	Salida bCal = new Salida();
	parser.require(XmlPullParser.START_TAG, nameSpace,
		SalidaXMLTags.event.name());
	boolean continuar = true;
	do {
	    int next = parser.next();
	    SalidaXMLTags bcx = SalidaXMLTags.valueOf(parser.getName());
	    if (next == XmlPullParser.END_TAG
		    && SalidaXMLTags.event.equals(bcx)) {
		continuar = false;
	    } else if (next != XmlPullParser.START_TAG) {
		continue;
	    } else {
		String valor = readTag(parser, bcx);
		if (valor != null) {
		    switch (bcx) {
		    case elevationGain:
			bCal.setElevationGain(Integer.valueOf(valor));
			break;
		    case date:
			bCal.setDate(valor);
			break;
		    case difficulty:
			bCal.setDifficulty(Difficulty.valueOf(valor).getDomain());
			break;
		    case km:
			bCal.setKm(Float.valueOf(valor));
			break;
		    case route:
			bCal.setRoute(valor);
			break;
		    case returnRoute:
			bCal.setReturnRoute(valor);
			break;
		    case stop:
			bCal.setStop(valor);
			break;
		    case type:
			bCal.setType(CyclingType.valueOf(valor).getDomain());
			break;
		    case aemetCodeStart:
			bCal.setAemetStart(Integer.valueOf(valor));
			break;
		    case aemetCodeStop:
			bCal.setAemetStop(Integer.valueOf(valor));
			break;
		    case puerto:
			addPuerto(bCal, valor);
			break;
		    default:
			break;
		    }
		}
	    }
	} while (continuar);

	return bCal;
    }

    private void addPuerto(Salida bCal, String nombre) {
	bCal.addPuerto(puertosDAO.findByNombre(nombre));
    }

    private String readTag(XmlPullParser parser, SalidaXMLTags tag)
	    throws IOException, XmlPullParserException {
	parser.require(XmlPullParser.START_TAG, nameSpace, tag.name());
	String valor = null;
	// Log.d(TAG + ".readTag", tag.name());
	if (parser.next() == XmlPullParser.TEXT) {
	    valor = parser.getText();
	    parser.nextTag();
	}
	parser.require(XmlPullParser.END_TAG, nameSpace, tag.name());
	return valor;
    }

    /** va saltando tags */
    private void skip(XmlPullParser parser)
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

}
