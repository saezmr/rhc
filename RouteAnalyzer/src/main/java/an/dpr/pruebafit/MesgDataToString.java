package an.dpr.pruebafit;

/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

import java.util.Collection;
import java.util.Iterator;

import an.dpr.routeanalyzer.reader.FitField;

import com.garmin.fit.Field;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;

public class MesgDataToString implements MesgListener {

    private StringBuilder sb = new StringBuilder();

    public MesgDataToString() {
    }

    public String getInfo() {
	return sb.toString();
    }

    public void onMesg(Mesg paramMesg) {
	String dato = ".";
	Collection localCollection = paramMesg.getFields();
	Iterator localIterator = localCollection.iterator();
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
