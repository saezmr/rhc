package an.dpr.gesclub.services.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrediccionAemet {

	private String provincia;
	private String localidad;
	private String prediccion;
	private Date dia;
	private Map<AemetPeriodo, Integer> probPrecipitacion;
	private List<AemetEstadoCielo> estadoCielo;
	private List<AemetViento> viento;
	private Map<AemetPeriodo, Integer> rachaMax;
	private Integer maxTemperatura;
	private Integer minTemperatura;
	private Map<AemetHora, Integer> horaTemperatura;
	private Integer maxSenTermica;
	private Integer minSenTermica;
	private Map<AemetHora, Integer> horaSenTermica;

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
	 * @return the provincia
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/**
	 * @param provincia
	 *            the provincia to set
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the prediccion
	 */
	public String getPrediccion() {
		return prediccion;
	}

	/**
	 * @param prediccion
	 *            the prediccion to set
	 */
	public void setPrediccion(String prediccion) {
		this.prediccion = prediccion;
	}

	/**
	 * @return the dia
	 */
	public Date getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(Date dia) {
		this.dia = dia;
	}

	/**
	 * @return the probPrecipitacion
	 */
	public Map<AemetPeriodo, Integer> getProbPrecipitacion() {
		if (probPrecipitacion == null){
			probPrecipitacion = new HashMap<AemetPeriodo, Integer>();
		}
		return probPrecipitacion;
	}

	/**
	 * @param probPrecipitacion
	 *            the probPrecipitacion to set
	 */
	public void setProbPrecipitacion(
			Map<AemetPeriodo, Integer> probPrecipitacion) {
		this.probPrecipitacion = probPrecipitacion;
	}

	/**
	 * @return the estadoCielo
	 */
	public List<AemetEstadoCielo> getEstadoCielo() {
		if (estadoCielo == null){
			estadoCielo = new ArrayList<AemetEstadoCielo>();
		}
		return estadoCielo;
	}

	/**
	 * @return the viento
	 */
	public List<AemetViento> getViento() {
		if (viento == null){
			viento = new ArrayList<AemetViento>();
		}
		return viento;
	}

	/**
	 * @return the racha_max
	 */
	public Map<AemetPeriodo, Integer> getRachaMax() {
		if (rachaMax == null){
			rachaMax = new HashMap<AemetPeriodo, Integer>();
		}
		return rachaMax;
	}

	/**
	 * @return the maxTemperatura
	 */
	public Integer getMaxTemperatura() {
		return maxTemperatura;
	}

	/**
	 * @param maxTemperatura
	 *            the maxTemperatura to set
	 */
	public void setMaxTemperatura(Integer maxTemperatura) {
		this.maxTemperatura = maxTemperatura;
	}

	/**
	 * @return the minTemperatura
	 */
	public Integer getMinTemperatura() {
		return minTemperatura;
	}

	/**
	 * @param minTemperatura
	 *            the minTemperatura to set
	 */
	public void setMinTemperatura(Integer minTemperatura) {
		this.minTemperatura = minTemperatura;
	}

	/**
	 * @return the horaTemperatura
	 */
	public Map<AemetHora, Integer> getHoraTemperatura() {
		if (horaTemperatura == null){
			horaTemperatura = new HashMap<AemetHora, Integer>();
		}
		return horaTemperatura;
	}

	/**
	 * @param horaTemperatura
	 *            the horaTemperatura to set
	 */
	public void setHoraTemperatura(Map<AemetHora, Integer> horaTemperatura) {
		this.horaTemperatura = horaTemperatura;
	}

	/**
	 * @return the maxSenTermica
	 */
	public Integer getMaxSenTermica() {
		return maxSenTermica;
	}

	/**
	 * @param maxSenTermica
	 *            the maxSenTermica to set
	 */
	public void setMaxSenTermica(Integer maxSenTermica) {
		this.maxSenTermica = maxSenTermica;
	}

	/**
	 * @return the minSenTermica
	 */
	public Integer getMinSenTermica() {
		return minSenTermica;
	}

	/**
	 * @param minSenTermica
	 *            the minSenTermica to set
	 */
	public void setMinSenTermica(Integer minSenTermica) {
		this.minSenTermica = minSenTermica;
	}

	/**
	 * @return the horaSenTermica
	 */
	public Map<AemetHora, Integer> getHoraSenTermica() {
		return horaSenTermica;
	}

	/**
	 * @param horaSenTermica
	 *            the horaSenTermica to set
	 */
	public void setHoraSenTermica(Map<AemetHora, Integer> horaSenTermica) {
		this.horaSenTermica = horaSenTermica;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PrediccionAemet [provincia=" + provincia + ", prediccion="
				+ prediccion + ", dia=" + dia + ", probPrecipitacion="
				+ probPrecipitacion + ", estadoCielo=" + estadoCielo
				+ ", viento=" + viento + ", racha_max=" + rachaMax
				+ ", maxTemperatura=" + maxTemperatura + ", minTemperatura="
				+ minTemperatura + ", horaTemperatura=" + horaTemperatura
				+ ", maxSenTermica=" + maxSenTermica + ", minSenTermica="
				+ minSenTermica + ", horaSenTermica=" + horaSenTermica + "]";
	}
}
