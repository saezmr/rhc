package an.dpr.enbizzi.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an.dpr.util.UtilFecha;

public class Orache {

    private String localidad;
    private String fecha;
    private String minTemp;
    private String maxTemp;
    private String mnyViento;
    private String tardeViento;
    private String mnyProbPre;
    private String tardeProbPre;

    public static Orache getOrache(PrediccionAemet pa) {
	Orache orache = new Orache();
//	orache.setLocalidad(pa.getLocalidad());
//	orache.setFecha(UtilFecha.formatFecha(pa.getDia()));
	orache.setMinTemp(String.valueOf(pa.getMinTemperatura()));
	orache.setMaxTemp(String.valueOf(pa.getMaxTemperatura()));
	orache.setVientos(pa.getViento());
	orache.setProbabilidadPrecipitacion(pa.getProbPrecipitacion());
	return orache;
    }

    private void setProbabilidadPrecipitacion(
	    Map<AemetPeriodo, Integer> probPrecipitacion) {
	if (probPrecipitacion.get(AemetPeriodo.p0612) != null) {
	    Integer mny = probPrecipitacion.get(AemetPeriodo.p0612);
	    Integer tarde = probPrecipitacion.get(AemetPeriodo.p1218);
	    setMnyProbPre(String.valueOf(mny));
	    setTardeProbPre(String.valueOf(tarde));
	} else if (probPrecipitacion.get(AemetPeriodo.p0012) != null) {
	    Integer mny = probPrecipitacion.get(AemetPeriodo.p0012);
	    Integer tarde = probPrecipitacion.get(AemetPeriodo.p1224);
	    setMnyProbPre(String.valueOf(mny));
	    setTardeProbPre(String.valueOf(tarde));
	} else {
	    Integer p0024 = probPrecipitacion.get(AemetPeriodo.p0024);
	    setMnyProbPre(String.valueOf(p0024));
	    setTardeProbPre(String.valueOf(p0024));
	}
    }

    private void setVientos(List<AemetViento> viento) {
	Map<AemetPeriodo, AemetViento> map = new HashMap<AemetPeriodo, AemetViento>();
	for (AemetViento v : viento) {
	    map.put(v.getPeriodo(), v);
	}
	if (map.get(AemetPeriodo.p0612) != null) {
	    this.setMnyViento(getVientoFormat(map.get(AemetPeriodo.p0612)));
	    this.setTardeViento(getVientoFormat(map.get(AemetPeriodo.p1218)));
	} else if (map.get(AemetPeriodo.p0012) != null) {
	    this.setMnyViento(getVientoFormat(map.get(AemetPeriodo.p0012)));
	    this.setTardeViento(getVientoFormat(map.get(AemetPeriodo.p1224)));
	} else {
	    this.setMnyViento(getVientoFormat(map.get(AemetPeriodo.p0024)));
	    this.setTardeViento(getVientoFormat(map.get(AemetPeriodo.p0024)));
	}
    }

    private static String getVientoFormat(AemetViento v) {
	StringBuilder sb = new StringBuilder();
	sb.append(v.getVelocidad()).append("km/h ").append(v.getDireccion());
	return sb.toString();
    }

    /**
     * @return the localidad
     */
    public String getLocalidad() {
	return localidad;
    }

    /**
     * @param localidad
     *            the localidad to set
     */
    public void setLocalidad(String localidad) {
	this.localidad = localidad;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
	return fecha;
    }

    /**
     * @param fecha
     *            the fecha to set
     */
    public void setFecha(String fecha) {
	this.fecha = fecha;
    }

    /**
     * @return the minTemp
     */
    public String getMinTemp() {
	return minTemp;
    }

    /**
     * @param minTemp
     *            the minTemp to set
     */
    public void setMinTemp(String minTemp) {
	this.minTemp = minTemp;
    }

    /**
     * @return the maxTemp
     */
    public String getMaxTemp() {
	return maxTemp;
    }

    /**
     * @param maxTemp
     *            the maxTemp to set
     */
    public void setMaxTemp(String maxTemp) {
	this.maxTemp = maxTemp;
    }

    /**
     * @return the mnyViento
     */
    public String getMnyViento() {
	return mnyViento;
    }

    /**
     * @param mnyViento
     *            the mnyViento to set
     */
    public void setMnyViento(String mnyViento) {
	this.mnyViento = mnyViento;
    }

    /**
     * @return the tardeViento
     */
    public String getTardeViento() {
	return tardeViento;
    }

    /**
     * @param tardeViento
     *            the tardeViento to set
     */
    public void setTardeViento(String tardeViento) {
	this.tardeViento = tardeViento;
    }

    /**
     * @return the mnyProbPre
     */
    public String getMnyProbPre() {
	return mnyProbPre;
    }

    /**
     * @param mnyProbPre
     *            the mnyProbPre to set
     */
    public void setMnyProbPre(String mnyProbPre) {
	this.mnyProbPre = mnyProbPre;
    }

    /**
     * @return the tardeProbPre
     */
    public String getTardeProbPre() {
	return tardeProbPre;
    }

    /**
     * @param tardeProbPre
     *            the tardeProbPre to set
     */
    public void setTardeProbPre(String tardeProbPre) {
	this.tardeProbPre = tardeProbPre;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Orache [localidad=" + localidad + ", fecha=" + fecha
		+ ", minTemp=" + minTemp + ", maxTemp=" + maxTemp
		+ ", mnyViento=" + mnyViento + ", tardeViento=" + tardeViento
		+ ", mnyProbPre=" + mnyProbPre + ", tardeProbPre="
		+ tardeProbPre + "]";
    }
}
