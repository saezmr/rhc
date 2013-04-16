package an.dpr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import an.dpr.enbizzi.domain.Salida;
import an.dpr.enbizzi.util.BeanFactory;
import an.dpr.util.UtilFecha;

/**
 * Hello world!
 * 
 */
public class ClientEnbizziApp {
    private static final String URL = "http://localhost:8080/EnbizziAppWeb/rest/noticiasws/";
    private HttpClient client;
    private static final Logger log = Logger.getLogger(ClientEnbizziApp.class);

    public ClientEnbizziApp() {
	client = new DefaultHttpClient();
    }

    public static void main(String[] args) {
	ClientEnbizziApp app = new ClientEnbizziApp();
	try {
	    // app.inicializa();
//	     app.lanzarGet();
	    // app.lanzarPost();
	    // app.lanzarPut();
	     app.cargarCalendario();
	    // app.altaPuerto();
//	     app.updatePuerto();
//	    app.getSalidaJson();
	} catch (Exception e) {
	    log.error("", e);
	}
    }

    private void getSalidaJson() throws IllegalStateException, IOException,
	    ParseException {
	String url = "http://localhost:8080/EnbizziAppWeb/rest/calendarWS/salidajson/20130407";
	HttpGet get = new HttpGet(url);
	HttpResponse res = client.execute(get);
	BufferedReader br = new BufferedReader(new InputStreamReader(res
		.getEntity().getContent()));
	StringBuilder jsonText = new StringBuilder();
	Object line;
	do {
	    line = br.readLine();
	    jsonText.append(line);
	} while (line != null);
	Salida salida = BeanFactory.getSalidaFromJSON(jsonText.toString());
//	procesarSalidaJson(jsonText.toString());
	System.out.println(salida);
    }

    private void procesarSalidaJson(String jsonTxt) throws ParseException {
	JSONObject json = new JSONObject(jsonTxt);
	JSONObject salida = json.getJSONObject("Salida");
	if (salida != null) {
	    String fecha = salida.getString("date");
	    Date date = UtilFecha.parseFechaJson(fecha);
	    Double km = salida.getDouble("km");
	    String returnRoute = salida.getString("returnRoute");
	    String route = salida.getString("route");
	    String stop = salida.getString("stop");

	    JSONObject orache = salida.getJSONObject("oracheStart");
	    Integer maxTemp = orache.getInt("maxTemp");
	    Integer minTemp = orache.getInt("minTemp");
	    Integer mnyProbPre = orache.getInt("mnyProbPre");
	    Integer TardeProbPre = orache.getInt("tardeProbPre");
	    String mnyViento = orache.getString("mnyViento");
	    String tardeViento = orache.getString("tardeViento");

	    if (salida.get("puertos") instanceof JSONArray) {
		System.out.println("ARRAY!!!!");
	    } else {
		JSONObject puerto = salida.getJSONObject("puertos");
		Integer altitud = puerto.getInt("altitud");
		String descripcion = puerto.getString("descripcion");
		Double desnivelMedio = puerto.getDouble("desnivelMedio");
		Integer desnivelMetros = puerto.getInt("desnivelMetros");
		String fechaAlta = puerto.getString("fechaAlta");
		Date dateAlta = UtilFecha.parseFechaJson(fechaAlta);
		Long idPuerto = puerto.getLong("idPuerto");
		String imagen = puerto.getString("imagen");
		Double kmp = puerto.getDouble("km");
		String nombre = puerto.getString("nombre");
		String nombreExtendido = puerto.getString("nombreExtendido");
		Boolean revisado = puerto.getBoolean("revisado");
		
		JSONObject comarca = puerto.getJSONObject("comarca");
		Integer codProvincia = comarca.getInt("codProvincia");
		Long idComarca = comarca.getLong("idComarca");
		String nombrec = comarca.getString("nombre");
		String pais = comarca.getString("pais");
		String provincia = comarca.getString("provincia");
	    }

	    JSONObject type = salida.getJSONObject("type");
	    Long cyclingTypeId = type.getLong("cyclingTypeId");
	    String namet = type.getString("name");

	    JSONObject dif = salida.getJSONObject("difficulty");
	    Long difId = dif.getLong("difficultyId");
	    String named = dif.getString("name");
	    log.debug("yep");
	}
    }

