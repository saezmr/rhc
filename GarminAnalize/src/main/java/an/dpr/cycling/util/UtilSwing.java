package an.dpr.cycling.util;

import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Utiles Para UI Swing
 * @author rsaez
 *
 */
public class UtilSwing {

    public static File getFile(String... extensiones){
	File file = null;
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter;
	for(String ext : extensiones){
	    filter = new FileNameExtensionFilter(ext,ext);
	    chooser.setFileFilter(filter);
	}
	int approval = chooser.showOpenDialog(null);
	if (approval == JFileChooser.APPROVE_OPTION){
	    file = chooser.getSelectedFile();
	}
	return file;
    }
    
    public static void main(String[] args){
	Date date = new Date(new Long("1338618630000"));
	System.out.println(date);
    }
}
