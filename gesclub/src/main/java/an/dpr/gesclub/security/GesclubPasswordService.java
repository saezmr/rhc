package an.dpr.gesclub.security;

import java.security.MessageDigest;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Md5Hash;

import sun.misc.BASE64Encoder;

/**
 * Clase para encryptado del password
 * 
 * @author rsaez
 * 
 */
public class GesclubPasswordService implements PasswordService {
    
    private MessageDigest msgDigest;

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
	boolean ret = false;
	if (submittedPlaintext != null && encrypted != null) {
	    String submittedEncrypted = encryptPassword(submittedPlaintext);
	    ret = submittedEncrypted.equals(encrypted);
	}
	return ret;
    }

    @Override
    public String encryptPassword(Object plaintextPassword)
	    throws IllegalArgumentException {
	String ret = null;
//	Md5Hash pass = new Md5Hash(plaintextPassword);
//	ret = pass.toString();
	ret = digest(plaintextPassword.toString());
	return ret;
    }
    
    public static void main(String[] args){
	Md5Hash pass = new Md5Hash("riki");
	System.out.println(pass.toString());
	System.out.println("62dd7e80fdfcb966cac9c849268c61d9");
    }
    
    private String digest(String msg) {
	msgDigest.reset();
        byte[] bytes = msg.getBytes();
        byte[] out = msgDigest.digest(bytes);
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(out);
    }

    public MessageDigest getMsgDigest() {
        return msgDigest;
    }

    public void setMsgDigest(MessageDigest msgDigest) {
        this.msgDigest = msgDigest;
    }
};