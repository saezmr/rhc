package an.dpr.routeanalyzer.bean;

public class FitActivityRecord implements Comparable<FitActivityRecord> {

    private Long timestamp;
    private Double distance;
    private Double altitude;
    private Integer heartRate;
    private Integer cadence;
    private Integer temperature;
    private Long positionLat;
    private Long positionLong;

    public Long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Long timestamp) {
	this.timestamp = timestamp;
    }

    public Integer getHeartRate() {
	return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
	this.heartRate = heartRate;
    }

    public Integer getCadence() {
	return cadence;
    }

    public void setCadence(Integer cadence) {
	this.cadence = cadence;
    }

    public Integer getTemperature() {
	return temperature;
    }

    public void setTemperature(Integer temperature) {
	this.temperature = temperature;
    }

    @Override
    public int compareTo(FitActivityRecord o) {
	return timestamp.compareTo(o.getTimestamp());
    }

    public Double getDistance() {
	return distance;
    }

    public void setDistance(Double distance) {
	this.distance = distance;
    }

    public Double getAltitude() {
	return altitude;
    }

    public void setAltitude(Double altitude) {
	this.altitude = altitude;
    }

    public Long getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(Long positionLat) {
        this.positionLat = positionLat;
    }

    public Long getPositionLong() {
        return positionLong;
    }

    public void setPositionLong(Long positionLong) {
        this.positionLong = positionLong;
    }

    @Override
    public String toString() {
	return "FitActivityRecord [timestamp=" + timestamp + ", distance=" + distance + ", altitude="
		+ altitude + ", heartRate=" + heartRate + ", cadence=" + cadence + ", temperature="
		+ temperature + ", positionLat=" + positionLat + ", positionLong=" + positionLong + "]";
    }
}
