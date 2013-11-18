package an.dpr.gesclub.services.beans;

public enum CyclingType {

    ROAD((long)1), MTB((long)2), BMX((long)3), INDOOR((long)4);

    private Long id;

    private CyclingType(Long id) {
	this.id = id;
    }
    
    public static CyclingType get(Long id) {
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

    public Long getId() {
	return id;
    }

    public String getI18n() {
	return this.name();
	//TODO i18n
//	String ret = "";
//	switch (this) {
//	case BMX:
//	    ret = I18n.getText("string.cycling_type_BMX");
//	    break;
//	case INDOOR:
//	    ret = I18n.getText("string.cycling_type_INDOOR");
//	    break;
//	case MTB:
//	    ret = I18n.getText("string.cycling_type_MTB");
//	    break;
//	case ROAD:
//	    ret = I18n.getText("string.cycling_type_ROAD");
//	    break;
//	}
//	return ret;
    }

//    public an.dpr.enbizzi.domain.CyclingType getDomain() {
//	an.dpr.enbizzi.domain.CyclingType ret = new an.dpr.enbizzi.domain.CyclingType();
//	ret.setCyclingTypeId(this.getId());
//	ret.setName(this.name());
//	return ret;
//    }
}
