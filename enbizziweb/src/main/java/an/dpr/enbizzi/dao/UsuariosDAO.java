package an.dpr.enbizzi.dao;

import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.domain.Usuario;
import an.dpr.enbizzi.jpa.repository.UsuariosRepository;

/**
 * DAO de usuarios
 * @author rsaez
 *
 */
public class UsuariosDAO {

    @Autowired UsuariosRepository repo;
    
    public Usuario login(Usuario usuario){
	return repo.login(usuario.getLogin(), usuario.getPassword());
    }

    public Usuario getUsuario(Long idUsuario){
	return repo.findOne(idUsuario);
    }
}
