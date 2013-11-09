package an.dpr.garmin.analize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParserException;

public class GarminAnalize {

    private static final Logger log = Logger.getLogger(GarminAnalize.class);
    private static final int z0 = 121;
    private static final int z1 = 135;
    private static final int z2 = 149;
    private static final int z3 = 163;
    private static final int z4 = 176;
    private static final int z5 = 190;

    public static void main(String[] args) throws IOException,
	    XmlPullParserException {
	File f = new File("c:\\home\\ganalize\\activity_314804762.gpx");
	FileReader fr = new FileReader(f);
	BufferedReader br = new BufferedReader(fr);
	StringBuffer xml = new StringBuffer();
	String line = null;
	while ((line = br.readLine()) != null) {
	    xml.append(line);
	}
	List<HRBean> listado = XMLGraminTrackConverter
		.getHRList(xml.toString());
	listado = completarTiempos(listado);
	int totalSec = listado.size();
	Collections.sort(listado);
	obtenerInfoZonas(listado);
    }

    private static void obtenerInfoZonas(List<HRBean> listado) {
	double[] hr =new double[10];
	hr[8] = listado.size();
	for (HRBean bean : listado) {
	    hr[7] += bean.getHr();
	    if (bean.getHr() < z0) {
		hr[0]++;
	    } else if (bean.getHr() < z1) {
		hr[1]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z2) {
		hr[2]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z3) {
		hr[3]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z4) {
		hr[4]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z5) {
		hr[5]++;
		hr[9] += bean.getHr();
	    } else {
		hr[6]++;
		hr[9] += bean.getHr();
	    }
	}
	printPorcentajeZonas(hr);
	printTiempoZonas(hr);
    }
	
	
    private static void printTiempoZonas(double[] hr) {
	log.debug("total:"+new Time((long)(hr[8]-3600)*1000));
	log.debug("total z 1-6:"+new Time((long)(hr[8]-hr[0]-3600)*1000));
	log.debug("zona 0:" + new Time((long)(hr[0]-3600)*1000));
	log.debug("zona 1:" + new Time((long)(hr[1]-3600)*1000));
	log.debug("zona 2:" + new Time((long)(hr[2]-3600)*1000));
	log.debug("zona 3:" + new Time((long)(hr[3]-3600)*1000));
	log.debug("zona 4:" + new Time((long)(hr[4]-3600)*1000));
	log.debug("zona 5:" + new Time((long)(hr[5]-3600)*1000));
	log.debug("zona 6:" + new Time((long)(hr[6]-3600)*1000));
    }
    
    

    private static void printPorcentajeZonas(double[] hr) {
	DecimalFormat df = new DecimalFormat("0.00");
	log.debug("pul media:" + (hr[7] / hr[8]));
	log.debug("pul m z 1-6:" + (hr[9] / (hr[8]-hr[0])));
	log.debug("zona 0:" + df.format(((hr[0] / hr[8]) * 100)));
	log.debug("zona 1:" + df.format(((hr[1] / hr[8]) * 100)));
	log.debug("zona 2:" + df.format(((hr[2] / hr[8]) * 100)));
	log.debug("zona 3:" + df.format(((hr[3] / hr[8]) * 100)));
	log.debug("zona 4:" + df.format(((hr[4] / hr[8]) * 100)));
	log.debug("zona 5:" + df.format(((hr[5] / hr[8]) * 100)));
	log.debug("zona 6:" + df.format(((hr[6] / hr[8]) * 100)));
    }

    /**
     * Completa el mapa segundo a segundo
     */
    private static List<HRBean> completarTiempos(List<HRBean> listado) {
	List<HRBean> list = new ArrayList<HRBean>();
	HRBean antBean = null;
	for (HRBean bean : listado) {
	    list.add(bean);
	    // log.debug(bean);
	    if (antBean != null) {
		long secf = (bean.getTime() - antBean.getTime()) / 1000;
		int difhr = bean.getHr() - antBean.getHr();
		// log.debug(secf+"/"+difhr);
		for (int i = 1; i < secf; i++) {
		    HRBean newBean = new HRBean();
		    newBean.setTime(antBean.getTime() + (i * 1000));
		    newBean.setHr(antBean.getHr() + (int) (difhr / secf));
		    // log.debug(newBean);
		    list.add(newBean);
		}
	    }
	    antBean = bean;
	}
	return list;
    }

}
