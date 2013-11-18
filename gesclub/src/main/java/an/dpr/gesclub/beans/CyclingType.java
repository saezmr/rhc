package an.dpr.gesclub.beans;

import java.util.ResourceBundle;


public enum CyclingType {

    ROAD((long)1), MTB((long)2);

    private Long id;
    private ResourceBundle bundle;

    private CyclingType(Long id) {
	this.id = id;
	bundle = ResourceBundle.getBundle("i18n");
    }
    
    public static CyclingType get(Long id) {
	CyclingType retValue = null;
	if (ROAD.getId() == id) {
	    retValue = ROAD;
	} else if (MTB.getId() == id) {
	    retValue = MTB;
	}
	return retValue;
    }

    public Long getId() {
	return id;
    }

    public String getI18n() {
	String ret = "";
	switch (this) {
	case MTB:
	    ret = bundle.getString("string.cycling_type_MTB");
	    break;
	case ROAD:
	    ret = bundle.getString("string.cycling_type_ROAD");
	    break;
	}
	return ret;
    }

}
