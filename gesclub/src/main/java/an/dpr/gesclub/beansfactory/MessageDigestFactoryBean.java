package an.dpr.gesclub.beansfactory;

import java.security.MessageDigest;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class MessageDigestFactoryBean implements FactoryBean<MessageDigest>, InitializingBean {

    private String defaultAlgorithm="MD5";
    private String algorithmName;
    private MessageDigest messageDigest;
    
    @Override
    public void afterPropertiesSet() throws Exception {
	if (algorithmName == null){
	    setAlgorithmName(defaultAlgorithm);
	}
	messageDigest = MessageDigest.getInstance(algorithmName);
    }

    @Override
    public MessageDigest getObject() throws Exception {
	return messageDigest;
    }

    @Override
    public Class<MessageDigest> getObjectType() {
	return MessageDigest.class;
    }

    @Override
    public boolean isSingleton() {
	return true;
    }

    public MessageDigest getMessageDigest() {
        return messageDigest;
    }

    public void setMessageDigest(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

}
