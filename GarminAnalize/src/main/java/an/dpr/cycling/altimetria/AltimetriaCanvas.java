
package an.dpr.cycling.altimetria;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.ConfiguracionAltimetriaBean;
import an.dpr.cycling.beans.Rampa;

/**
 * Historial CVS de $Id: AltimetriaCanvas.java $
 * $Log: AltimetriaCanvas.java,v $
 *
 */
/**
 * AltimetriaCanvas
 *
 * @author ricardo.saez, Ultima modificacion:  $Author:  $
 * @version $Revision:  $ $Date: $
 */
public class AltimetriaCanvas extends Canvas{

    private static final Logger log = Logger.getLogger(AltimetriaCanvas.class);
    private Collection<AltimetryPoint> data = null;
    private double[][] puntos;
    private int porcentajeCadaMetros = 10000;
    private DecimalFormat df = new DecimalFormat("##0.##");
    private BufferedImage bImagen = null;
    private Image img = null;
    private ConfiguracionAltimetriaBean conf;

    /**
     * MaximaX -> X maxima de la altimetria (aplicado el reductor)
     * MaximaY -> Y maxima de la altimetria (aplicado el reductor)
     * MinimaY -> Y minima de la altimetria (aplicado el reductor)
     * limiteY -> Y maxima de la imagen
     * limiteX -> X maxima de la imagen
     * OffsetX -> offset entre el limite x de la grafica y el de la imagen
     * OffsetY -> offset entre el limite y de la grafica y el de la imagen
     */
    private int reductorX = 10;
    private int reductorY = 1;
    private int maximaX = 0;
    private int yAltitudMaxima = 0;
    private int yAltitudMinima = 9999;
    private int limiteY = 0;
    private int limiteX = 0;
    private int offsetX= 100;
    private int offsetY= 100;
    private int multiplicador = 1;//TODO, usar el multiplicador
    private String rutaImg;
    private int altitudMaxima = 0;
    private double longitudMetros = 0.0;
    private int altitudMinima = 0;

    private int altitudInicial = 0;
    private int altitudFinal = 0;
    private Double altitudAcumulada = 0.0;
    private int factorCorreccionAltura = 0;
    private int metrosTotales = 0;
    private double pendienteMedia;
    private Double pendienteMaxima = 0.0;
    private double mostrarRampasMayores = 5;
    private String nombrePuerto = "";
    private boolean voltearAuto = true;
    private int minMetrosCalcularPendiente = 50;//TODO a configuracion!!!
    private Double kmIni = 0.0;
    private double[] pendienteKms;
    private List<Rampa> rampas;

    //colores
    private Color colorLineas;
    private Color colorLineasSuaves;
    private Color colorTexto;
    private Color colorKmImpar;
    private Color colorKmPar;
    private Color colorHorizonte;
    private Color colorRampas = Color.RED;//TODO

    public AltimetriaCanvas(Collection<AltimetryPoint> data,String nombre){
        this(data, 0.0, nombre, null, null, null, true);
    }

    public AltimetriaCanvas(Collection<AltimetryPoint> data, Double kmIni,
	    String nombre, Integer reductor, Integer porcentajeCadaMetros,
	    Double mostrarRampasMayores, boolean voltearAuto){
        this.data = data;
        if (kmIni != null)
            this.kmIni = kmIni;
        this.nombrePuerto = nombre != null ? nombre : "SinNombre";
        this.voltearAuto = voltearAuto;
        if (reductor != null) 
            this.reductorX = reductor;
        if (porcentajeCadaMetros != null)
            this.porcentajeCadaMetros = porcentajeCadaMetros;
        if (mostrarRampasMayores != null)
            this.mostrarRampasMayores = mostrarRampasMayores;
        init();
    }

