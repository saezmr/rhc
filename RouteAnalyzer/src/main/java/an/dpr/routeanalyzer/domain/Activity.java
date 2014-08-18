package an.dpr.routeanalyzer.domain;

import java.util.Date;

/**
 * 
 * @author rsaez
 * 
 */
// @Entity
public class Activity {

    private Long id;
    private Date fecha;
    private Date fechaFin;
    private Integer minutosMov;
    private String nombre;
    private Double km;
    private Integer heartRateAvg;
    // private Tipo tipo;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getFecha() {
	return fecha;
    }

    public void setFecha(Date fecha) {
	this.fecha = fecha;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Integer getHeartRateAvg() {
        return heartRateAvg;
    }

    public void setHeartRateAvg(Integer heartRateAvg) {
        this.heartRateAvg = heartRateAvg;
    }

    @Override
    public String toString() {
	return "Activity [id=" + id + ", fecha=" + fecha + ", fechaFin=" + fechaFin + ", minutosMov="
		+ minutosMov + ", nombre=" + nombre + ", km=" + km + ", heartRateAvg=" + heartRateAvg + "]";
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getMinutosMov() {
        return minutosMov;
    }

    public void setMinutosMov(Integer minutosMov) {
        this.minutosMov = minutosMov;
    }
    
}
