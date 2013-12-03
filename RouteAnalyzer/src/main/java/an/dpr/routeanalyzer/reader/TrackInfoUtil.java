package an.dpr.routeanalyzer.reader;

import java.util.ArrayList;
import java.util.List;

import an.dpr.routeanalyzer.bean.HRBean;

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
}