    public AltimetriaCanvas(Collection<AltimetryPoint> data, Double kmIni, 
	    String nombre, ConfiguracionAltimetriaBean conf) {
	this.conf = conf;
        this.data = data;
        if (kmIni != null)
            this.kmIni = kmIni;
        this.nombrePuerto = nombre != null ? nombre : conf.getNombre();
        this.voltearAuto = conf.getVoltearAuto();
        if (conf.getReductorX() != null)
            this.reductorX = conf.getReductorX();
        if (conf.getReductorY() != null)
            this.reductorY = conf.getReductorY();
        if (conf.getPorcentajeCadaMetros() != null)
            this.porcentajeCadaMetros = conf.getPorcentajeCadaMetros();
        if (conf.getMostrarRampasMayores() != null)
            this.mostrarRampasMayores = conf.getMostrarRampasMayores() ;
        if (conf.getFactorCorreccionAltura() != null)
            this.factorCorreccionAltura = conf.getFactorCorreccionAltura();
        if (conf.getOffsetX() != null)
            this.offsetX = conf.getOffsetX();
        if (conf.getOffsetY() != null)
            this.offsetY = conf.getOffsetY();
        if (conf.getMultiplicador() != null)
            this.multiplicador = conf.getMultiplicador();
        if(conf.getRutaImg() != null){
        	this.rutaImg = conf.getRutaImg();
        }
        this.setColores(conf);
        init();
    }

    private void setColores(ConfiguracionAltimetriaBean conf){
        colorLineas = conf.getColorLineas();
        colorLineasSuaves = conf.getColorLineasSuaves();
        colorTexto = conf.getColorTexto();
        colorKmImpar = conf.getColorKmImpar();
        colorKmPar = conf.getColorKmPar();
        colorHorizonte = conf.getColorHorizonte();
    }


    private void init(){
        this.puntos = obtenerPuntos();
        obtenerMaximos();
        obtenerPendienteMedia();        
        pendienteKms = obtenerPendienteKm();
        rampas = getRampas();
        bImagen = new BufferedImage(limiteX, limiteY, BufferedImage.TYPE_INT_RGB);
        crearImagen();
        img = bImagen.getScaledInstance(limiteX, limiteY, 1);
        this.setSize(limiteX,limiteY);
    }

    private void crearImagen() {
        Graphics g = bImagen.createGraphics();
        g.setFont(new Font("Verdana", Font.PLAIN, 10));
        g.setColor(colorHorizonte);
        g.fillRect(0, 0, limiteX, limiteY);
        
        crearLineasKm(g);
        limpiarHorizonte(g);
        escribirPendienteKms(g);
        crearPerfil(g);
        crearReglaAltitud(g);
        introducirTextos(g);
        pintarRampas(g);
        
        g.dispose();
    }
    
