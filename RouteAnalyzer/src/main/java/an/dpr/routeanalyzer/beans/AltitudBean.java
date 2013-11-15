package an.dpr.routeanalyzer.beans;

import java.util.Date;

public class AltitudBean implements Comparable<AltitudBean> {

    private Long time;
    private Double altitud;
    private Double lat1;
    private Double lon1;
    private Double lat2;
    private Double lon2;

    public Double getAltitud() {
	return altitud;
    }

    public void setAltitud(Double altitud) {
	this.altitud = altitud;
    }

    public Double getMetrosAvanzados() {
	Double ret = 0.0;
	if (lat1 != null && lon1 != null){
//	    int R = 6371; // earth's mean radius in km
//	    double dLat = lat2 - lat1;
//	    double dLon = lon2 - lon1;
//	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1)
//		    * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//	    ret = R * c;
	    ret = haversine(lat1, lon1, lat2, lon2)*1000;
	}

	return ret;
    }
    
    public double haversine(
	        double lat1, double lng1, double lat2, double lng2) {
	    int r = 6371; // average radius of the earth in km
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lng2 - lng1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	       Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) 
	      * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = r * c;
	    return d;
	}

    @Override
    public int compareTo(AltitudBean o) {
	return this.time.compareTo(o.getTime());
    }

    public Double getLat1() {
	return lat1;
    }

    public void setLat1(Double lat1) {
	this.lat1 = lat1;
    }

    public Double getLon1() {
	return lon1;
    }

    public void setLon1(Double lon1) {
	this.lon1 = lon1;
    }

    public Double getLat2() {
	return lat2;
    }

    public void setLat2(Double lat2) {
	this.lat2 = lat2;
    }

    public Double getLon2() {
	return lon2;
    }

    public void setLon2(Double lon2) {
	this.lon2 = lon2;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
	return "AltitudBean [time=" + time + ", altitud=" + altitud + ", lat1="
		+ lat1 + ", lon1=" + lon1 + ", lat2=" + lat2 + ", lon2=" + lon2
		+ "]";
    }

}
