package an.dpr.gesclub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Roles de usuario
 * @author dsex5.trib
 *
 */
@Entity
@Table(name="user_roles")
public class UserRole {

    private Long roleId;
    private String roleName;
    private String username;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_id")
    public Long getRoleId() {
	return roleId;
    }

    public void setRoleId(Long roleId) {
	this.roleId = roleId;
    }

    @Column(name="role_name")
    public String getRoleName() {
	return roleName;
    }

    public void setRoleName(String roleName) {
	this.roleName = roleName;
    }

    @Column(name="username")
    public String getUsername() {
	return username;
    }

    public void setUsername(String userName) {
	this.username = userName;
    }

}
