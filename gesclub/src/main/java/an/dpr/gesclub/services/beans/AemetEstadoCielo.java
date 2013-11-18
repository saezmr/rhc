package an.dpr.gesclub.services.beans;

public class AemetEstadoCielo {

	private AemetPeriodo periodo;
	private String code;
	private String descripcion;

	/**
	 * @return the periodo
	 */
	public AemetPeriodo getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo
	 *            the periodo to set
	 */
	public void setPeriodo(AemetPeriodo periodo) {
		this.periodo = periodo;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AemetEstadoCielo [periodo=" + periodo + ", code=" + code
				+ ", descripcion=" + descripcion + "]";
	}
}
