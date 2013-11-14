package an.dpr.garmin.analize;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.xmlpull.v1.XmlPullParserException;

public class GarminAnalize {

    private static final Logger log = Logger.getLogger(GarminAnalize.class);
    private static final int z0 = 121;
    private static final int z1 = 135;
    private static final int z2 = 149;
    private static final int z3 = 163;
    private static final int z4 = 176;
    private static final int z5 = 190;
    

    private static final String ZONA_0 = "zona 0";
    private static final String ZONA_1 = "zona 1";
    private static final String ZONA_2 = "zona 2";
    private static final String ZONA_3 = "zona 3";
    private static final String ZONA_4 = "zona 4";
    private static final String ZONA_5 = "zona 5";
    private static final String ZONA_6 = "zona 6";
    

    public static void main(String[] args) throws IOException,
	    XmlPullParserException {
	File f = new File("C:\\var\\ganalize\\ReconocimientoPR200.gpx");
	FileReader fr = new FileReader(f);
	BufferedReader br = new BufferedReader(fr);
	StringBuffer xml = new StringBuffer();
	String line = null;
	while ((line = br.readLine()) != null) {
	    xml.append(line);
	}
	List<HRBean> listado = XMLGraminTrackConverter
		.getHRList(xml.toString());
	listado = completarTiempos(listado);
	int totalSec = listado.size();
	Collections.sort(listado);
	obtenerInfoZonas(listado);
    }

    private static void obtenerInfoZonas(List<HRBean> listado) {
	double[] hr =new double[10];
	hr[8] = listado.size();
	for (HRBean bean : listado) {
	    hr[7] += bean.getHr();
	    if (bean.getHr() < z0) {
		hr[0]++;
	    } else if (bean.getHr() < z1) {
		hr[1]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z2) {
		hr[2]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z3) {
		hr[3]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z4) {
		hr[4]++;
		hr[9] += bean.getHr();
	    } else if (bean.getHr() < z5) {
		hr[5]++;
		hr[9] += bean.getHr();
	    } else {
		hr[6]++;
		hr[9] += bean.getHr();
	    }
	}
	printPorcentajeZonas(hr);
	printTiempoZonas(hr);
	showChart(hr);
    }
	
	
    private static void showChart(double[] hr) {
	JFrame frame = new JFrame("Zonas");
	frame.setSize(400, 400);
	
	
	JFreeChart chart = 
		createPieChart(hr);
//		createBarChart(hr);
	ChartPanel panel = new ChartPanel(chart);
	frame.add(panel);
	frame.setVisible(true);
	frame.addWindowListener(new WindowAdapter() {
	    
	    @Override
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	    
	    @Override
	    public void windowClosed(WindowEvent e) {
		System.exit(0);
	    }
	    
	});
    }
    
    
    private static JFreeChart createBarChart(double[] hr) {
	DefaultCategoryDataset dataset = createBarDataset(hr);
	String title = "Zonas";
	JFreeChart chart =
	ChartFactory.createBarChart3D(title, "pulsaciones", "% total", dataset,
		PlotOrientation.VERTICAL, true, true, false);

	CategoryPlot plot = (CategoryPlot) chart.getPlot();
	BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
	barRenderer.setSeriesPaint(0, Color.white);
	barRenderer.setSeriesPaint(1, Color.blue);
	barRenderer.setSeriesPaint(2, Color.green);
	barRenderer.setSeriesPaint(3, Color.yellow);
	barRenderer.setSeriesPaint(4, Color.orange);
	barRenderer.setSeriesPaint(5, Color.red);
	barRenderer.setSeriesPaint(6, Color.black);
	return chart;
    }
    
    private static  DefaultCategoryDataset createBarDataset(double[] hr) {
	DefaultCategoryDataset result = new DefaultCategoryDataset();
        result.setValue(((hr[0] / hr[8]) * 100), ZONA_0, "pulsaciones");
        result.setValue(((hr[1] / hr[8]) * 100), ZONA_1, "pulsaciones");
        result.setValue(((hr[2] / hr[8]) * 100), ZONA_2, "pulsaciones");
        result.setValue(((hr[3] / hr[8]) * 100), ZONA_3, "pulsaciones");
        result.setValue(((hr[4] / hr[8]) * 100), ZONA_4, "pulsaciones");
        result.setValue(((hr[5] / hr[8]) * 100), ZONA_5, "pulsaciones");
        result.setValue(((hr[6] / hr[8]) * 100), ZONA_6, "pulsaciones");
        return result;
        
    }

