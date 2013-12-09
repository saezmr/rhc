package an.dpr.routeanalyzer.reader;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import an.dpr.pruebafit.MesgDataToString;
import an.dpr.routeanalyzer.bean.HRBean;

import com.garmin.fit.Decode;
import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgDefinitionListener;
import com.garmin.fit.MesgListener;
import com.garmin.fit.csv.MesgFilter;

public class FitReader implements MesgListener {

	private static final Logger log = Logger.getLogger(FitReader.class);
	private StringBuilder sb = new StringBuilder();

	public FitReader() {
	}

	public String readFile(File file) {
		if (file == null){
			throw new IllegalArgumentException("file no puede ser nulo");
		}
		List<HRBean> list = null;
		Decode decode = new Decode();
		FileInputStream in = null;
		String fileName = file.getName();
		String info = null;

		try {
			if (!Decode.checkIntegrity((InputStream) new FileInputStream(file))){
				log.error("FIT file integrity failed.");
				throw new RuntimeException("FIT file integrity failed.");
			}
			log.debug(file+" es un archivo correcto");
			in = new FileInputStream(file);
			MesgFilter mesgFilter = new MesgFilter();
			ArrayList<String> mesgDefinitionsToOutput = new ArrayList<String>();
			ArrayList<String> dataMessagesToOutput = new ArrayList<String>();
			mesgFilter.setMesgDefinitionsToOutput(mesgDefinitionsToOutput);
			mesgFilter.setDataMessagesToOutput(dataMessagesToOutput);
			MesgDataToString dataMesgWriter = new MesgDataToString();
			mesgFilter.addListener((MesgListener) dataMesgWriter);

			decode.addListener((MesgDefinitionListener) mesgFilter);
			decode.addListener((MesgListener) mesgFilter);

			decode.read(in);
			log.debug("decodificado con exito");
			info = dataMesgWriter.getInfo();//TODO ¿synchronize? puede darse acceso concurrente?
		} catch (java.io.IOException e) {
			throw new RuntimeException("Error opening file ");
		} finally {
			try {
				in.close();
			} catch (java.io.IOException e) {
				throw new RuntimeException(e);
			}
		}
		return info;
	}

	public void onMesg(Mesg paramMesg) {
		Collection<Field> localCollection = paramMesg.getFields();
		Iterator<Field> localIterator = localCollection.iterator();
		while (localIterator.hasNext()) {
			Field localField = (Field) localIterator.next();
			int i = paramMesg.GetActiveSubFieldIndex(localField.getNum());

			String fValue = localField.getStringValue(0, i);

			if (fValue == null) {
				fValue = "";
			}
			for (int j = 1; j < localField.getNumValues(); ++j) {
				fValue = fValue + "|";

				String str3 = localField.getStringValue(j, i);

				if (str3 != null)
					fValue = fValue + str3;
			}
			String fUnits = localField.getUnits(i);
			if (fUnits.isEmpty()) {
				sb.append(paramMesg.getName() + "." + localField.getName(i) + FitField.FIELD_SEPARATOR
						+ fValue);
			} else {
				sb.append(paramMesg.getName() + "." + localField.getName(i) + "_" + fUnits
						+ FitField.FIELD_SEPARATOR + fValue);
			}
			sb.append(",");
		}
		sb.append("\n\r");
	}
}
