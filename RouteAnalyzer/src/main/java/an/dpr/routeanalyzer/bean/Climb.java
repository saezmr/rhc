package an.dpr.routeanalyzer.bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Climb implements Comparable<Climb>{

    private Double kmIni;
    private AltimetryPoint initPoint;
    private AltimetryPoint finishPoint;
    private Double metros;

    public AltimetryPoint getInitPoint() {
	return initPoint;
    }

    public void setInitPoint(AltimetryPoint initPoint) {
	this.initPoint = initPoint;
    }

    public AltimetryPoint getFinishPoint() {
	return finishPoint;
    }

    public void setFinishPoint(AltimetryPoint finishPoint) {
	this.finishPoint = finishPoint;
    }

    public Double getMetros() {
	return metros;
    }

    public void setMetros(Double metros) {
	this.metros = metros;
    }

    public Double getAltitud() {
	Double ret = 0.0;
	if (initPoint != null && finishPoint != null){
	    ret = finishPoint.getAltitud()-initPoint.getAltitud();
	}
	return ret;
    }

    public Double getDesnivel(){
	if (getAltitud() > 0){
	    return (getAltitud()/metros)*100;
	} else {
	    return 0.0;
	}
	
    }

    @Override
    public String toString() {
	NumberFormat nf = new DecimalFormat("#####.00");
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	return "Climb ["
		+ "altIni: " + nf.format(initPoint.getAltitud())
		+ ", altFin: " + nf.format(finishPoint.getAltitud()) 
		+ ", dateIni: " + df.format(initPoint.getDate())
		+ ", dateFin: " + df.format(finishPoint.getDate()) 
		+ ", metros: " + nf.format(metros) 
		+ ", altitud: " + nf.format(getAltitud()) 
		+ ", desnivel: "+ nf.format(getDesnivel()) + "]";
    }

    @Override
    public int compareTo(Climb o) {
	return metros.compareTo(o.metros);
    }

    public Double getKmIni() {
        return kmIni;
    }

    public void setKmIni(Double kmIni) {
        this.kmIni = kmIni;
    }
}
