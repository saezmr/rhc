package an.dpr.gesclub.beans;

import java.util.ResourceBundle;

public enum TipoSalida {
    ROAD_CLUB, BTT_CLUB, ESPECIAL_CLUB, ROAD_MARCHA, BTT_MARCHA;
    private ResourceBundle bundle;


    private TipoSalida() {
	bundle = ResourceBundle.getBundle("i18n");
    }

    public String getI18n() {
	String ret = "";
	switch (this) {
	case ROAD_CLUB:
	    ret = bundle.getString("road.club");
	    break;
	case BTT_CLUB:
	    ret = bundle.getString("btt.club");
	    break;
	case ESPECIAL_CLUB:
	    ret = bundle.getString("especial.club");
	    break;
	case BTT_MARCHA:
	    ret = bundle.getString("btt.marcha");
	    break;
	case ROAD_MARCHA:
	    ret = bundle.getString("road.marcha");
	    break;
	}
	return ret;
    }
    
    public String toString(){
	return getI18n();
    }
}
