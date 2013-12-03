package an.dpr.routeanalyzer.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jfree.util.Log;

import an.dpr.routeanalyzer.bean.AltimetryPoint;
import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;

public class FitTrackInfo implements ITrackInfo {

    private static final String CSV_SEPARATOR = ",";

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
	    Log.error(e);
	    hr = null;
	}
	return hr;
    }

    @Override
    public HRZones getHRZones(String cadena) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(String cadena) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<AltimetryPoint> getAltimetrySet(File file) throws IOException {
	// TODO Auto-generated method stub
	return null;
    }

}
