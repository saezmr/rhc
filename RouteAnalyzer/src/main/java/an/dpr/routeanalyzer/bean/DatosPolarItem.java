
package an.dpr.routeanalyzer.bean;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Historial CVS de $Id: DatosPolarItem.java $
 * $Log: DatosPolarItem.java,v $
 *
 */
/**
 * DatosPolarItem
 *
 * Bean de los datos a exportar de polar.
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class DatosPolarItem implements AltimetryPoint{

    private Double altitud;
    private Double metros;
    private Integer id;
    private Integer pulsaciones;
    private Double velocidad;
    private Integer segundosIntervalo;

    public DatosPolarItem(){
    }

    /**
     * 
     * @param id contador para ordenar
     * @param linea lintea de texto de donde scar la info
     * @param segundosRegistroPolar segundos de intervalo de registro de datos configurado en el aparato
     */
    public void loadDatos(Integer id, String linea, Integer segundosRegistroPolar){
        StringTokenizer st = new StringTokenizer(linea, "\t");
        this.setId(id);
        int cont = 0;
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            if (cont == 0){
                this.setPulsaciones(Integer.valueOf(token));
            } else if (cont == 1){
                this.setVelocidad(Double.valueOf(token));
            } else if (cont == 3){
                this.setAltitud(Double.valueOf(token));
            }
            cont++;
        }
        this.setSegundosIntervalo(segundosRegistroPolar);
        this.setMetros(getMetrosRecorridos());
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the pulsaciones
     */
    public Integer getPulsaciones() {
        return pulsaciones;
    }

    /**
     * @param pulsaciones the pulsaciones to set
     */
    public void setPulsaciones(Integer pulsaciones) {
        this.pulsaciones = pulsaciones;
    }

    /**
     * @return the velocidad
     */
    public Double getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * De momento tomtamos 5s como referencia
     * @return
     */
    public Double getMetrosRecorridos(){
        if (this.velocidad != null) {
            Double vms = ((this.velocidad*1000)/3600)*segundosIntervalo;
            return vms;
        } else {
            return 0.0;
        }
    }

    /**
     * @return the segundosIntervalo
     */
    public Integer getSegundosIntervalo() {
        return segundosIntervalo;
    }

    /**
     * @param segundosIntervalo the segundosIntervalo to set
     */
    public void setSegundosIntervalo(Integer segundosIntervalo) {
        this.segundosIntervalo = segundosIntervalo;
    }

    @Override
    public int compareTo(AltimetryPoint o) {
	return id.compareTo(((DatosPolarItem)o).getId());
    }

    @Override
    public Double getMetros() {
	return metros;
    }

    public Double getAltitud() {
        return altitud;
    }

    public void setAltitud(Double altitud) {
        this.altitud = altitud;
    }

    public void setMetros(Double metros) {
        this.metros = metros;
    }
    
    public Date getDate(){
	//TODO por implementar
	return null;
    }

}
