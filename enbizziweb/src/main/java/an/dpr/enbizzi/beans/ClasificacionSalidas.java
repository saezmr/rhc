package an.dpr.enbizzi.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Item de una clasificacion de salidas
 * @author rsaez
 *
 */
@XmlRootElement(name="Clasificacion")
public class ClasificacionSalidas {

    public Integer posicion;
    public String nombre;
    public Integer numeroSalidas;
    
}
