package an.dpr.pruebafit;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.reader.FitTrackInfo;
import an.dpr.routeanalyzer.util.UtilSwing;

import com.garmin.fit.Decode;
import com.garmin.fit.MesgDefinitionListener;
import com.garmin.fit.MesgListener;
import com.garmin.fit.csv.MesgFilter;

public class Prueba {

    private static final Logger log = Logger.getLogger(Prueba.class);
    public static void main(String[] args) {
	getListado();
    }

    public static List<HRBean> getListado() {
	List<HRBean> list = null;

	Decode decode = new Decode();
	FileInputStream in = null;

	System.out.println("FIT Decode Example Application");
	String fileName = null;
	java.io.File file = UtilSwing.getFile("fit");
	 fileName = file.getAbsolutePath();
	// fileName =
	// "/home/rsaez/Documents/GarminActivities/fit/2013-11-30-09-00-51.fit";

	try {
	    if (!Decode.checkIntegrity((InputStream) new FileInputStream(file)))
		throw new RuntimeException("FIT file integrity failed.");
	    in = new FileInputStream(file);
	    MesgFilter mesgFilter = new MesgFilter();
	    ArrayList<String> mesgDefinitionsToOutput = new ArrayList<String>();
	    ArrayList<String> dataMessagesToOutput = new ArrayList<String>();
	    mesgFilter.setMesgDefinitionsToOutput(mesgDefinitionsToOutput);
	    mesgFilter.setDataMessagesToOutput(dataMessagesToOutput);
	    MesgDataToString dataMesgWriter = new MesgDataToString();
	    mesgFilter.addListener((MesgListener) dataMesgWriter);

	    decode.addListener((MesgDefinitionListener) mesgFilter);
	    decode.addListener((MesgListener) mesgFilter);

	    decode.read(in);
	    String info = dataMesgWriter.getInfo();
	    FitTrackInfo fti = new FitTrackInfo();
	    list = fti.getHRList(info);
	    log.info(list);
	} catch (java.io.IOException e) {
	    throw new RuntimeException("Error opening file ");
	} finally {
	    try {
		in.close();
	    } catch (java.io.IOException e) {
		throw new RuntimeException(e);
	    }
	}
	return list;
    }
}
