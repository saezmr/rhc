package an.dpr.enbizzi.util;

import java.util.ArrayList;
import java.util.List;

import an.dpr.enbizzi.domain.Comarca;

public class ComarcasContract {
    
    public enum Provincias{
	Huesca(22), Zaragoza(50), Teruel(44);
	
	private Integer id;
	Provincias(Integer id){
	    this.id = id;
	}
	
	public static Provincias getById(Integer id){
	    Provincias ret = null;
	    for(Provincias p: values()){
		if (p.id.equals(id)){
		    ret = p;
		    break;
		}
	    }
	    return ret;
	}
    }
    
    public enum Paises{
	Spain(34);
	
	private Integer id;
	Paises(Integer id){
	    this.id = id;
	}
	
	public static Paises getById(Integer id){
	    Paises ret = null;
	    for(Paises p: values()){
		if (p.id.equals(id)){
		    ret = p;
		    break;
		}
	    }
	    return ret;
	}
    }

    public static List<Comarca> getComarcasAragon(){
	List<Comarca> list = new ArrayList<Comarca>();
	list.add(new Comarca("La Jacetania", 22));
	list.add(new Comarca("Alto G�llego", 22));
	list.add(new Comarca("Sobrarbe", 22));
	list.add(new Comarca("La Ribagorza", 22));
	list.add(new Comarca("Cinco Villas", 50));
	list.add(new Comarca("Hoya de Huesca", 22));
	list.add(new Comarca("Somontano de Barbastro", 22));
	list.add(new Comarca("Cinca Medio", 22));
	list.add(new Comarca("La Litera", 22));
	list.add(new Comarca("Los Monegros", 50));
	list.add(new Comarca("Bajo Cinca", 50));
	list.add(new Comarca("Tarazona y el Moncayo", 50));
	list.add(new Comarca("Campo de Borja", 50));
	list.add(new Comarca("Aranda", 50));
	list.add(new Comarca("Ribera Alta del Ebro", 50));
	list.add(new Comarca("Valdejal�n", 50));
	list.add(new Comarca("Zaragoza ", 50));
	list.add(new Comarca("Ribera Baja del Ebro ", 50));
	list.add(new Comarca("Bajo Arag�n-Caspe ", 50));
	list.add(new Comarca("Comunidad de Calatayud", 50));
	list.add(new Comarca("Campo de Cari�ena", 50));
	list.add(new Comarca("Campo de Belchite", 50));
	list.add(new Comarca("Bajo Mart�n", 44));
	list.add(new Comarca("Campo de Daroca", 50));
	list.add(new Comarca("Jiloca", 44));
	list.add(new Comarca("Cuencas Mineras", 44));
	list.add(new Comarca("Andorra-Sierra de Arcos", 44));
	list.add(new Comarca("Bajo Arag�n", 44));
	list.add(new Comarca("Comunidad de Teruel", 44));
	list.add(new Comarca("Maestrazgo", 44));
	list.add(new Comarca("Sierra de Albarrac�n", 44));
	list.add(new Comarca("G�dar-Javalambre", 44));
	list.add(new Comarca("Matarra�a", 44));
	return list;
    }
}
