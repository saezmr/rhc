package an.dpr.routeanalyzer.reader;

import java.util.ArrayList;
import java.util.List;

import an.dpr.routeanalyzer.bean.HRBean;
import an.dpr.routeanalyzer.bean.HRZones;
import static an.dpr.routeanalyzer.Configuracion.*;

public class TrackInfoUtil {

    /**
     * Completa el mapa segundo a segundo
     */
    public static List<HRBean> completarTiempos(List<HRBean> listado) {
	List<HRBean> list = new ArrayList<HRBean>();
	HRBean antBean = null;
	for (HRBean bean : listado) {
	    if (bean.getTime() == null || bean.getHr() == null) {
		continue;
	    }
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

    public static HRZones getHRZones(List<HRBean> listado) {
	HRZones hrzones = new HRZones();
	double[] hr = new double[10];
	hr[8] = listado.size();
	for (HRBean bean : listado) {
	    hr[7] += bean.getHr();
	    if (bean.getHr() < getConfiguracion().getHrZona0()) {
		hr[0]++;
	    } else if (bean.getHr() < getConfiguracion().getHrZona1()) {
		hr[1]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < getConfiguracion().getHrZona2()) {
		hr[2]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < getConfiguracion().getHrZona3()) {
		hr[3]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < getConfiguracion().getHrZona4()) {
		hr[4]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < getConfiguracion().getHrZona5()) {
		hr[5]++;
		hr[9] += bean.getHr();
	    } else {
		hr[6]++;
		hr[9] += bean.getHr();
	    }
	}
	hrzones.setMedia(hr[7] / hr[8]);
	hrzones.setMedia16((hr[9] / (hr[8] - hr[0])));
	hrzones.setZona0((hr[0] / hr[8]) * 100);
	hrzones.setZona1((hr[1] / hr[8]) * 100);
	hrzones.setZona2((hr[2] / hr[8]) * 100);
	hrzones.setZona3((hr[3] / hr[8]) * 100);
	hrzones.setZona4((hr[4] / hr[8]) * 100);
	hrzones.setZona5((hr[5] / hr[8]) * 100);
	hrzones.setZona6((hr[6] / hr[8]) * 100);
	return hrzones;
    }
}
