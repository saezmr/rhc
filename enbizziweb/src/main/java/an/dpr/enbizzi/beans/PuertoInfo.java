package an.dpr.enbizzi.beans;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import an.dpr.enbizzi.domain.Puerto;

/**
 * Informacion para el exterior del entity Puerto
 * 
 * @author rsaez
 * 
 */
@XmlRootElement(name = "Puerto")
public class PuertoInfo {

    private static final Logger log = Logger.getLogger(PuertoInfo.class);
    
    private String nombre;
    private String nombreExtendido;
    private String descripcion;
    private String comarca;
    private Float km;
    private Integer desnivelMetros;
    private Float desnivelMedio;
    private Integer altitud;
    private byte[] imagen;
    

    /**
     * Rellena un objeto PuertoInfo con la informacion del entity Puerto
     * @param puerto
     * @return
     */
    public static PuertoInfo getBean(Puerto puerto) {
	PuertoInfo info = null;
	if (puerto != null) {
	    log.debug("creamos PuertoInfo con "+puerto);
	    info = new PuertoInfo();
	    info.setAltitud(puerto.getAltitud());
	    info.setComarca(puerto.getComarca() != null
		    ? puerto.getComarca().getNombre()
	            : null);
	    info.setDescripcion(puerto.getDescripcion());
	    info.setDesnivelMedio(puerto.getDesnivelMedio());
	    info.setDesnivelMetros(puerto.getDesnivelMetros());
	    info.setImagen(puerto.getImagen());
	    info.setKm(puerto.getKm());
	    info.setNombre(puerto.getNombre());
	    info.setNombreExtendido(puerto.getNombreExtendido());
	}
	log.debug("return: "+info);
	return info;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    /**
     * @return the nombreExtendido
     */
    public String getNombreExtendido() {
	return nombreExtendido;
    }

    /**
     * @param nombreExtendido
     *            the nombreExtendido to set
     */
    public void setNombreExtendido(String nombreExtendido) {
	this.nombreExtendido = nombreExtendido;
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

    /**
     * @return the comarca
     */
    public String getComarca() {
	return comarca;
    }

    /**
     * @param comarca
     *            the comarca to set
     */
    public void setComarca(String comarca) {
	this.comarca = comarca;
    }

    /**
     * @return the km
     */
    public Float getKm() {
	return km;
    }

    /**
     * @param km
     *            the km to set
     */
    public void setKm(Float km) {
	this.km = km;
    }

    /**
     * @return the desnivelMetros
     */
    public Integer getDesnivelMetros() {
	return desnivelMetros;
    }

    /**
     * @param desnivelMetros
     *            the desnivelMetros to set
     */
    public void setDesnivelMetros(Integer desnivelMetros) {
	this.desnivelMetros = desnivelMetros;
    }

    /**
     * @return the desnivelMedio
     */
    public Float getDesnivelMedio() {
	return desnivelMedio;
    }

    /**
     * @param desnivelMedio
     *            the desnivelMedio to set
     */
    public void setDesnivelMedio(Float desnivelMedio) {
	this.desnivelMedio = desnivelMedio;
    }

    /**
     * @return the altitud
     */
    public Integer getAltitud() {
	return altitud;
    }

    /**
     * @param altitud
     *            the altitud to set
     */
    public void setAltitud(Integer altitud) {
	this.altitud = altitud;
    }

    /**
     * @return the imagen
     */
    public byte[] getImagen() {
	return imagen;
    }

    /**
     * @param imagen
     *            the imagen to set
     */
    public void setImagen(byte[] imagen) {
	this.imagen = imagen;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "PuertoInfo [nombre=" + nombre + ", nombreExtendido="
		+ nombreExtendido + ", descripcion=" + descripcion
		+ ", comarca=" + comarca + ", km=" + km + ", desnivelMetros="
		+ desnivelMetros + ", desnivelMedio=" + desnivelMedio
		+ ", altitud=" + altitud + ", imagen="
		+ Arrays.toString(imagen) + "]";
    }

}
