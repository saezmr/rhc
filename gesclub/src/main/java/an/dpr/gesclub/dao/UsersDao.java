package an.dpr.gesclub.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.domain.Club;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.domain.UserRole;
import an.dpr.gesclub.jpa.repository.SociosRepository;
import an.dpr.gesclub.jpa.repository.UserRoleRepository;
import an.dpr.gesclub.jpa.repository.UsersRepository;

public class UsersDao {

    private static final Logger log = Logger.getLogger(UsersDao.class);
    @Autowired UsersRepository userRepo;
    @Autowired UserRoleRepository roleRepo;

    public UsersDao() {

    }

    public User getByUsername(String username) {
	User ret = null;
	try{
	    ret = userRepo.findByUsername(username);
	} catch(Throwable t){
	    log.error("Error obteniendo usuario por nombre", t);
	}
	return ret;
    }


    public User save(User user) {
	User ret;
	log.debug("save user:"+user);
	try {
	    ret = userRepo.save(user);
	} catch (Exception e) {
	    ret = null;
	    log.error("Error guardando Usuario" + user, e);
	}
	return ret;
    }

    public UserRole saveRole(UserRole ur) {
	UserRole ret;
	log.debug("save user:"+ur);
	try {
	    ret = roleRepo.save(ur);
	} catch (Exception e) {
	    ret = null;
	    log.error("Error guardando Usuario" + ur, e);
	}
	return ret;
    }

}
