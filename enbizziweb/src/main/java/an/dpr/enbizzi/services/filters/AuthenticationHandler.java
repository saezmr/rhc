package an.dpr.enbizzi.services.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.beans.Rol;
import an.dpr.enbizzi.dao.UsuariosDAO;
import an.dpr.enbizzi.domain.Usuario;

/**
 * Filtro para autenticar via HTTP BASIC AUTHENTICATION
 * 
 * @author rsaez
 */
public class AuthenticationHandler implements RequestHandler {

    private static final Logger log = Logger
	    .getLogger(AuthenticationHandler.class);
    private static final String REQUEST_URI = "org.apache.cxf.request.uri";
    private static final String PRIVATE_URI = ".*private.*WS.*";
    private static final String ADMIN_URI = ".*admin.*";
    
    @Autowired UsuariosDAO usuarioDao;

    public Response handleRequest(Message m, ClassResourceInfo resourceClass) {
	AuthorizationPolicy policy = (AuthorizationPolicy) m
		.get(AuthorizationPolicy.class);
	Response ret = null;
	Usuario user;
	if (needAuthentication(m)){
	    user = userAuthenticate(policy);
	    if (user == null 
		    || !tienePermisos(user, m)){
		// authentication failed, request the authetication, add the realm
		// name if needed to the value of WWW-Authenticate
		ret =  Response.status(401).header("WWW-Authenticate", "Basic")
			.build();
	    } 
	}
	return ret;
    }
    
    private boolean tienePermisos(Usuario user, Message msg) {
	boolean tiene = false;
	String ruri = (String) msg.get(REQUEST_URI);
	Rol rolSolicitado = getRol(ruri);
	Rol rolUser = getRol(user);
	if (rolUser != null && rolUser.compareTo(rolSolicitado) >= 0){
	    tiene = true;
	}
	log.debug(user+" permisos? "+tiene+" para rol "+rolSolicitado);
	return tiene;
    }

    private Rol getRol(Usuario user) {
	return Rol.valueOf(user.getRol());
    }

    private Rol getRol(String ruri) {
	Rol rol = null;
	Pattern p = Pattern.compile(ADMIN_URI);
	Matcher matcher = p.matcher(ruri);
	if (matcher.matches()) {
	    rol = Rol.ADMIN;
	} else {
	    rol = Rol.USER;
	}
	return rol;
    }

    private boolean needAuthentication(Message msg) {
	String ruri = (String) msg.get(REQUEST_URI);
	return needAuthentication(ruri);
    }
    
    private boolean needAuthentication(String ruri){
	boolean ret;
	Pattern p = Pattern.compile(PRIVATE_URI);
	Matcher matcher = p.matcher(ruri);
	if (matcher.matches()) {
	    ret = true;
	} else {
	    ret = false;
	}
	return ret;
    }

    /**
     * 
     * @param policy
     * @return usuario si se autentica, null si no
     */
    private Usuario userAuthenticate(AuthorizationPolicy policy) {
	Usuario retUser = null;
	if (policy != null) {
	    Usuario usuario = new Usuario();
	    usuario.setLogin(policy.getUserName());
	    usuario.setPassword(policy.getPassword());
	    retUser = usuarioDao.login(usuario);
	    log.debug(policy.getUserName()+" intenta logearse con resultado:"+null);
	}
	return retUser;
    }

}