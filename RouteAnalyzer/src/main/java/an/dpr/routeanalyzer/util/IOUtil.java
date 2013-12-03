package an.dpr.routeanalyzer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IOUtil {

    /**
     * @param args
     * @throws IOException 
     */
    public static String readFile(File file) throws IOException {
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	StringBuffer xml = new StringBuffer();
	String line = null;
	while ((line = br.readLine()) != null) {
	    xml.append(line);
	    xml.append("\n");
	}
	return xml.toString();
    }

}
