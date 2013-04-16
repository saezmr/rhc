package an.dpr.enbizzi.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import an.dpr.enbizzi.domain.Usuario;

/**
 * Repository para usuarios
 * @author rsaez
 */
public interface UsuariosRepository  extends CrudRepository<Usuario, Long>{
    
    @Query("FROM Usuario u WHERE u.login =:login AND u.password = :pass")
    public Usuario login(
	    @Param("login") String login, 
	    @Param("pass") String pass);

}
