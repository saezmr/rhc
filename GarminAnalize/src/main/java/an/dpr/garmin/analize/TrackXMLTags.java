package an.dpr.garmin.analize;

public enum TrackXMLTags {
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
    
    public static TrackXMLTags getValueOf(String texto){
	TrackXMLTags ret = null;
	if (texto != null){
	    ret = TrackXMLTags.valueOf(texto.replace(":","_"));
	}
	return ret;
    }
}
