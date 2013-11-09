package an.dpr.garmin.analize;

public enum GPXTags {
    gpx,
    trk,
    trkseg,
    trkpt, 
    ele, 
    time,
    extensions,
    gpxtpx_TrackPointExtension,
    gpxtpx_atemp, 
    gpxtpx_hr,
    gpxtpx_cad;
    
    public String getTag(){
	return this.name().replace("_", ":");
    }
    
    public static GPXTags getValueOf(String texto){
	GPXTags ret = null;
	if (texto != null){
	    ret = GPXTags.valueOf(texto.replace(":","_"));
	}
	return ret;
    }
}
