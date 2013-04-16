package an.dpr.enbizzi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Usuario de partes privadas de la app
 * @author rsaez
 *
 */
@Entity
@Table(name="usuarios")
public class Usuario {

    private Long idUsuario;
    private String login;
    private String password;
    private String rol;

    /**
     * @return the idUsuario
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getIdUsuario() {
	return idUsuario;
    }

    /**
     * @param idUsuario
     *            the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
	this.idUsuario = idUsuario;
    }

    /**
     * @return the login
     */
    @Column
    public String getLogin() {
	return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * password encriptado con md5
     * @return the password
     */
    @Column
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the rol
     */
    @Column
    public String getRol() {
	return rol;
    }

    /**
     * @param rol
     *            the rol to set
     */
    public void setRol(String rol) {
	this.rol = rol;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Usuario [idUsuario=" + idUsuario + ", login=" + login
		+ ", password=" + password + ", rol=" + rol + "]";
    }
}
