package an.dpr.enbizzi.beans;

import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.util.I18n;

public enum CyclingType {

    ROAD(1), MTB(2), BMX(3), INDOOR(4);

    private int id;

    private CyclingType(int id) {
	this.id = id;
    }
    
    public static CyclingType get(int id) {
	CyclingType retValue = null;
	if (ROAD.getId() == id) {
	    retValue = ROAD;
	} else if (MTB.getId() == id) {
	    retValue = MTB;
	} else if (BMX.getId() == id) {
	    retValue = BMX;
	} else if (INDOOR.getId() == id) {
	    retValue = INDOOR;
	}
	return retValue;
    }

    public int getId() {
	return id;
    }

    public String getI18n() {
	String ret = "";
	switch (this) {
	case BMX:
	    ret = I18n.getText("string.cycling_type_BMX");
	    break;
	case INDOOR:
	    ret = I18n.getText("string.cycling_type_INDOOR");
	    break;
	case MTB:
	    ret = I18n.getText("string.cycling_type_MTB");
	    break;
	case ROAD:
	    ret = I18n.getText("string.cycling_type_ROAD");
	    break;
	}
	return ret;
    }

    public an.dpr.enbizzi.domain.CyclingType getDomain() {
	an.dpr.enbizzi.domain.CyclingType ret = new an.dpr.enbizzi.domain.CyclingType();
	ret.setCyclingTypeId(this.getId());
	ret.setName(this.name());
	return ret;
    }
}
