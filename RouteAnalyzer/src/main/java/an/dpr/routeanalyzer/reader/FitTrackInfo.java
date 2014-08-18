package an.dpr.routeanalyzer.reader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.FitActivityRecord;
import an.dpr.routeanalyzer.bean.FitAltimetryPoint;
import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;
import an.dpr.routeanalyzer.domain.Activity;

import com.garmin.fit.DateTime;

public class FitTrackInfo implements ITrackInfo {

    private static final Logger log = Logger.getLogger(FitTrackInfo.class);
    private static final String CSV_SEPARATOR = ",";
    private FitReader reader;

    @Override
    public List<HRBean> getHRList(File file) throws IOException {
	String cadena = reader.readFile(file);
	return getHRList(cadena);
    }

    @Override
    public List<HRBean> getHRList(String cadena) {
	List<HRBean> data = new ArrayList<HRBean>();
	String[] lineas = cadena.split("\n");
	for (int i = 0; i < lineas.length; i++) {
	    String linea = lineas[i];
	    if (linea != null && !linea.trim().equals("")) {
		HRBean hr = getHrBean(linea);
		if (hr != null)
		    data.add(hr);
	    }
	}
	data = TrackInfoUtil.completarTiempos(data);
	return data;
    }

    private HRBean getHrBean(String linea) {
	HRBean hr = new HRBean();
	try {
	    String[] tokens = linea.split(CSV_SEPARATOR);
	    for (String token : tokens) {
		token = token.replace(FitField.RECORD_PREFIX, "");
		String sField = token.split(FitField.FIELD_SEPARATOR)[0].trim();
		String value = token.split(FitField.FIELD_SEPARATOR)[1].trim();
		FitField field = FitField.valueOf(sField);
		switch (field) {
		case heart_rate_bpm:
		    hr.setHr(Integer.valueOf(value));
		    break;
		case timestamp_s:
		    hr.setTime(Long.valueOf(value));
		    break;
		default:
		    break;

		}
	    }
	} catch (Exception e) {
	    log.error(e);
	    hr = null;
	}
	return hr;
    }

    @Override
    public HRZones getHRZones(String cadena) {
	List<HRBean> listado = getHRList(cadena);
	return TrackInfoUtil.getHRZones(listado);
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	Set<AltimetryPoint> data = new TreeSet<AltimetryPoint>();
	String[] lineas = cadena.split("\n");
	Double metrosAnt = 0.0;
	for (int i = 0; i < lineas.length; i++) {
	    String linea = lineas[i];
	    if (linea != null && !linea.trim().equals("")) {
		FitAltimetryPoint ap = getAltimetryPoint(linea);
		if (ap != null && ap.getMetros() != null) {
		    Double metrosAvanzados = ap.getMetros() - metrosAnt;
		    metrosAnt = ap.getMetros();
		    ap.setMetros(metrosAvanzados);
		    data.add(ap);
		}
	    }
	}
	return data;
    }

