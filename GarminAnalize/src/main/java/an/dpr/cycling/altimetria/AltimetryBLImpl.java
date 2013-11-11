package an.dpr.cycling.altimetria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.Climb;
import an.dpr.cycling.beans.ConfiguracionAltimetriaBean;
import an.dpr.garmin.analize.AltitudBean;

public class AltimetryBLImpl implements AltimetryBL {

    private Double corteNegativo = 100.0;// conf
    private Double minMetros = 2000.0;// conf
    private Double minDesnivel = 2.0;// conf
    private Double kmIniciales = 300.0;// conf
    private Double minDesnivelInicial = 4.0;// conf
    private Double maxDescenso = 2000.0;// para cuando el corte negativo es muy

    @Override
    /**
     * TODO marcar suelo de altitud minima que seria inicio de puerto
     * TODO desechar km iniciales sino supera X portentaje
     */
    public List<Climb> getClimbs(Set<AltimetryPoint> data) {
	List<Climb> ret = new ArrayList<Climb>();
	Climb climb = null;
	Double kmActual = 0.0;
	Double xaux = 0.0;
	Double xpass = 0.0;
	// Double yaux = 0.0;
	Double yant = 0.0;
	Double altNeg = 0.0;
	Double highYX = 0.0;
	AltimetryPoint highap = null;
	boolean climbFinalizado = true;
	boolean filtradoInicial = false;
	for (AltimetryPoint ap : data) {
	    // punto mas alto
	    if (highap != null && highap.getAltitud() < ap.getAltitud()) {
		highap = ap;
		highYX = kmActual;
	    }
	    if (climbFinalizado && yant > ap.getAltitud()) {
		yant = ap.getAltitud();
		continue;
	    } else if (climbFinalizado) {
		climb = new Climb();
		climb.setKmIni(kmActual);
		climb.setInitPoint(ap);
		highap = ap;
		highYX = kmActual;
		climbFinalizado = false;
		filtradoInicial = false;
	    } else if (altNeg > corteNegativo
		    || (kmActual - highYX) > maxDescenso) {
		climb.setFinishPoint(highap);
		if (climb.getMetros() > minMetros
			&& climb.getDesnivel() > minDesnivel) {
		    ret.add(climb);
		}
		climbFinalizado = true;
		highap = null;
		highYX = kmActual;
		xaux = 0.0;
		yant = 0.0;
		altNeg = 0.0;
	    } else if (yant > ap.getAltitud()) {
		altNeg += yant - ap.getAltitud();
		xpass += ap.getMetros();
		// TODO quiza un unico metro de ascension
		// no sea suficiente para limpiar el corte
		// } else if (altNeg > 0 && ap.getAltitud() > yant) {
		// altNeg = 0.0;
		// xpass = 0.0;
	    } else if (kmActual > 11000) {
		int rai = 0;
	    }
	    xaux += ap.getMetros();
	    kmActual += ap.getMetros();
	    yant = ap.getAltitud();
	    if (!climbFinalizado) {
		climb.setMetros(xaux);
		if (!filtradoInicial && climb.getMetros() > kmIniciales) {
		    if (filtrarKmIniciales(climb, ap)) {
			climbFinalizado = true;
			highap = null;
			highYX = kmActual;
			xaux = 0.0;
			yant = 0.0;
			altNeg = 0.0;
		    } else {
			filtradoInicial = true;
		    }
		}
	    }
	}
	if (highap != null) {
	    climb.setFinishPoint(highap);
	    if (climb.getMetros() > minMetros
		    && climb.getDesnivel() > minDesnivel) {
		ret.add(climb);
	    }
	}
	return ret;
    }

    private boolean filtrarKmIniciales(Climb climb, AltimetryPoint ap) {
	boolean ret = false;
	climb.setFinishPoint(ap);
	if (minDesnivelInicial > climb.getDesnivel()) {
	    ret = true;
	}
	climb.setFinishPoint(null);
	return ret;
    }

    @Override
    public Collection<AltimetryPoint> getClimbData(Climb climb,
	    Set<AltimetryPoint> data) {
	Collection<AltimetryPoint> ret = new TreeSet<AltimetryPoint>();
	boolean init = false;
	for (AltimetryPoint ap : data) {
	    if (ap.equals(climb.getInitPoint())) {
		init = true;
	    } else if (ap.equals(climb.getFinishPoint())) {
		ret.add(ap);
		break;
	    }
	    if (init) {
		ret.add(ap);
	    }
	}
	return ret;
    }

    /**

    * TODO finalAlto, control de parametros correctos
     * 
     * @param data
     * @param kmIni
     * @param kmFin
     * @param finalAlto
     * @return
     */
    public Climb getClimbByKm(Set<AltimetryPoint> data, Double kmIni,
	    Double kmFin, boolean finalAlto) {
	Climb climb = new Climb();
	Double kmActual = 0.0;
	Double kmAux = 0.0;
	boolean inicializado = false;
	for (AltimetryPoint ap : data) {
	    kmActual += ap.getMetros();
	    if (!inicializado && kmActual > kmIni) {
		climb = new Climb();
		climb.setKmIni(kmActual);
		climb.setInitPoint(ap);
		inicializado = true;
	    } else if (kmActual > kmFin) {
		climb.setFinishPoint(ap);
		break;
	    } else if (inicializado) {
		kmAux += ap.getMetros();
	    }
	}
	return climb;
    }

    /**
     * metodo para inicializar el objeto
     * 
     * @postConstruct
     */
    public void init() {
	ConfiguracionAltimetriaBean conf = ConfiguracionAltimetria
		.getConfiguracion();
	corteNegativo = conf.getCorteNegativoPuertos();
	minMetros = conf.getMinMetrosPuertos();
	minDesnivel = conf.getMinDesnivelPuertos();
	kmIniciales = conf.getKmInicialesPuertos();
	minDesnivelInicial = conf.getMinDesnivelInicialPuertos();
    }
}
