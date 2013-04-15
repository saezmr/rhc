package an.dpr.enbizzi.domain;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "puertos")
@XmlRootElement(name = "Puerto")
public class Puerto {

    private Long idPuerto;
    private String nombre;
    private String nombreExtendido;
    private String descripcion;
    private Comarca comarca;
    private Float km;
    private Integer desnivelMetros;
    private Float desnivelMedio;
    private Integer altitud;
    private byte[] imagen;
    private boolean revisado;
    private Date fechaAlta;

    /**
     * @return the idPuerto
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getIdPuerto() {
	return idPuerto;
    }

    /**
     * @param idPuerto
     *            the idPuerto to set
     */
    public void setIdPuerto(Long idPuerto) {
	this.idPuerto = idPuerto;
    }

    /**
     * @return the nombre
     */
    @Column(unique=true)
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
     * @return the descripcion
     */
    @Column
    public String getNombreExtendido() {
	return nombreExtendido;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setNombreExtendido(String nombreExtendido) {
	this.nombreExtendido = nombreExtendido;
    }

    /**
     * @return the km
     */
    @Column
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
    @Column
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
    @Column
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
    @Column
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
    @Lob
//    @Basic(fetch=FetchType.EAGER)
    @Type(type = "org.hibernate.type.PrimitiveByteArrayBlobType")
    @Column()
//    @Transient
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

    /**
     * @return the revisado
     */
    @Column
    public boolean isRevisado() {
        return revisado;
    }

    /**
     * @param revisado the revisado to set
     */
    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }
    /**
     * @return the fechaAlta
     */
    @Column
    public Date getFechaAlta() {
        return fechaAlta;
    }

    /**
     * @param fechaAlta the fechaAlta to set
     */
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    /**
     * @return the comarca
     */
    @ManyToOne
    public Comarca getComarca() {
        return comarca;
    }

    /**
     * @param comarca the comarca to set
     */
    public void setComarca(Comarca comarca) {
        this.comarca = comarca;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Puerto [idPuerto=" + idPuerto + ", nombre=" + nombre
		+ ", nombreExtendido=" + nombreExtendido + ", comarca=" + comarca
		+ ", km=" + km + ", desnivelMetros=" + desnivelMetros
		+ ", desnivelMedio=" + desnivelMedio + ", altitud=" + altitud
		+ ", imagen=" + Arrays.toString(imagen) + ", revisado="
		+ revisado + ", fechaAlta=" + fechaAlta + "]";
    }

    /**
     * @return the descripcion
     */
    @Column
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}