    /**
     * Pinta el resumen del puerto
     * @param g
     */
    private void introducirTextos(Graphics g) {
        g.setFont(new Font("Verdana", Font.BOLD, 15));
        g.drawString(nombrePuerto, 50, 20);
        g.setFont(new Font("Verdana", Font.PLAIN, 10));
        int posY = 50;
        g.drawString("pendienteMax" + " : " + df.format(pendienteMaxima) + "%", 60, posY);
        g.drawString("pendienteAvg" + " : " + df.format(pendienteMedia) + "%", 60, (posY += 15));
        g.drawString("altitudIni"+ " : " + this.altitudInicial, 60, (posY += 15));
        g.drawString("altitudMax"+ " : " + this.altitudMaxima, 60, (posY += 15));
        g.drawString("altitudFin" + " : " + this.altitudFinal, 60, (posY += 15));
        g.drawString("Km's: " + df.format(new Double(this.metrosTotales) / 1000), 60, (posY += 15));
        g.drawString("altitudAcumulada" + " : " + this.altitudAcumulada.intValue(), 60, (posY += 15));
        if (kmIni > 0.0)
            g.drawString("Km Inicial Ruta" + " : " + df.format(kmIni/1000), 60, (posY += 15));
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * calcula la x real, a partir de la x ficticia, aplicandole reductores y offset
     * @param x
     * @return
     */
    private int calculaX(double x){
        return new Double(x/reductorX).intValue()+offsetX/2;
    }

    private int calculaY(double y){
        return limiteY - offsetY/2 - new Double((y-altitudMinima)/reductorY).intValue();
    }


    @Override
    public void paint(Graphics g){
        g.drawImage(img, 0, 0,  this);
    }

    /**
     * Pinta la linea de perfil, la "carretera", y los porcentajes por rampas
     * TODO seria interesante sacar los portencentajes de rampa a otro metodo
     * @param g
     */
    private void pintarRampas(Graphics g){
	g.setColor(colorRampas);
	boolean pendienteArriba = true;
        for(Rampa rampa: rampas){
            double x = rampa.getmIni();
            double x2 = rampa.getmFin();
            double y = rampa.getAltitudIni();
            double y2 = rampa.getAltitudFin();
            double pendiente = rampa.getPendiente();
            for(int i=-2; i<3 ; i++){
        	g.drawLine(calculaX(x), calculaY(y+i), calculaX(x2) , calculaY(y2+i));
            }
            int offsetY2 = pendienteArriba ? -25 : 25;
            g.drawString(df.format(pendiente)+"%", calculaX(x2), calculaY(y2)+offsetY2);
            g.drawLine(calculaX(x2) , calculaY(y2), calculaX(x2)-5, calculaY(y2)+offsetY2);
            pendienteArriba = !pendienteArriba;
        }
    }
    
    private void crearPerfil(Graphics g){
	g.setColor(colorLineas);
	double x = 1.0;
	double y = puntos[1][1];
	int contPorcentajes = 1;
	Double xAc = 0.0;
	Double yAc = new Double(puntos[1][1]);
	boolean pendienteArriba = true;
	for (int i = 1; i<puntos.length; i++){
	    double x2 = x+puntos[i][0];
	    double y2 = puntos[i][1];
	    if (x2/contPorcentajes >= porcentajeCadaMetros){
		Double pendiente = new Double( ((y2-yAc)/(x2-xAc))*100);
		if (pendiente >= mostrarRampasMayores){
		    g.drawString(df.format(pendiente)+"%", calculaX(x2), pendienteArriba ? calculaY(y2)-25 : calculaY(y2)+25);
		    
		    g.setColor(colorLineasSuaves);
		    g.drawLine(calculaX(x2) , calculaY(y2), calculaX(x2)-5, pendienteArriba ? calculaY(y2)-25 : calculaY(y2)+20);
		    pendienteArriba = !pendienteArriba;
		}
		yAc = new Double(y2);
		xAc = new Double(x2);
		contPorcentajes++;
	    }
	    g.setColor(colorLineas);
	    if (x2 != 0){
		g.drawLine(calculaX(x), calculaY(y), calculaX(x2) , calculaY(y2));
	    }
	    x = x2;
	    y = y2;
	}
    }

    /**
     * Crea las reglas de altitud a los laterales
     * @param g
     */
    private void crearReglaAltitud(Graphics g){
        g.drawLine(5, Double.valueOf(yAltitudMaxima).intValue(), 
        	5, Double.valueOf(yAltitudMinima).intValue());
        g.drawLine(limiteX-30, Double.valueOf(yAltitudMaxima).intValue(), 
        	limiteX-30, Double.valueOf(yAltitudMinima).intValue());
        for (double altitud = altitudMinima; altitud< altitudMaxima; altitud++){
            if (altitud%50 == 0){
                g.setColor(colorLineas);
                g.drawLine(5, calculaY(altitud), 10, calculaY(altitud));
                g.drawLine(limiteX-30, calculaY(altitud), limiteX-25, calculaY(altitud));

                g.setColor(colorTexto);
                g.drawString(String.valueOf(altitud), 11, calculaY(altitud));
                g.drawString(String.valueOf(altitud), limiteX-24, calculaY(altitud));
            }
        }
    }

    /**
     * Crea las rellenos de color de los km y las lineas de pie de los km
     * @param g
     */
    private void crearLineasKm(Graphics g){
        int[] xPoints;
        int[] yPoints;
        double kmMax = longitudMetros;
        g.setColor(colorLineas);
        g.fillRect(calculaX(0),yAltitudMinima, calculaX(longitudMetros)-calculaX(0), 4);
        for (int i = 0; i<=kmMax; i=i+1000){
            xPoints = new int[]{calculaX(i),calculaX(i),calculaX(i+1000),calculaX(i+1000)};
            yPoints = new int[]{yAltitudMaxima,yAltitudMinima, yAltitudMinima, yAltitudMaxima};

            g.setColor(getColorKm(i));
            g.fillPolygon(xPoints, yPoints, 4);
            g.setColor(colorLineasSuaves);
            g.drawLine(calculaX(i),yAltitudMaxima,calculaX(i),yAltitudMinima);
            
            if ((i/1000)%2 > 0){
                g.setColor(Color.WHITE);
                g.fillRect(calculaX(i),yAltitudMinima+1, 1000/reductorX, 2);
            }
        }
        g.setColor(colorLineas);
    }
    
    private Color getColorKm(int km){
	Color color = null;
	if(ConfiguracionAltimetria.COLOR_KM_RAMPA.equals(conf.getColorKm())){
	    if (pendienteKms[km/1000]>conf.getKmCinco()){
		color = conf.getColorCinco(); 
	    } else if (pendienteKms[km/1000]>conf.getKmCuatro()){
		color = conf.getColorCuatro();
	    } else if (pendienteKms[km/1000]>conf.getKmTres()){
		color = conf.getColorTres();
	    } else if (pendienteKms[km/1000]>conf.getKmDos()){
		color = conf.getColorDos();
	    } else {
		color = conf.getColorUno();
	    }
	} else {
	    if ((km/1000)%2 > 0){
		color = colorKmImpar;
	    } else {
		color = colorKmPar;
	    }
	}
	return color;
    }

    /**
     * Escribe los km y sus pendientes medias
     * @param g
     */
    private void escribirPendienteKms(Graphics g){
        g.setColor(colorTexto);
        int kmMax = Double.valueOf(longitudMetros).intValue();
        for (int i = 0; i<=kmMax; i=i+1000){
            g.drawString(df.format(pendienteKms[i/1000])+"%", calculaX(i)+2, yAltitudMinima-5);
            g.drawString(""+(i/1000), calculaX(i), yAltitudMinima+15);
        }
        g.drawString("Kms", calculaX(0), yAltitudMinima+30);
        g.setColor(colorLineas);
    }
    
    /**
     * Obtiene todas las posibles rampas de la ruta
     * TODO filtrar rampas que se montan
     * @return
     */
    public List<Rampa> getRampas(){
	List<Rampa> list = new ArrayList<Rampa>();
	List<Rampa> listAux = new ArrayList<Rampa>();
	List<Rampa> listToDel = new ArrayList<Rampa>();
	Double mAux = 0.0;
	for (int i = 1; i<puntos.length; i++){
	    double x = puntos[i][0];
	    double y = puntos[i][1];
	    Rampa rampa = getNewRampa(y, mAux);
	    listAux.add(rampa);
	    for(Rampa rl : listAux){
		rl.setAltitudFin(y);
		rl.setmFin(mAux);
		rl.setMetros(rl.getMetros()+x);
		if (rl.getMetros()>minMetrosCalcularPendiente){
		    if (guardarRampa(rl)){
			list.add(rl);
		    }
		    listToDel.add(rl);
		}
	    }
	    listAux.removeAll(listToDel);
	    mAux += x;
	}
	list = agruparRampas(list);
	return list;
    }

    private List<Rampa> agruparRampas(List<Rampa> list) {
	List<Rampa> ret = new ArrayList<Rampa>();
	boolean add = true;
	for(Rampa rampa : list){
	    add = true;
	    for(Rampa r : ret){
		if (r.getmFin()>rampa.getmIni()){
		    r.setmFin(rampa.getmFin());
		    r.setAltitudFin(rampa.getAltitudFin());
		    add = false;
		}
	    }
	    if(add)
		ret.add(rampa);
	}
	return ret;
    }

    private boolean guardarRampa(Rampa rl) {
	boolean guardar = false;
	if (rl.getMetros()>minMetrosCalcularPendiente){
	    double altitud = rl.getAltitudFin()-rl.getAltitudIni();
	    double pendiente = (altitud/rl.getMetros())*100;
	    if (pendiente >mostrarRampasMayores){
		rl.setPendiente(pendiente);
		guardar = true;
	    }
	}
	return guardar;
    }

    private Rampa getNewRampa(double y, double x) {
	Rampa rampa = new Rampa();
	rampa.setAltitudIni(y);
	rampa.setmIni(x);
	rampa.setMetros(0.0);
	return rampa;
    }

    private double[] obtenerPendienteKm(){
        int kmReducido = 1000/reductorX;
        int kmMax = ((this.maximaX)/kmReducido)+1;
        int countKm = 0;
        double[] pKm = new double[kmMax];
        double x = 1;
        double y = puntos[0][1];
        int contPorcentajes = 1;
        Double xAc = 0.0;
        Double yAc = new Double(puntos[1][1]);
        for (int i = 1; i<puntos.length; i++){
            double x2 = x+puntos[i][0];
            double y2 = puntos[i][1];
            if (x2/contPorcentajes >= 1000){
                Double pendiente = new Double( ((y2-yAc)/(x2-xAc))*100);
                pKm[countKm++] = pendiente;
                yAc = new Double(y2);
                xAc = new Double(x2);
                contPorcentajes++;
            }
            x = x2;
            y = y2;
        }
	Double pendiente = 0.0;
        if (x-xAc > minMetrosCalcularPendiente) {
        	pendiente = new Double( ((y-yAc)/(x-xAc))*100);
        }
        pKm[countKm++] = pendiente;
        return pKm;
    }

    /**
     * Borra el "cielo" desde la linea de altitud del perfil
     * @param g
     */
    private void limpiarHorizonte(Graphics g){
        int[] equis = new int[puntos.length+3];
        int[] is = new int[puntos.length+3];

        double x = 1.0;
        double y = 1.0;
        for (int i = 1; i<puntos.length; i++){
            double x2 = x+puntos[i][0];
            double y2 = puntos[i][1];
            g.setColor(colorLineas);
            equis[i] = calculaX(x2);
            is[i] = calculaY(y2);
            x = x2;
            y = y2;
        }
        equis[0] = 0;
        is[0] = 0;
        equis[1] = 0;
        is[1] = calculaY(altitudInicial);
        equis[puntos.length] = calculaX(x);
        is[puntos.length] = limiteY;
        equis[puntos.length+1] = limiteX;
        is[puntos.length+1] = limiteY;
        equis[puntos.length+2] = limiteX;
        is[puntos.length+2] = 0;
        Polygon p = new Polygon(equis, is, puntos.length+3);
        g.setColor(colorHorizonte);
        g.fillPolygon(p);
    }

    /**
     * Obtiene los datos maximos de pendiente, km, altitud...
     * @param puntos
     */
    private void obtenerMaximos() {
        Double pMax = 0.0;
        double x = 0;
        double y = 0.0;
        int contPorcentajes = 1;
        Double xAc = 0.0;
        Double yAc = new Double(puntos[1][1]);
        for (int i = 1; i<puntos.length; i++){
            double x2 = x+puntos[i][0];
            double y2 = puntos[i][1];
            actualizarAltitudAcumulada(y, y2);
            if (x2/contPorcentajes >= minMetrosCalcularPendiente){
                Double pendiente = new Double( ((y2-yAc)/(x2-xAc))*100);
                if (pendiente > pMax){
                    pMax = pendiente;
                }
                yAc = new Double(y2);
                xAc = new Double(x2);
                contPorcentajes++;
            }
            x = x2;
            y = y2;
        }
        altitudMaxima = Double.valueOf(puntos[0][0]).intValue();
        altitudMinima = Double.valueOf(puntos[0][1]).intValue();
        longitudMetros = x;
        altitudInicial = Double.valueOf(puntos[1][1]).intValue();
        altitudFinal = Double.valueOf(puntos[puntos.length-1][1]).intValue();
        
        maximaX = (Double.valueOf(x).intValue()/reductorX);
        pendienteMaxima = pMax;
        metrosTotales = Double.valueOf(x).intValue();
        limiteY = (altitudMaxima/reductorY) - (altitudMinima/reductorY) + 30 + offsetY;
        limiteX = maximaX + offsetX;
        yAltitudMaxima = calculaY(puntos[0][0]);
        yAltitudMinima = calculaY(altitudMinima)+30;
    }

    /**
     * suma a la altitud acumulada la diferencia de altitud entre ini y fin.
     * @param ini
     * @param fin
     */
    private void actualizarAltitudAcumulada(double altitudInicial, double altitudFinal){
        if (altitudInicial>0 && altitudInicial<altitudFinal){
            altitudAcumulada+=(altitudFinal-altitudInicial);
        }
    }

    private void obtenerPendienteMedia() {
        int desnivel = altitudFinal - altitudInicial;// puntos[0][0] - puntos[0][1];
        pendienteMedia = (new Double(desnivel)/new Double(metrosTotales))*100;
    }

//    private int[][] obtenerPuntos(){
    private double[][] obtenerPuntos(){
        double[][] pts = new double[data.size()+1][2];
        double yMax = 0.0;
        double yMin = 5000.0;
        int cont = 1;

        for(AltimetryPoint dpi : data){
            if (dpi.getAltitud() == null)
                continue;
            double aux = dpi.getMetros().doubleValue();//TODO double para mejorar precision
            double auy = dpi.getAltitud().doubleValue()
        	    +factorCorreccionAltura;//TODO double para mejorar precision
            pts[cont][0] = aux;
            pts[cont++][1] = auy;

            if(auy > yMax){
                yMax = auy;
            }
            if (auy < yMin){
                yMin = auy;
            }

        }
        pts[0][0] = yMax;
        pts[0][1] = yMin;

        if (voltearAuto && isVolteable(pts))
            pts = voltear(pts);

        return pts;
    }

    //cambia el orden de los puntos si la altura inicial es mayor que la final, para altimetrias de descenso.
    private double[][] voltear(double[][] pts) {
	int size = pts.length;
	double[][] ptsRet = new double[size][2];

        for (int i = 1; i<size; i++){
            ptsRet[i] = pts[size-i];
        }
        ptsRet[0][0] = pts[0][0];
        ptsRet[0][1] = pts[0][1];


        return ptsRet;
    }

    private boolean isVolteable(double[][] pts){
        int size = pts.length;
        boolean retValue = false;
        int altitudIni = 0;
        int altitudFin = 0;
        int cont = 0;

        for (int i = 1; i<size; i++){
            altitudIni += pts[i][1];
            cont++;
            if (cont > 3)
                break;
        }
        cont = 0;
        for (int i = size-1; i>0; i--){
            altitudFin += pts[i][1];
            cont++;
            if (cont > 3)
                break;
        }

        if (altitudIni > altitudFin){
            retValue = true;
        }

        return retValue;
    }

    /**
     * Guarda la imagen cargada en la ruta indicada
     * @param ruta
     * @throws java.io.IOException
     */
	public void guardarImagen(String ruta) throws IOException {
		File file = null;
		file = new File(ruta);
		if (ruta.contains("jpg")) {
			ImageIO.write(bImagen, "jpg", file);
		} else if (ruta.contains("png")) {
			ImageIO.write(bImagen, "png", file);
		} else if (ruta.contains("gif")) {
			ImageIO.write(bImagen, "gif", file);
		}
		log.info("archivoGuardadoCorrecto " + ruta);// "Archivo guardado correctamente");
	}

    /**
     * Devuelve la imagen de la altimetria creada en el canvas
     * @return objeto Image
     */
    public Image getImagen(){
        return img;
    }


}
