package an.dpr.enbizzi.services.filters;

import javax.ws.rs.core.Response;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.log4j.Logger;

/**
 * Filtro para autenticar via HTTP BASIC AUTHENTICATION 
 * @author rsaez
 */
public class AuthenticationHandler implements RequestHandler {
	
	private static final Logger log = Logger.getLogger(AuthenticationHandler.class);
	private static final String REQUEST_URI = "org.apache.cxf.request.uri";
	private static final String PUBLIC_URI = "/EnbizziAppWeb/rest/publicnoticiasws/";

    public Response handleRequest(Message m, ClassResourceInfo resourceClass) {
        AuthorizationPolicy policy = (AuthorizationPolicy)m.get(AuthorizationPolicy.class);
        if (!needAuthentication(m)){
        	return null;
        } else if (isAuthenticated(policy)) {
            // let request to continue
            return null;
        } else {
            // authentication failed, request the authetication, add the realm name if needed to the value of WWW-Authenticate 
            return Response.status(401).header("WWW-Authenticate", "Basic").build();
        }
    }

	private boolean needAuthentication(Message m) {
		boolean ret;
		String ruri = (String) m.get(REQUEST_URI);
		if (ruri.contains(PUBLIC_URI)){
			ret = false;
		} else {
			ret = true;
		}
		return ret;
	}

	private boolean isAuthenticated(AuthorizationPolicy policy) {
		boolean ret;
		if (policy != null){
	        String username = policy.getUserName();
	        String password = policy.getPassword();
			log.debug("username:"+username);
			log.debug("password:"+password);
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

}