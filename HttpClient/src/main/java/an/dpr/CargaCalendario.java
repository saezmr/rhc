package an.dpr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class CargaCalendario {

	private HttpClient client;
	private static final Logger log = Logger.getLogger(CargaCalendario.class);

	public CargaCalendario() {
		client = new DefaultHttpClient();
	}

	public static void main(String[] args) {
		try {
			CargaCalendario cc = new CargaCalendario();
			cc.cargarCalendario();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	private void cargarCalendario() throws Exception {
		String url = "http://enbizziweb-dprsoft.rhcloud.com";
		url = url + "/rest/private/calendarWS/admin/cargarCalendario/";
		String xml = leerXml();
		String user = "riki";
		String pass = "riki";
		HttpPost request = new HttpPost(url);
		request.setHeader(
				"Authorization",
				"Basic "
						+ Base64.encodeBase64String((user + ":" + pass)
								.getBytes()));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("xmlCalendar", xml));
		request.setEntity(new UrlEncodedFormEntity(params));
		log.debug("lanzamos peticion");
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		log.debug("printamos la respuesta");
		String line = "";
		while ((line = rd.readLine()) != null) {
			log.debug(line);
		}
	}
	
	private String leerXml() throws IOException{
		String ruta = "/home/rsaez/workspace/github/rhc/HttpClient/src/main/resources/calendario2013.xml";
		BufferedReader bf = new BufferedReader(new FileReader(ruta));
		StringBuilder xml = new StringBuilder();
		String line = null;
		while((line = bf.readLine())!=null){
			xml.append(line);
		}
		return xml.toString();
	}
}
