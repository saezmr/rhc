package an.dpr.routeanalyzer.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FitAltimetryPoint implements AltimetryPoint {

	private static final long serialVersionUID = 1L;
	private Date date;
	private Double altitud;
	private Double metros;

	@Override
	public int compareTo(AltimetryPoint o) {
		return date.compareTo(o.getDate());
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Double getAltitud() {
		return altitud;
	}

	@Override
	public Double getMetros() {
		return metros;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}

	public void setMetros(Double metros) {
		this.metros = metros;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return "FitAltimetryPoint [date=" + sdf.format(date) + ", altitud=" + altitud + ", metros=" + metros + "]";
	}

}
