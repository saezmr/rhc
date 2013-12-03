package an.dpr.routeanalyzer.bean;

public class Rampa {

    private Double mIni;
    private Double mFin;
    private Double pendiente;
    private Double metros;
    private Double altitudIni;
    private Double altitudFin;

    public Double getmIni() {
	return mIni;
    }

    public void setmIni(Double mIni) {
	this.mIni = mIni;
    }

    public Double getmFin() {
	return mFin;
    }

    public void setmFin(Double mFin) {
	this.mFin = mFin;
    }

    public Double getPendiente() {
	return pendiente;
    }

    public void setPendiente(Double pendiente) {
	this.pendiente = pendiente;
    }

    @Override
    public String toString() {
	return "Rampa [mIni=" + mIni + ", mFin=" + mFin + ", pendiente="
		+ pendiente + ", metros=" + metros + ", altitudIni="
		+ altitudIni + ", altitudFin=" + altitudFin + "]";
    }

    public Double getMetros() {
	return metros;
    }

    public void setMetros(Double metros) {
	this.metros = metros;
    }

    public Double getAltitudIni() {
	return altitudIni;
    }

    public void setAltitudIni(Double altitudIni) {
	this.altitudIni = altitudIni;
    }

    public Double getAltitudFin() {
	return altitudFin;
    }

    public void setAltitudFin(Double altitudFin) {
	this.altitudFin = altitudFin;
    }

}
