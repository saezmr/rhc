
package an.dpr.cycling.beans;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Historial CVS de $Id: ConfiguracionAltimetriaBean.java $
 * $Log: ConfiguracionAltimetriaBean.java,v $
 *
 */
/**
 * ConfiguracionAltimetriaBean
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class ConfiguracionAltimetriaBean implements Serializable{

    //valores por defecto
    private static final String NOMBRE = "Desconocido";
    private static final Integer REDUCTOR_X = 10;
    private static final Integer REDUCTOR_Y = 1;
    private static final Integer PORCENTAJE_CADA_METROS = 100;
    private static final Double MOSTRAR_RAMPAS_MAYORES = 5.0;
    private static final Boolean VOLTEAR_AUTO = true;
    private static final String IDIOMA = "es_ES";
    private static final Color COLOR_LINEAS = Color.BLACK;
    private static final Color COLOR_LINEAS_SUAVES = Color.LIGHT_GRAY;
    private static final Color COLOR_TEXTO = Color.BLACK;
    private static final Color COLOR_KM_IMPAR = new Color(0xf5e6e1);
    private static final Color COLOR_KM_PAR = new Color(0xfcfbf5);
    private static final Color COLOR_HORIZONTE = Color.WHITE;
    private static final ImageIcon ICONO_MARCA = null;
    private static final Integer SEGUNDOS_INTERVALO = 5;
    private static final Integer FACTOR_CORRECCION_ALTURA = 0;
    private static final Integer OFFSET_X = 100;
    private static final Integer OFFSET_Y = 100;
    private static final Integer MULTIPLICADOR = 1;
    private static final String RUTA_IMG = "/home/rsaez/Documents/ganalize/altimetria.png";
    

    public ConfiguracionAltimetriaBean(){
        
    }

    private String nombre;
    private Integer reductorX;
    private Integer reductorY;
    private Integer porcentajeCadaMetros;
    private Double mostrarRampasMayores;
    private Boolean voltearAuto;
    private String idioma;
    private Integer segundosIntervalo;
    private Integer factorCorreccionAltura;
    private Double corteNegativoPuertos;
    private Double minMetrosPuertos;
    private Double minDesnivelPuertos;
    private Double kmInicialesPuertos;
    private Double minDesnivelInicialPuertos;

    //colores
    private Color colorLineas;
    private Color colorLineasSuaves;
    private Color colorTexto;
    private Color colorKmImpar;
    private Color colorKmPar;
    private Color colorHorizonte;

    /** Icono que el usuario configura para mostrar en sus altimetrias */
    private ImageIcon iconoMarca;

    private Integer offsetX;
    private Integer offsetY;
    private Integer multiplicador;
    private String rutaImg;


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        if (nombre == null)
            this.nombre = NOMBRE;
        else
            this.nombre = nombre;
    }

   

    /**
     * @return the porcentajeCadaMetros
     */
    public Integer getPorcentajeCadaMetros() {
        return porcentajeCadaMetros;
    }

    /**
     * @param porcentajeCadaMetros the porcentajeCadaMetros to set
     */
    public void setPorcentajeCadaMetros(Integer porcentajeCadaMetros) {
        if (porcentajeCadaMetros == null)
            this.porcentajeCadaMetros = PORCENTAJE_CADA_METROS;
        else
            this.porcentajeCadaMetros = porcentajeCadaMetros;
    }

    /**
     * @return the mostrarRampasMayores
     */
    public Double getMostrarRampasMayores() {
        return mostrarRampasMayores;
    }

    /**
     * @param mostrarRampasMayores the mostrarRampasMayores to set
     */
    public void setMostrarRampasMayores(Double mostrarRampasMayores) {
        if (mostrarRampasMayores == null)
            this.mostrarRampasMayores = MOSTRAR_RAMPAS_MAYORES;
        else
            this.mostrarRampasMayores = mostrarRampasMayores;
    }

    /**
     * @return the voltearAuto
     */
    public Boolean getVoltearAuto() {
        return voltearAuto;
    }

    /**
     * @param voltearAuto the voltearAuto to set
     */
    public void setVoltearAuto(Boolean voltearAuto) {
        if (voltearAuto == null)
            this.voltearAuto = VOLTEAR_AUTO;
        else
            this.voltearAuto = voltearAuto;
    }

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        if (idioma == null)
            this.idioma = IDIOMA;
        else
            this.idioma = idioma;
    }

    /**
     * @return the reductorX
     */
    public Integer getReductorX() {
        return reductorX;
    }

    /**
     * @param reductorX the reductorX to set
     */
    public void setReductorX(Integer reductorX) {
        if (reductorX == null)
            this.reductorX = REDUCTOR_X;
        else
            this.reductorX = reductorX;
    }

    /**
     * @return the reductorY
     */
    public Integer getReductorY() {
        return reductorY;
    }

    /**
     * @param reductorY the reductorY to set
     */
    public void setReductorY(Integer reductorY) {
        if (reductorY == null)
            this.reductorY = REDUCTOR_Y;
        else
            this.reductorY = reductorY;
    }

    /**
     * @return the colorLineas
     */
    public Color getColorLineas() {
        return colorLineas;
    }

    /**
     * @param colorLineas the colorLineas to set
     */
    public void setColorLineas(Color colorLineas) {
        if (colorLineas == null)
            this.colorLineas = COLOR_LINEAS;
        else
            this.colorLineas = colorLineas;
    }

    /**
     * @return the colorTexto
     */
    public Color getColorTexto() {
        return colorTexto;
    }

    /**
     * @param colorTexto the colorTexto to set
     */
    public void setColorTexto(Color colorTexto) {
        if (colorTexto == null)
            this.colorTexto = COLOR_TEXTO;
        else
            this.colorTexto = colorTexto;
    }

    /**
     * @return the colorKm1
     */
    public Color getColorKmImpar() {
        return colorKmImpar;
    }

    /**
     * @param colorKm1 the colorKm1 to set
     */
    public void setColorKmImpar(Color colorKm1) {
        if (colorKm1 == null)
            this.colorKmImpar = COLOR_KM_IMPAR;
        else
            this.colorKmImpar = colorKm1;
    }

    /**
     * @return the colorKm3
     */
    public Color getColorKmPar() {
        return colorKmPar;
    }

    /**
     * @param colorKm3 the colorKm3 to set
     */
    public void setColorKmPar(Color colorKm3) {
        if (colorKm3 == null)
            this.colorKmPar = COLOR_KM_PAR;
        else
            this.colorKmPar = colorKm3;
    }

    /**
     * @return the colorHorizonte
     */
    public Color getColorHorizonte() {
        return colorHorizonte;
    }

    /**
     * @param colorHorizonte the colorHorizonte to set
     */
    public void setColorHorizonte(Color colorHorizonte) {
        if (colorHorizonte == null)
            this.colorHorizonte = COLOR_HORIZONTE;
        else
            this.colorHorizonte = colorHorizonte;
    }

    /**
     * @return the iconoMarca
     */
    public ImageIcon getIconoMarca() {
        return iconoMarca;
    }

    /**
     * @param iconoMarca the iconoMarca to set
     */
    public void setIconoMarca(ImageIcon iconoMarca) {
        this.iconoMarca = iconoMarca;
    }

    /**
     * @return the colorLineasSuaves
     */
    public Color getColorLineasSuaves() {
        return colorLineasSuaves;
    }

    /**
     * @param colorLineasSuaves the colorLineasSuaves to set
     */
    public void setColorLineasSuaves(Color colorLineasSuaves) {
        if (colorLineasSuaves == null)
            this.colorLineasSuaves = COLOR_LINEAS_SUAVES;
        else
            this.colorLineasSuaves = colorLineasSuaves;
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
        if (segundosIntervalo == null)
            this.segundosIntervalo = SEGUNDOS_INTERVALO;
        else
            this.segundosIntervalo = segundosIntervalo;
    }

    /**
     * @return the factorCorreccionAltura
     */
    public Integer getFactorCorreccionAltura() {
        return factorCorreccionAltura;
    }

    /**
     * @param factorCorreccionAltura the factorCorreccionAltura to set
     */
    public void setFactorCorreccionAltura(Integer factorCorreccionAltura) {
        if (factorCorreccionAltura == null)
            this.factorCorreccionAltura = FACTOR_CORRECCION_ALTURA;
        else
            this.factorCorreccionAltura = factorCorreccionAltura;
    }

     /**
     * @return the offsetx
     */
    public Integer getOffsetX() {
        return offsetX;
    }

    /**
     * @param offsetx the offsetx to set
     */
    public void setOffsetX(Integer offsetx) {
        if (offsetx == null)
            this.offsetX = OFFSET_X;
        else
            this.offsetX = offsetx;
    }

     /**
     * @return the offsety
     */
    public Integer getOffsetY() {
        return offsetY;
    }

    /**
     * @param offsety the offsety to set
     */
    public void setOffsetY(Integer offsety) {
        if (offsety == null)
            this.offsetY = OFFSET_Y;
        else
            this.offsetY = offsety;
    }
    
     /**
     * @return the multiplicador
     */
    public Integer getMultiplicador() {
        return multiplicador;
    }

    /**
     * @param offsety the offsety to set
     */
    public void setMultiplicador(Integer multiplicador) {
        if (multiplicador == null)
            this.multiplicador = MULTIPLICADOR;
        else
            this.multiplicador = multiplicador;
    }



	public String getRutaImg() {
		return rutaImg;
	}

	public void setRutaImg(String rutaImg) {
		if (rutaImg == null){
			this.rutaImg=RUTA_IMG;
		}
		this.rutaImg = rutaImg;
	}

	public Double getCorteNegativoPuertos() {
		return corteNegativoPuertos;
	}

	public void setCorteNegativoPuertos(Double corteNegativoPuertos) {
		this.corteNegativoPuertos = corteNegativoPuertos;
	}

	public Double getMinMetrosPuertos() {
		return minMetrosPuertos;
	}

	public void setMinMetrosPuertos(Double minMetrosPuertos) {
		this.minMetrosPuertos = minMetrosPuertos;
	}

	public Double getMinDesnivelPuertos() {
		return minDesnivelPuertos;
	}

	public void setMinDesnivelPuertos(Double minDesnivelPuertos) {
		this.minDesnivelPuertos = minDesnivelPuertos;
	}

	public Double getKmInicialesPuertos() {
		return kmInicialesPuertos;
	}

	public void setKmInicialesPuertos(Double kmInicialesPuertos) {
		this.kmInicialesPuertos = kmInicialesPuertos;
	}

	public Double getMinDesnivelInicialPuertos() {
		return minDesnivelInicialPuertos;
	}

	public void setMinDesnivelInicialPuertos(Double minDesnivelInicialPuertos) {
		this.minDesnivelInicialPuertos = minDesnivelInicialPuertos;
	}

}