    private static JFreeChart createPieChart(double[] hr) {
	PieDataset ds = createDataset(hr);
	String title = "Zonas";
	JFreeChart chart = 
		ChartFactory.createPieChart3D(title,ds,true,true,false);
		
//	PiePlot3D plot = (PiePlot3D) chart.getPlot();
//	plot.setStartAngle(290);
//	plot.setDirection(Rotation.CLOCKWISE);
//	plot.setForegroundAlpha(0.5f);
	
	//colores
	PiePlot plot = (PiePlot) chart.getPlot();
	plot.setSectionPaint(ZONA_0, Color.white);
	plot.setSectionPaint(ZONA_1, Color.blue);
	plot.setSectionPaint(ZONA_2, Color.green);
	plot.setSectionPaint(ZONA_3, Color.yellow);
	plot.setSectionPaint(ZONA_4, Color.orange);
	plot.setSectionPaint(ZONA_5, Color.red);
	plot.setSectionPaint(ZONA_6, Color.black);
	return chart;
    }

    private static  PieDataset createDataset(double[] hr) {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue(ZONA_0,((hr[0] / hr[8]) * 100));
        result.setValue(ZONA_1,((hr[1] / hr[8]) * 100));
        result.setValue(ZONA_2,((hr[2] / hr[8]) * 100));
        result.setValue(ZONA_3,((hr[3] / hr[8]) * 100));
        result.setValue(ZONA_4,((hr[4] / hr[8]) * 100));
        result.setValue(ZONA_5,((hr[5] / hr[8]) * 100));
        result.setValue(ZONA_6,((hr[6] / hr[8]) * 100));
        return result;
        
    }

    private static void printTiempoZonas(double[] hr) {
	log.debug("total:"+new Time((long)(hr[8]-3600)*1000));
	log.debug("total z 1-6:"+new Time((long)(hr[8]-hr[0]-3600)*1000));
	log.debug("zona 0:" + new Time((long)(hr[0]-3600)*1000));
	log.debug("zona 1:" + new Time((long)(hr[1]-3600)*1000));
	log.debug("zona 2:" + new Time((long)(hr[2]-3600)*1000));
	log.debug("zona 3:" + new Time((long)(hr[3]-3600)*1000));
	log.debug("zona 4:" + new Time((long)(hr[4]-3600)*1000));
	log.debug("zona 5:" + new Time((long)(hr[5]-3600)*1000));
	log.debug("zona 6:" + new Time((long)(hr[6]-3600)*1000));
    }
    
    

    private static void printPorcentajeZonas(double[] hr) {
	DecimalFormat df = new DecimalFormat("0.00");
	log.debug("pul media:" + (hr[7] / hr[8]));
	log.debug("pul m z 1-6:" + (hr[9] / (hr[8]-hr[0])));
	log.debug("zona 0:" + df.format(((hr[0] / hr[8]) * 100)));
	log.debug("zona 1:" + df.format(((hr[1] / hr[8]) * 100)));
	log.debug("zona 2:" + df.format(((hr[2] / hr[8]) * 100)));
	log.debug("zona 3:" + df.format(((hr[3] / hr[8]) * 100)));
	log.debug("zona 4:" + df.format(((hr[4] / hr[8]) * 100)));
	log.debug("zona 5:" + df.format(((hr[5] / hr[8]) * 100)));
	log.debug("zona 6:" + df.format(((hr[6] / hr[8]) * 100)));
    }

    /**
     * Completa el mapa segundo a segundo
     */
    private static List<HRBean> completarTiempos(List<HRBean> listado) {
	List<HRBean> list = new ArrayList<HRBean>();
	HRBean antBean = null;
	for (HRBean bean : listado) {
	    list.add(bean);
	    // log.debug(bean);
	    if (antBean != null) {
		long secf = (bean.getTime() - antBean.getTime()) / 1000;
		int difhr = bean.getHr() - antBean.getHr();
		// log.debug(secf+"/"+difhr);
		for (int i = 1; i < secf; i++) {
		    HRBean newBean = new HRBean();
		    newBean.setTime(antBean.getTime() + (i * 1000));
		    newBean.setHr(antBean.getHr() + (int) (difhr / secf));
		    // log.debug(newBean);
		    list.add(newBean);
		}
	    }
	    antBean = bean;
	}
	return list;
    }

}
