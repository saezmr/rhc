package an.dpr.routeanalyzer.reader;

public enum FitField {
	timestamp_s, distance_m, altitude_m, speed_m, heart_rate_bpm, 
	temperature_C, cadence_rpm, position_lat_semicircles, position_long_semicircles;
	public static String FIELD_SEPARATOR = "/";
	public static String RECORD_PREFIX = "record.";
}