    private void inicializa() throws ClientProtocolException, IOException {
	String url = "http://localhost:8080/EnbizziAppWeb/rest/adminWS/inicializa/";
	HttpGet req = new HttpGet(url);
	HttpResponse res = client.execute(req);
	pintarRespuesta(res);
    }

    /**
     * ejemplo para probar a encriptar a base64 y desenecriptar un fichero rula
     * 
     * @throws IOException
     */
    private void copyImgPuerto() throws IOException {
	String ruta = "C:\\buzon\\_old\\torrot_nacho2.jpg";
	String b64 = getImgPuerto();
	byte[] b = Base64.decodeBase64(b64);
	File f = new File(ruta);
	FileOutputStream fos = new FileOutputStream(f);
	IOUtils.write(b, fos);
	log.debug("ok, imagen copiada");
    }

    private void altaPuerto() throws IOException {
	String url = "http://localhost:8080/EnbizziAppWeb/rest/privatePuertosWS/puerto/";
	HttpPost req = new HttpPost(url);
	// autenticacion
	req.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("fulano:passFulano"
				.getBytes()));

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("nombre", "prueba1"));
	params.add(new BasicNameValuePair("nombreExtendido", "Col de prueba2"));
	params.add(new BasicNameValuePair("descripcion",
		"es un col para probar1"));
	params.add(new BasicNameValuePair("comarca", "Sobrarbe"));
	params.add(new BasicNameValuePair("km", "525"));
	params.add(new BasicNameValuePair("desnivelMetros", "500"));
	params.add(new BasicNameValuePair("desnivelMedio", "985"));
	params.add(new BasicNameValuePair("altitud", "2698"));
	params.add(new BasicNameValuePair("imagen", getImgPuerto()));
	params.add(new BasicNameValuePair("revisado", "false"));
	req.setEntity(new UrlEncodedFormEntity(params));
	log.debug("vamos a lanzar");
	HttpResponse response = client.execute(req);
	log.debug("recojemos respuesta");
	pintarRespuesta(response);
    }

    private void updatePuerto() throws IOException {
	String url = "http://localhost:8080/EnbizziAppWeb/rest/privatePuertosWS/puerto/";
	HttpPut req = new HttpPut(url);
	// autenticacion
	req.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("fulano:passFulano"
				.getBytes()));

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("nombre", "prueba2"));
	params.add(new BasicNameValuePair("nombreExtendido",
		"Col de prueba editado"));
	params.add(new BasicNameValuePair("descripcion",
		"es un col para probar editado"));
	params.add(new BasicNameValuePair("comarca", "Sobrarbe"));
	params.add(new BasicNameValuePair("km", "525"));
	params.add(new BasicNameValuePair("desnivelMetros", "500"));
	params.add(new BasicNameValuePair("desnivelMedio", "985"));
	params.add(new BasicNameValuePair("altitud", "2698"));
	params.add(new BasicNameValuePair("imagen", getImgPuerto()));
	params.add(new BasicNameValuePair("revisado", "true"));
	req.setEntity(new UrlEncodedFormEntity(params));
	log.debug("vamos a lanzar");
	HttpResponse response = client.execute(req);
	log.debug("recojemos respuesta");
	pintarRespuesta(response);
    }

    private String getImgPuerto() throws IOException {
	String ruta = "C:\\buzon\\_old\\torrot_nacho.jpg";
	File f = new File(ruta);
	FileInputStream fis = new FileInputStream(f);
	byte[] b = IOUtils.toByteArray(fis);
	return Base64.encodeBase64String(b);
    }

    private void pintarRespuesta(HttpResponse response)
	    throws IllegalStateException, IOException {
	BufferedReader rd = new BufferedReader(new InputStreamReader(response
		.getEntity().getContent()));
	log.debug("printamos la respuesta");
	String line = "";
	while ((line = rd.readLine()) != null) {
	    log.debug(line);
	}
    }

    private void cargarCalendario() throws Exception {
	String url = "http://enbizziweb-dprsoft.rhcloud.com";
	url =url+"/rest/private/calendarWS/admin/cargarCalendario/";
	// TODO incluir puertos!
	// String xml =
	// "<calendar><year>2012</year><club>enbizzi</club><version>1</version><events><event><date>01/01/2013 10:00</date><stop>MUEL</stop><route>MUEL</route><returnRoute>Crta.Valencia</returnRoute><difficulty></difficulty><km>54.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>05/01/2013 09:00</date><stop>ONTINAT</stop><route>ZUERA-ONTINAR</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>72.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>06/01/2013 10:00</date><stop>REMOLINOS</stop><route>ALAGON-REMOLINOS</route><returnRoute>Crta.Logro�o</returnRoute><difficulty></difficulty><km>66.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>07/01/2013 09:00</date><stop>MUEL</stop><route>ALTO MEZALOCHA</route><returnRoute>Crta.Valencia</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>12/01/2013 09:00</date><stop>SAN MATEO</stop><route>ZUERA-SAN MATEO</route><returnRoute>Crta.Huesca</returnRoute><difficulty></difficulty><km>60.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>13/01/2013 09:00</date><stop>LONGARES</stop><route>LONGARES</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>19/01/2013 09:00</date><stop>VALMADRID</stop><route>ALTO VALMADRID</route><returnRoute>Crta.Castellon</returnRoute><difficulty></difficulty><km>74.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>20/01/2013 09:00</date><stop>BARDALLUR</stop><route>ALAGON-BARDALLUR</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>26/01/2013 09:00</date><stop>FARLETE</stop><route>FARLETE</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>27/01/2013 09:00</date><stop>LECI�ENA</stop><route>SAN MATEO-LECI�ENA</route><returnRoute>Perdiguera</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>29/01/2013 09:00</date><stop>LA PAUL</stop><route>ZUERA-LA PAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>84.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>02/02/2013 09:00</date><stop>LECI�ENA</stop><route>ALTO ALCUBIERRE-LECI�ENA-</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>86.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>03/02/2013 09:00</date><stop>PRADILLA</stop><route>REMOLINOS-PRADILLA-LUCENI</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>09/02/2013 09:00</date><stop>ALFAMEN</stop><route>LONGARES-ALFAMEN</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>10/02/2013 09:00</date><stop>FUENTES</stop><route>CRUCE MEDIANA-FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>16/02/2013 09:00</date><stop>VILLANUEVA</stop><route>VILLANUEVA DE HUERVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>17/02/2013 09:00</date><stop>TAUSTE</stop><route>ALAGON-TAUSTE</route><returnRoute>Pradilla</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>23/02/2013 09:00</date><stop>LECI�ENA</stop><route>ZUERA-SAN MATEO-LECI�ENA</route><returnRoute>Perdiguera</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>24/02/2013 09:00</date><stop>MUEL</stop><route>LA MUELA - MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>80.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>02/03/2013 09:00</date><stop>CASTEJON</stop><route>CASTEJON DE VALDEJASA</route><returnRoute>Villanueva</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>03/03/2013 09:00</date><stop>CARI�ENA</stop><route>MUEL - CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>05/03/2013 09:00</date><stop>MONEGRILLO</stop><route>MONEGRILLO</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>09/03/2013 09:00</date><stop>EL TEMPLE</stop><route>ZUERA - EL TEMPLE</route><returnRoute>Ontinar</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>10/03/2013 09:00</date><stop>EPILA</stop><route>ALAGON-EPILA-MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>16/03/2013 09:00</date><stop>VILLANUEVA</stop><route>VILLANUEVA DE HUERVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>17/03/2013 09:00</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE-LECI�ENA</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>23/03/2013 09:00</date><stop>LA PAUL</stop><route>SAN JORGE-GURREA-LA PAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>24/03/2013 09:00</date><stop>ALFAMEN</stop><route>LONGARES-ALFAMEN</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>28/03/2013 09:00</date><stop>FARLETE</stop><route>FARLETE</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>29/03/2013 09:00</date><stop>FUENTES</stop><route>CRUCE MEDIANA-FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>30/03/2013 09:00</date><stop>CARI�ENA</stop><route>CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>31/03/2013 09:00</date><stop>LECI�ENA</stop><route>ALTO ALCUBIERRE-LECI�ENA</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>86.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>06/04/2013 08:30</date><stop>LA PAUL</stop><route>SAN JORGE-GURREA-LA PAUL</route><returnRoute>La Paul</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>07/04/2013 08:30</date><stop>EPILA</stop><route>LA MUELA-EPILA-ALAGON</route><returnRoute>Epila</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>13/04/2013 08:30</date><stop>SIERRA LUNA</stop><route>CASTEJON VALDEJASA-SIERRA LUNA</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>114.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>14/04/2013 08:30</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>20/04/2013 08:30</date><stop>CARI�ENA</stop><route>VILLANUEVA-TOSOS-CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>120.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>21/04/2013 08:30</date><stop>GALLUR</stop><route>ALAGON-TAUSTE-GALLUR</route><returnRoute>Luceni</returnRoute><difficulty></difficulty><km>105.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>23/04/2013 08:30</date><stop>CARI�ENA</stop><route>ALTO PANIZA-CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>116.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>27/04/2013 08:30</date><stop>LASPEDROSAS</stop><route>ZUERA - LAS PEDROSAS</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>106.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>28/04/2013 08:30</date><stop>ALFAMEN</stop><route>LA MUELA - ALFAMEN</route><returnRoute>Longares</returnRoute><difficulty></difficulty><km>105.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>01/05/2013 08:00</date><stop>TAUSTE</stop><route>CASTEJON DE VA�DEJASA-TAUSTE</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>120.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>04/05/2013 08:00</date><stop>VILLANUEVA</stop><route>LA PUEBLA-AZUARA-VILLANUEVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>130.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>05/05/2013 08:00</date><stop>EPILA</stop><route>ALAGON-POZUELO-EPILA-MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>128.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>10/05/2013 08:00</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE-TARDIENTA</route><returnRoute>Crta.Huesca</returnRoute><difficulty></difficulty><km>134.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>12/05/2013 08:00</date><stop>CARI�ENA</stop><route>ALTO PANIZA-CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>116.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>18/05/2013 08:00</date><stop>LA PAUL</stop><route>ZUERA-CRUCE PEDROSAS-LA PAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>96.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>19/05/2013 08:00</date><stop>ALMONACID</stop><route>ALFAMEN-ALMONACID SIERRA</route><returnRoute>Longares</returnRoute><difficulty></difficulty><km>118.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>25/05/2013 08:00</date><stop>MONEGRILLO</stop><route>MONEGRILLO</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>26/05/2013 08:00</date><stop>PRADILLA</stop><route>ALAGON-TAUSTE-PRADILLA</route><returnRoute>Luecni</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>01/06/2013 08:00</date><stop>CARI�ENA</stop><route>ALTO AGUARON-CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>116.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>02/06/2013 08:00</date><stop>LAS PEDROSAS</stop><route>CASTEJON VALDEJASA-LAS PEDROSAS</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>114.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>08/06/2013 08:00</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>09/06/2013 08:00</date><stop>LUNA</stop><route>LUNA-MONLORA</route><returnRoute>Las Pedrosas</returnRoute><difficulty></difficulty><km>132.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>15/06/2013 08:00</date><stop>VILLANUEVA</stop><route>VILLANUEVA-TOSOS-VILLANUEVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>110.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>16/06/2013 08:00</date><stop>ALFAMEN</stop><route>MUEL-CARI�ENA-ALFAMEN</route><returnRoute>Longares</returnRoute><difficulty></difficulty><km>102.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>22/06/2013 08:00</date><stop>LA PAUL</stop><route>SAN JORGE-GURRREA-LA PAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>23/06/2013 08:00</date><stop>EPILA</stop><route>ALAGON-EPILA-MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>29/06/2013 08:00</date><stop>MONEGRILLO</stop><route>MONEGRILLO</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>30/06/2013 08:00</date><stop>VILLANUEVA</stop><route>FUENDETODOS-VILLANUEVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>96.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>06/07/2013 08:00</date><stop>EL TEMPLE</stop><route>ZUERA EL TEMPLE</route><returnRoute>Ontinar</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>07/07/2013 08:00</date><stop>EPILA</stop><route>LA MUELA-EPILA.ALAGON</route><returnRoute>Crta.Logro�o</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>13/07/2013 08:00</date><stop>CARI�ENA</stop><route>ALTO PANIZA-CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>116.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>14/07/2013 08:00</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>20/07/2013 08:00</date><stop>ALFAMEN</stop><route>LONGARES-ALFAMEN</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>21/07/2013 08:00</date><stop>GALLUR</stop><route>ALAGON-TAUSTE-GALLUR</route><returnRoute>Luceni</returnRoute><difficulty></difficulty><km>105.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>27/07/2013 08:00</date><stop>CARI�ENA</stop><route>CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>28/07/2013 08:00</date><stop>LAS PEDROSAS</stop><route>ZUERA-LAS PEDROSAS</route><returnRoute>Crta.Huesca</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>03/08/2013 08:00</date><stop>MONEGRILLO</stop><route>MONEGRILLO</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>04/08/2013 08:00</date><stop>PRADILLA</stop><route>REMOLINOS-PRADILLA-LUCENI</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>10/08/2013 08:00</date><stop>LA PUEBLA</stop><route>LA PUEBLA DE ALBORTON</route><returnRoute>Valmadrid</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>11/08/2013 08:00</date><stop>EPILA</stop><route>LA MUELA-CALATORAO-EPILA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>110.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>15/08/2013 08:00</date><stop>LA PAUL</stop><route>SAN JORGE -GURREA-LAPAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>17/08/2013 08:00</date><stop>ALCUBIERRE</stop><route>ALCUBIERRE</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>18/08/2013 08:00</date><stop>VILLANUEVA</stop><route>VILLANUEVA DE HUERVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>24/08/2013 08:00</date><stop>ALFAMEN</stop><route>LONGARES-ALFAMEN</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>25/08/2013 08:00</date><stop>LECI�ENA</stop><route>ZUERA-SAN MATEO-LECI�ENA</route><returnRoute>Perdiguera</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>31/08/2013 08:00</date><stop>CARI�ENA</stop><route>CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>01/09/2013 08:30</date><stop>LA PAUL</stop><route>ZUERA-CRUCE L.PEDROSAS-LA PAUL</route><returnRoute>Crta.Huesca</returnRoute><difficulty></difficulty><km>96.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>07/09/2013 08:30</date><stop>LA PUEBLA</stop><route>LA PUEBLA DE ALBORTON</route><returnRoute>Valmadrid</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>08/09/2013 08:30</date><stop>MONEGRILLO</stop><route>MONEGRILLO</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>98.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>14/09/2013 08:30</date><stop>CARI�ENA</stop><route>MUEL-CARI�ENA</route><returnRoute>Longares</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>15/09/2013 08:30</date><stop>EL TEMPLE</stop><route>ZUERA-EL TEMPLE</route><returnRoute>Ontinar</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>21/09/2013 08:30</date><stop>FUENTES</stop><route>CRUCE MEDIANA - FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>22/09/2013 08:30</date><stop>VILLANUEVA</stop><route>VILLANUEVA DE HUERVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>28/09/2013 08:30</date><stop>LA PAUL</stop><route>SAN JORGE-GURREA-LA PAUL</route><returnRoute>Zuera</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>29/09/2013 08:30</date><stop>TAUSTE</stop><route>ALAGON - TAUSTE</route><returnRoute>Luceni</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>05/10/2013 09:00</date><stop>LECI�ENA</stop><route>ZUERA-SAN MATEO-LECI�ENA</route><returnRoute>Perdiguera</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>06/10/2013 09:00</date><stop>CARI�ENA</stop><route>CARI�ENA</route><returnRoute>Longares</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>12/10/2013 10:00</date><stop>MUEL</stop><route>ALTO MEZALOCHA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>13/10/2013 09:00</date><stop>FUENTES</stop><route>CRUCE MEDIANA- FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>19/10/2013 09:00</date><stop>LAPAUL</stop><route>SAN JORGE-GURREA-LA PAUL</route><returnRoute>Huesca</returnRoute><difficulty></difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>20/10/2013 09:00</date><stop>MUEL</stop><route>LA MUELA - MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>80.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>26/10/2013 09:00</date><stop>LECI�ENA</stop><route>ERMITA LECI�ENA - SAN MATEO</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>27/10/2013 09:00</date><stop>ALAMEN</stop><route>LONGARES-ALFAMEN</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>01/11/2013 09:00</date><stop>FARLETE</stop><route>FARLETE</route><returnRoute>Villamayor</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>02/11/2013 09:00</date><stop>VALMADRID</stop><route>ALTO DE VALMADRID</route><returnRoute>Valmadrid</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>03/11/2013 09:00</date><stop>VILLANUEVA</stop><route>VILLANUEVA DE HUERVA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>09/11/2013 09:00</date><stop>EL TEMPLE</stop><route>ZUERA - EL TEMPLE</route><returnRoute>Ontinar</returnRoute><difficulty></difficulty><km>92.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>10/11/2013 09:00</date><stop>LUCENI</stop><route>ALAGON - LUCENI</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>16/11/2013 09:00</date><stop>FUENTES</stop><route>CRUCE MEDIANA - FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>17/11/2013 09:00</date><stop>LONGARES</stop><route>LONGARES</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>23/11/2013 09:00</date><stop>LECI�ENA</stop><route>ALTO ALCUBIERRE - LECI�ENA</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>24/11/2013 09:00</date><stop>MUEL</stop><route>LA MUELA - MUEL</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>80.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>30/11/2013 09:00</date><stop>LA PAUL</stop><route>ZUERA - LA PAUL</route><returnRoute>La Paul</returnRoute><difficulty></difficulty><km>86.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>01/12/2013 09:00</date><stop>CARI�ENA</stop><route>CARI�ENA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>06/12/2013 09:00</date><stop>MUEL</stop><route>BODEGAS AYLES-</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>80.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>07/12/2013 09:00</date><stop>LECI�ENA</stop><route>ERMITA LECI�ENA-SAN MATEO</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>08/12/2013 09:00</date><stop>REMOLINOS</stop><route>ALAGON - REMOLINOS</route><returnRoute>Crta.Logro�o</returnRoute><difficulty></difficulty><km>72.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>09/12/2013 09:00</date><stop>FUENTES</stop><route>CRUCE MEDIANA - FUENTES</route><returnRoute>Mediana</returnRoute><difficulty></difficulty><km>82.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>14/12/2013 09:00</date><stop>ONTINAR</stop><route>ZUERA - ONTINAR</route><returnRoute>Crta.Huesca</returnRoute><difficulty></difficulty><km>72.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>15/12/2013 09:00</date><stop>LECI�ENA</stop><route>ALTO ALCUBIERRE - LECI�ENA</route><returnRoute>San Mateo</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>21/12/2013 09:00</date><stop>LONGARES</stop><route>LONGARES</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>78.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>22/12/2013 09:00</date><stop>EL TEMPLE</stop><route>ZUERA - EL TEMPLE</route><returnRoute>Ontinar</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>25/12/2013 10:00</date><stop>JAULIN</stop><route>JAULIN</route><returnRoute>Crta.Valencia</returnRoute><difficulty></difficulty><km>60.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>28/12/2013 09:00</date><stop>MUEL</stop><route>ALTO MEZALOCHA</route><returnRoute>Muel</returnRoute><difficulty></difficulty><km>76.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event><event><date>29/12/2013 09:00</date><stop>TAUSTE</stop><route>ALAGON - TAUSTE</route><returnRoute>Alagon</returnRoute><difficulty></difficulty><km>90.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop></event></events></calendar>";
	// TODO con puertos pero solo dos salidas para probar
	String xml = "<calendar><year>2012</year><club>enbizzi</club><version>1</version><events><event><date>13/04/2013 08:30</date><stop>LA PAUL</stop><route>SAN JORGE-GURREA-LA PAUL</route><returnRoute>La Paul</returnRoute><difficulty>EASY</difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop><puerto>prueba1</puerto></event><event><date>14/04/2013 08:30</date><stop>EPILA</stop><route>LA MUELA-EPILA-ALAGON</route><returnRoute>Epila</returnRoute><difficulty>HARD</difficulty><km>100.0</km><elevationGain></elevationGain><type>ROAD</type><aemetCodeStart>50297</aemetCodeStart><aemetCodeStop></aemetCodeStop><puerto>prueba1</puerto><puerto>prueba2</puerto></event></events></calendar>";
	HttpPost request = new HttpPost(url);
	request.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("riki:riki".getBytes()));
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

    public void lanzarPut() throws Exception {
	log.debug("lanzarPut");
	String url = URL + "noticias/";
	HttpPut put = new HttpPut(url);
	// autenticacion
	put.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("fulano:passFulano"
				.getBytes()));

	log.debug("seteamos valores al put");
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	nameValuePairs.add(new BasicNameValuePair("idNoticia", "123"));
	nameValuePairs.add(new BasicNameValuePair("entradilla",
		"entradilla de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("titulo",
		"titulo de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("cuerpo",
		"cuerpo de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("fechaPublicacion",
		"15/03/2013"));
	nameValuePairs.add(new BasicNameValuePair("publicada", "true"));
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

    public void lanzarPost() throws Exception {
	log.debug("lanzandoPost");
	String url = URL + "noticias/";
	HttpPost post = new HttpPost(url);
	// autenticacion
	post.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("mengano:lapass".getBytes()));

	log.debug("seteamos valores al post");
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	nameValuePairs.add(new BasicNameValuePair("entradilla",
		"entradilla de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("titulo",
		"titulo de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("cuerpo",
		"cuerpo de la noticia"));
	nameValuePairs.add(new BasicNameValuePair("fechaPublicacion",
		"15/03/2013"));
	nameValuePairs.add(new BasicNameValuePair("publicada", "true"));
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
	log.debug("lanzamos get noticia");
	HttpGet get = new HttpGet(
		"http://localhost:8080/EnbizziAppWeb/rest/calendarWS/salida/20130328");
	HttpResponse response = client.execute(get);
	pintarRespuesta(response);

	log.debug("lanzamos last puertos");
	get = new HttpGet(
		"http://localhost:8080/EnbizziAppWeb/rest/privatePuertosWS/lastPuertos/");

	get.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String("mengano:lapass".getBytes()));
	response = client.execute(get);
	pintarRespuesta(response);
    }

    public void lanzarDelete() throws Exception {

    }
}
