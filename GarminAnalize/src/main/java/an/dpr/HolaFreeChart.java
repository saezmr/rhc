package an.dpr;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class HolaFreeChart {

    /**
     * @param args
     */
    public static void main(String[] args) {
	DefaultPieDataset dataset = new DefaultPieDataset();
	dataset.setValue("Linux", 29);
	dataset.setValue("Mac", 20);
	dataset.setValue("Windows", 51);
	JFreeChart chart = ChartFactory.createPieChart3D("titulo", dataset,
		true, true, false);
	JFrame frame = new JFrame("chart");
	ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
	frame.add(panel);
	frame.setVisible(true);
	System.out.println("finish");
    }

}