    private FitAltimetryPoint getAltimetryPoint(String linea) {
	FitAltimetryPoint ap = new FitAltimetryPoint();
	try {
	    String[] tokens = linea.split(CSV_SEPARATOR);
	    for (String token : tokens) {
		token = token.replace(FitField.RECORD_PREFIX, "");
		String sField = token.split(FitField.FIELD_SEPARATOR)[0].trim();
		String value = token.split(FitField.FIELD_SEPARATOR)[1].trim();
		FitField field = FitField.valueOf(sField);
		switch (field) {
		case altitude_m:
		    ap.setAltitud(Double.valueOf(value));
		    break;
		case timestamp_s:
		    DateTime dt = new DateTime(Long.valueOf(value));
		    ap.setDate(dt.getDate());
		    break;
		case distance_m:
		    ap.setMetros(Double.valueOf(value));
		    break;
		default:
		    break;

		}
	    }
	} catch (Exception e) {
	    log.error(e);
	    ap = null;
	}
	return ap;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException {
	String cadena = reader.readFile(file);
	return getAltimetrySet(cadena);
    }

    public FitReader getReader() {
	return reader;
    }

    public void setReader(FitReader reader) {
	this.reader = reader;
    }

    @Override
    public Activity getActivityInfo(File file) {
	Activity retValue = null;
	String cadena = reader.readFile(file);
	log.debug(cadena);
	retValue = getActivityInfo(cadena);
	return retValue;
    }

    /**
     * TODO refactorizar, metodo muy largo
     * @param cadena
     * @return
     */
    private Activity getActivityInfo(String cadena) {
	Activity act = null;
	if (cadena != null) {
	    act = new Activity();
	    Set<FitActivityRecord> set = getActivitySet(cadena);
	    boolean first = true;
	    Long moveTime = (long) 0.0;
	    Long ts_aux = (long)0.0;
	    Double metros = 0.0;
	    FitActivityRecord rec = null;
	    for(FitActivityRecord obj : set){
		rec = obj;
		if (first){
		    Long ts_ini = rec.getTimestamp();
		    DateTime dt = new DateTime(ts_ini);
		    act.setFecha(dt.getDate());
		    first = false;
		} else if(metros != null &&
			rec.getDistance() != null) {
		    Double mAnt = metros;
		    if (mAnt < rec.getDistance()){

			DateTime dti = new DateTime(ts_aux);
			DateTime dtf = new DateTime(rec.getTimestamp());
			moveTime += dtf.getTimestamp()-dti.getTimestamp();
		    }
		}
		metros = rec.getDistance();
		ts_aux = rec.getTimestamp();
	    }
	    if (rec != null){
		metros = rec.getDistance();
		act.setKm((metros == null ? 0.0 : metros)/1000);
		Long ts_fin = rec.getTimestamp();
		DateTime dt = new DateTime(ts_fin);
		act.setFechaFin(dt.getDate());
		act.setMinutosMov(moveTime.intValue()/60);
	    } else {
		act.setKm(0.0);
	    }
		
	    HRZones hrz = getHRZones(cadena);
	    act.setHeartRateAvg(hrz.getMedia().intValue());
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    act.setNombre("activity_"+sdf.format(act.getFecha()));
	}
	return act;
    }
    
    private Set<FitActivityRecord> getActivitySet(String cadena) {
	Set<FitActivityRecord> data = new TreeSet<FitActivityRecord>();
	String[] lineas = cadena.split("\n");
	Double metrosAnt = 0.0;
	for (int i = 0; i < lineas.length; i++) {
	    String linea = lineas[i];
	    if (linea != null && !linea.trim().equals("")) {
		FitActivityRecord ap = getFitActivityRecord(linea);
		if (ap != null) {
		    data.add(ap);
		}
	    }
	}
	return data;
    }

    private FitActivityRecord getFitActivityRecord(String linea) {
	FitActivityRecord ap = new FitActivityRecord();
	try {
	    String[] tokens = linea.split(CSV_SEPARATOR);
	    for (String token : tokens) {
		token = token.replace(FitField.RECORD_PREFIX, "");
		String sField = token.split(FitField.FIELD_SEPARATOR)[0].trim();
		String value = token.split(FitField.FIELD_SEPARATOR)[1].trim();
		FitField field = FitField.valueOf(sField);
		switch (field) {
		case altitude_m:
		    ap.setAltitude(Double.valueOf(value));
		    break;
		case timestamp_s:
		    ap.setTimestamp(new Long(value));
		    break;
		case distance_m:
		    ap.setDistance(Double.valueOf(value));
		    break;
		case cadence_rpm:
		    ap.setCadence(Integer.valueOf(value));
		    break;
		case heart_rate_bpm:
		    ap.setHeartRate(Integer.valueOf(value));
		    break;
		case position_lat_semicircles:
		    ap.setPositionLat(Long.valueOf(value));
		    break;
		case position_long_semicircles:
		    ap.setPositionLong(Long.valueOf(value));
		    break;
		case temperature_C:
		    ap.setTemperature(Integer.valueOf(value));
		    break;
		default:
		    break;
		}
	    }
	} catch (Exception e) {
	    log.error(e);
	    ap = null;
	}
	return ap;
    }

}
