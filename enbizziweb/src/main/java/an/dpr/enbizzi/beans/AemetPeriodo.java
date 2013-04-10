package an.dpr.enbizzi.beans;

public enum AemetPeriodo {
    p0024, p0012, p1224, p0006, p0612, p1218, p1824;

    public static AemetPeriodo getPeriodo(String periodo) {
	AemetPeriodo ret = null;
	if (periodo == null){
	    ret = p0024;
	} else {
	    for (AemetPeriodo ap : AemetPeriodo.values()) {
		if (ap.aCadena().equals(periodo)) {
		    ret = ap;
		    break;
		}
	    }
	}
	return ret;
    }

    private String aCadena() {
	StringBuilder retValue = new StringBuilder();
	retValue.append(name().substring(1, 3)).append("-")
		.append(name().substring(3));
	return retValue.toString();
    }
}
