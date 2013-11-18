package an.dpr.gesclub.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

    private Long id;
    private String username;
    private Club club;
    private Boolean esSocio = true;
    private String password;
    

    @Id @Column(name="userid")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Column(nullable=false, name="username")
    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @ManyToOne
    public Club getClub() {
	return club;
    }

    public void setClub(Club club) {
	this.club = club;
    }

    @Column(nullable=false)
    public Boolean getEsSocio() {
	return esSocio;
    }
    
    public void setEsSocio(Boolean esSocio) {
	this.esSocio = esSocio;
    }
    
    @Override
    public String toString() {
	return "User [id=" + id + ", username=" + username + ", club=" + club
		+ ", esSocio=" + esSocio + "]";
    }

    @Column(nullable=false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

