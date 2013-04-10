package an.dpr.enbizzi.beans;

import java.text.SimpleDateFormat;

public enum AemetLocalidadTags {

	tag_root,
	tag_nombre,
	tag_provincia,
	tag_prediccion,
	tag_dia,
	param_fecha,
	tag_prob_precipitacion,
	param_periodo,
	tag_estado_cielo,
	param_descripcion,
	tag_viento,
	tag_direccion,
	tag_velocidad,
	tag_racha_max,
	tag_temperatura,
	tag_maxima,
	tag_minima,
	tag_dato,
	tag_sens_termica,
	param_hora,
	tag_humedad_relativa,
	tag_uv_max;
	
	private static final String TAG = "tag";
	private static final String PARAM = "param";
	
	public String getValue(){
		String retValue = null;
		if(this.name().startsWith(TAG)){
			retValue = this.name().substring(4);
		} else if(this.name().startsWith(PARAM)){
			retValue = this.name().substring(6);
		}
		return retValue;
	}
	
	public static final AemetLocalidadTags get(String tag){
		AemetLocalidadTags retValue = null;
		for (AemetLocalidadTags alt : AemetLocalidadTags.values()){
			if (alt.getValue().equals(tag)){
				retValue = alt;
			}
		}
		return retValue;
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final SimpleDateFormat getSdf(){
		return sdf;
	}
	
	public static final String XML_AEMET_PRUEBA = "<prediccion><dia fecha=\"2012-10-30\"><prob_precipitacion periodo=\"00-12\">5</prob_precipitacion><prob_precipitacion periodo=\"12-24\">60</prob_precipitacion><prob_precipitacion periodo=\"00-06\">0</prob_precipitacion><prob_precipitacion periodo=\"06-12\">5</prob_precipitacion><prob_precipitacion periodo=\"12-18\">30</prob_precipitacion><prob_precipitacion periodo=\"18-24\">55</prob_precipitacion><cota_nieve_prov periodo=\"00-12\"></cota_nieve_prov><cota_nieve_prov periodo=\"12-24\">1600</cota_nieve_prov><cota_nieve_provperiodo=\"00-06\"></cota_nieve_prov><cota_nieve_prov periodo=\"06-12\"></cota_nieve_prov><cota_nieve_provperiodo=\"12-18\">1600</cota_nieve_prov><cota_nieve_prov periodo=\"18-24\">1600</cota_nieve_prov><estado_cielo periodo=\"00-12\" descripcion=\"Nuboso\">14</estado_cielo><estado_cielo periodo=\"12-24\" descripcion=\"Muy nuboso con lluvia escasa\">45</estado_cielo><estado_cielo periodo=\"00-06\" descripcion=\"Nubes altas\">17n</estado_cielo><estado_cielo periodo=\"06-12\"descripcion=\"Nuboso\">14</estado_cielo><estado_cielo periodo=\"12-18\" descripcion=\"Muy nuboso\">15</estado_cielo><estado_cieloperiodo=\"18-24\" descripcion=\"Muy nuboso con lluvia escasa\">45n</estado_cielo><viento periodo=\"00-12\"><direccion>SE</direccion><velocidad>10</velocidad></viento><viento periodo=\"12-24\"><direccion>SE</direccion><velocidad>10</velocidad></viento><viento periodo=\"00-06\"><direccion>C</direccion><velocidad>0</velocidad></viento><viento periodo=\"06-12\"><direccion>SE</direccion><velocidad>10</velocidad></viento><viento periodo=\"12-18\"><direccion>SE</direccion><velocidad>10</velocidad></viento><viento periodo=\"18-24\"><direccion>C</direccion><velocidad>0</velocidad></viento><racha_max periodo=\"00-12\"></racha_max><racha_maxperiodo=\"12-24\"></racha_max><racha_max periodo=\"00-06\"></racha_max><racha_max periodo=\"06-12\"></racha_max><racha_maxperiodo=\"12-18\"></racha_max><racha_max periodo=\"18-24\"></racha_max><temperatura><maxima>16</maxima><minima>4</minima><dato hora=\"06\">5</dato><dato hora=\"12\">13</dato><dato hora=\"18\">12</dato><datohora=\"24\">10</dato></temperatura><sens_termica><maxima>16</maxima><minima>4</minima><datohora=\"06\">5</dato><dato hora=\"12\">13</dato><dato hora=\"18\">12</dato><dato hora=\"24\">10</dato></sens_termica><humedad_relativa><maxima>85</maxima><minima>55</minima><dato hora=\"06\">70</dato><datohora=\"12\">55</dato><dato hora=\"18\">70</dato><dato hora=\"24\">85</dato></humedad_relativa><uv_max>2</uv_max></dia><dia fecha=\"2012-10-31\"><prob_precipitacion periodo=\"00-12\">65</prob_precipitacion><prob_precipitacionperiodo=\"12-24\">0</prob_precipitacion><prob_precipitacion periodo=\"00-06\">35</prob_precipitacion><prob_precipitacionperiodo=\"06-12\">30</prob_precipitacion><prob_precipitacion periodo=\"12-18\">0</prob_precipitacion><prob_precipitacionperiodo=\"18-24\">0</prob_precipitacion><cota_nieve_prov periodo=\"00-12\">1600</cota_nieve_prov><cota_nieve_provperiodo=\"12-24\"></cota_nieve_prov><cota_nieve_prov periodo=\"00-06\">1500</cota_nieve_prov><cota_nieve_provperiodo=\"06-12\">1600</cota_nieve_prov><cota_nieve_prov periodo=\"12-18\"></cota_nieve_prov><cota_nieve_provperiodo=\"18-24\"></cota_nieve_prov><estado_cielo periodo=\"00-12\" descripcion=\"Intervalos nubosos con lluvia escasa\">43</estado_cielo><estado_cielo periodo=\"12-24\" descripcion=\"Poco nuboso\">12</estado_cielo><estado_cielo periodo=\"00-06\" descripcion=\"Intervalos nubosos con lluviaescasa\">43n</estado_cielo><estado_cielo periodo=\"06-12\" descripcion=\"Intervalos nubosos\">13</estado_cielo><estado_cielo periodo=\"12-18\"descripcion=\"Poco nuboso\">12</estado_cielo><estado_cielo periodo=\"18-24\" descripcion=\"Poco nuboso\">12n</estado_cielo><vientoperiodo=\"00-12\"><direccion>NO</direccion><velocidad>25</velocidad></viento><viento periodo=\"12-24\"><direccion>NO</direccion><velocidad>25</velocidad></viento><viento periodo=\"00-06\"><direccion>O</direccion><velocidad>15</velocidad></viento><viento periodo=\"06-12\"><direccion>NO</direccion><velocidad>25</velocidad></viento><viento periodo=\"12-18\"><direccion>O</direccion><velocidad>10</velocidad></viento><viento periodo=\"18-24\"><direccion>SO</direccion><velocidad>10</velocidad></viento><racha_max periodo=\"00-12\">55</racha_max><racha_maxperiodo=\"12-24\">55</racha_max><racha_max periodo=\"00-06\"></racha_max><racha_max periodo=\"06-12\">55</racha_max><racha_maxperiodo=\"12-18\"></racha_max><racha_max periodo=\"18-24\"></racha_max><temperatura><maxima>16</maxima><minima>8</minima><dato hora=\"06\">9</dato><dato hora=\"12\">13</dato><dato hora=\"18\">13</dato><datohora=\"24\">11</dato></temperatura><sens_termica><maxima>16</maxima><minima>6</minima><datohora=\"06\">7</dato><dato hora=\"12\">13</dato><dato hora=\"18\">13</dato><dato hora=\"24\">11</dato></sens_termica><humedad_relativa><maxima>90</maxima><minima>45</minima><dato hora=\"06\">90</dato><datohora=\"12\">50</dato><dato hora=\"18\">45</dato><dato hora=\"24\">65</dato></humedad_relativa><uv_max>2</uv_max></dia></prediccion>";
}

