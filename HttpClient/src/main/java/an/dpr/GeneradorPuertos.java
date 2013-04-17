package an.dpr;

import java.io.BufferedReader;
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

import an.dpr.enbizzi.domain.Puerto;

public class GeneradorPuertos {

    private HttpClient client;
    private static final Logger log = Logger.getLogger(GeneradorPuertos.class);
    private static final String URL_ALTA = "http://localhost:8080/EnbizziAppWeb/rest/privatePuertosWS/puerto/";

    public static void main(String[] args){
	try {
	    System.out.println(GeneradorPuertos.generar());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private GeneradorPuertos() {}

    public static boolean generar() throws IOException {
	boolean ret = false;
	GeneradorPuertos gp = new GeneradorPuertos();
	List<Puerto> list = getListPuertos();
	StringBuilder result = new StringBuilder();
	for(Puerto p : list){
	   result.append(gp.altaPuerto(p)); 
	}
	if (result.length()>0){
	    ret = true;
	}
	return ret;
    }

    private String altaPuerto(Puerto puerto) throws IOException {
	client = new DefaultHttpClient();
	HttpPost req = new HttpPost(URL_ALTA);
	// autenticacion
	String user = "user";
	String pass = "pass";
	req.setHeader(
		"Authorization",
		"Basic "
			+ Base64.encodeBase64String((user + ":" + pass)
				.getBytes()));

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("nombre", puerto.getNombre()));
	params.add(new BasicNameValuePair("nombreExtendido", 
		puerto.getNombreExtendido()));
	params.add(new BasicNameValuePair("descripcion", 
		puerto.getDescripcion()));
	params.add(new BasicNameValuePair("comarca", 
		puerto.getComarca()!= null 
			? puerto.getComarca().getNombre()
			: null 
	));
	params.add(new BasicNameValuePair("km",
		puerto.getKm()!=null
			? String.valueOf((int) (puerto.getKm() * 100))
			: null
		));
	params.add(new BasicNameValuePair("desnivelMetros",
		puerto.getDesnivelMetros() != null
		  ? String.valueOf(puerto.getDesnivelMetros())
		  : null ));
	params.add(new BasicNameValuePair("desnivelMedio",
		puerto.getDesnivelMedio()!=null
			? String.valueOf((int) (puerto.getDesnivelMedio() * 100))
			: null
			));
	params.add(new BasicNameValuePair("altitud",
		puerto.getAltitud()!=null
			? puerto.getAltitud().toString()
			: null
		));
	params.add(new BasicNameValuePair("imagen",
		puerto.getImagen()!=null
			? Base64.encodeBase64String(puerto.getImagen())
			: null
			));
	params.add(new BasicNameValuePair("revisado", "true"));
	req.setEntity(new UrlEncodedFormEntity(params));
	HttpResponse response = client.execute(req);
	BufferedReader rd = new BufferedReader(new InputStreamReader(response
		.getEntity().getContent()));
	String line = rd.readLine();
	log.debug("return:"+line);
	return line;
    }
    

    private static List<Puerto> getListPuertos() {
	List<Puerto> ret = new ArrayList<Puerto>();
	ret.add(getPuertoJaulin());
	ret.add(getPuertoPedrosas());
	ret.add(getPuertoAlcubierre());
	ret.add(getPuertoMezalocha());
	ret.add(getPuertoCodos());
	ret.add(getPuertoAguaron());
	ret.add(getPuertoTosos());
	ret.add(getPuertoAguilon());
	ret.add(getPuertoVillanueva());
	ret.add(getPuertoLaMuela());
	ret.add(getPuertoSanEsteban());
	ret.add(getPuertoValmadrid());
	ret.add(getPuertoMonlora();)
	return ret;
    }

    private static Puerto getPuertoJaulin() {
	Puerto p = new Puerto();
	p.setNombre("Jaulin");
	p.setNombreExtendido("Alto de Jaulin");
	p.setKm((float)5.5);
	return p;
    }

    private static Puerto getPuertoPedrosas() {
	Puerto p = new Puerto();
	p.setNombre("Pedrosas");
	p.setNombreExtendido("Alto de Las Pedrosas");
	return p;
    }

    private static Puerto getPuertoAlcubierre() {
    	//TODO vertientes Alcubierre-Alcubierre y Alcubierre-Lecinena
	Puerto p = new Puerto();
	p.setNombre("Alcubierre");
	p.setNombreExtendido("Alto de Alcubierre");
	p.setAltitud(500);
	return p;
    }

    private static Puerto getPuertoMezalocha() {
    	//TODO vertientes Mezalocha-norte y Mezalocha-sur
	Puerto p = new Puerto();
	p.setNombre("Mezalocha");
	p.setNombreExtendido("Alto de Mezalocha");
	p.setDesnivelMedio((float)4.25);
	return p;
    }

    private static Puerto getPuertoCodos() {
	Puerto p = new Puerto();
	p.setNombre("Codos");
	p.setNombreExtendido("Alto de Aguaron(Por Codos)");
	p.setDesnivelMetros(160);
	return p;
    }

    private static Puerto getPuertoAguaron() {
	Puerto p = new Puerto();
	p.setNombre("Aguaron");
	p.setNombreExtendido("Alto de Aguaron");
	return p;
    }

    private static Puerto getPuertoTosos() {
	Puerto p = new Puerto();
	p.setNombre("Tosos");
	p.setNombreExtendido("Alto de Tosos");
	return p;
    }

    private static Puerto getPuertoAguilon() {
	Puerto p = new Puerto();
	p.setNombre("Aguilon");
	p.setNombreExtendido("Puerto de Aguilon");
	return p;
    }

    private static Puerto getPuertoVillanueva() {
	Puerto p = new Puerto();
	p.setNombre("Villanueva");
	p.setNombreExtendido("Alto de Villanueva");
	return p;
    }

    private static Puerto getPuertoLaMuela() {
	Puerto p = new Puerto();
	p.setNombre("Muela");
	p.setNombreExtendido("Alto de la Muela (carretera antigua)");
	return p;
    }

    private static Puerto getPuertoSanEsteban() {
    	//TODO vertientes sur y norte!
	Puerto p = new Puerto();
	p.setNombre("SanEsteban-norte");
	p.setNombreExtendido("Alto de San Esteban-Norte");
	return p;
    }
    
    
}
