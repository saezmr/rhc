package an.dpr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * Hello world!
 * 
 */
public class App {
	private static final String URL = "http://localhost:8080/CxfRestJDK5/rest/customerservice/";
	private HttpClient client;
	private static final Logger log = Logger.getLogger(App.class);
	
	public App(){
		client = new DefaultHttpClient();
	}
	
	public static void main(String[] args){
		App app = new App();
		try{
//			app.lanzarPost();
			app.lanzarPut();
		} catch(Exception e){
			log.error("", e);
		}
	}
	
	public void lanzarPut() throws Exception {
		log.debug("lanzarPut");
		String url = URL+"putcustomer/";
		HttpPut put = new HttpPut(url);
		
		log.debug("seteamos valores al put");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("id","123"));
		nameValuePairs.add(new BasicNameValuePair("name","Fulano"));
		put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		log.debug("lanzamos peticion");
		HttpResponse response = client.execute(put);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		log.debug("printamos la respuesta");
		String line = "";
		while ((line = rd.readLine()) != null) {
			log.debug(line);
		}
	}

	public void lanzarPutJson() throws Exception {
		log.debug("lanzarPut");
		String url = URL+"putcustomerjson/";
		HttpPut put = new HttpPut(url);
		
		log.debug("seteamos valores al put");
		JSONObject json = new JSONObject();
		json.put("id","123");
		json.put("name","Fulano");
		StringEntity se = new StringEntity( "JSON: " + json.toString());  
		se.setContentEncoding("UTF-8");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		put.setEntity(se);
		
		log.debug("lanzamos peticion");
		HttpResponse response = client.execute(put);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		log.debug("printamos la respuesta");
		String line = "";
		while ((line = rd.readLine()) != null) {
			log.debug(line);
		}
	}
	
	public void lanzarPost() throws Exception {
		log.debug("lanzandoPost");
		String url = URL+"postcustomer/";
		HttpPost post = new HttpPost(url);

		log.debug("seteamos valores al post");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    nameValuePairs.add(new BasicNameValuePair("id","15"));
	    nameValuePairs.add(new BasicNameValuePair("name","Fulano"));
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      
	    log.debug("lanzamos peticion");
		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		log.debug("printamos la respuesta");
		String line = "";
		while ((line = rd.readLine()) != null) {
			log.debug(line);
		}
	}

	public void lanzarGet() throws Exception {
		
	}

	public void lanzarDelete() throws Exception {
		
	}
}
