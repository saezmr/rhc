package an.dpr.routeanalyzer.reader;

public enum FitField {
    timestamp_s, distance_m, altitude_m, speed_m, heart_rate_bpm, temperature_C;
    public static String FIELD_SEPARATOR = "/";
    public static String RECORD_PREFIX = "record.";
}
