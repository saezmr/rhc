package an.dpr.gesclub.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "socios")
public class Socio {

    private Long idSocio;
    private String nif;
    private String nombre;
    private String username;
    private String apellido1;
    private String apellido2;
    private Integer numSocio;
    private Date fechaAlta;
    private Date fechaBaja;
    private Date fechaNacimiento;
    private String email;
    private String direccion;
    private String telefono;
    private Club club;
    private Set<Salida> salidas;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Long idSocio) {
	this.idSocio = idSocio;
    }

    @Column @NotNull
    public String getNif() {
	return nif;
    }

    public void setNif(String nif) {
	this.nif = nif;
    }

    @Column @NotNull
    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    @Column
    public String getApellido1() {
	return apellido1;
    }

    public void setApellido1(String apellido1) {
	this.apellido1 = apellido1;
    }

    @Column
    public String getApellido2() {
	return apellido2;
    }

    public void setApellido2(String apellido2) {
	this.apellido2 = apellido2;
    }

    @Column @NotNull
    public Integer getNumSocio() {
	return numSocio;
    }

    public void setNumSocio(Integer numSocio) {
	this.numSocio = numSocio;
    }

    @Column @NotNull
    public Date getFechaAlta() {
	return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
    }

    @Column
    public Date getFechaBaja() {
	return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
	this.fechaBaja = fechaBaja;
    }

    @Column
    public Date getFechaNacimiento() {
	return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
	this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
	return "Socio [idSocio=" + idSocio + ", nif=" + nif + ", nombre="
		+ nombre + ", apellido1=" + apellido1 + ", apellido2="
		+ apellido2 + ", numSocio=" + numSocio + ", fechaAlta="
		+ fechaAlta + ", fechaBaja=" + fechaBaja + ", fechaNacimiento="
		+ fechaNacimiento + ", email=" + email + ", direccion="
		+ direccion + ", telefono=" + telefono + ", club=" + club + "]";
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Column
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Transient
    public String getNombreCompleto(){
	StringBuilder sb = new StringBuilder();
	sb.append(apellido1!=null ? apellido1 : "-")
	    .append(" ")
	    .append(apellido2!=null ? apellido2 : "-")
	    .append(", ")
	    .append(nombre != null ? nombre :"-");
	return sb.toString();
    }

    @ManyToOne
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="socios")
    public Set<Salida> getSalidas() {
	return salidas;
    }

    public void setSalidas(Set<Salida> salidas) {
	this.salidas = salidas;
    }

    @Column(unique=true)
    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }
    
    @Transient
    public int getNumeroSalidas(){
	int ret = 0;
	if (salidas != null){
	    ret = salidas.size();
	}
	return ret;
    }
    
    

}
