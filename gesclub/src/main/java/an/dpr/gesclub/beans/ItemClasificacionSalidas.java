package an.dpr.gesclub.beans;

import an.dpr.gesclub.domain.Socio;

/**
 * Item de clasificacion de salidas
 * 
 * @author rsaez
 * 
 */
public class ItemClasificacionSalidas implements Comparable {

    private Integer posicion;
    private Integer numSocio;
    private String socio;
    private Integer numeroSalidas;

    public String getSocio() {
	return socio;
    }

    public void setSocio(String socio) {
	this.socio = socio;
    }

    public Integer getPosicion() {
	return posicion;
    }

    public void setPosicion(Integer posicion) {
	this.posicion = posicion;
    }

    public Integer getNumSocio() {
	return numSocio;
    }

    public void setNumSocio(Integer socio) {
	numSocio = socio;
    }

    public Integer getNumeroSalidas() {
	return numeroSalidas;
    }

    public void setNumeroSalidas(Integer numeroSalidas) {
	this.numeroSalidas = numeroSalidas;
    }

    @Override
    public int compareTo(Object obj) {
	int ret = 0;
	if (!(obj instanceof ItemClasificacionSalidas)) {
	    ret = -1;
	} else {
	    ItemClasificacionSalidas item = (ItemClasificacionSalidas) obj;
	    ret = item.getNumeroSalidas().compareTo(numeroSalidas);
	    if (ret == 0){
		ret = numSocio.compareTo(item.getNumSocio());
	    }
	}
	return ret;
    }
    
    @Override
    public String toString() {
	return "ItemClasificacionSalidas [posicion=" + posicion + ", numSocio="
		+ numSocio + ", socio=" + socio + ", numeroSalidas="
		+ numeroSalidas + "]";
    }
}
