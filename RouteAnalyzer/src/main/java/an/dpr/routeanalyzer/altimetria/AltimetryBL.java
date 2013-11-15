package an.dpr.routeanalyzer.altimetria;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import an.dpr.routeanalyzer.beans.AltimetryPoint;
import an.dpr.routeanalyzer.beans.AltitudBean;
import an.dpr.routeanalyzer.beans.Climb;

public interface AltimetryBL {

    public List<Climb> getClimbs(Set<AltimetryPoint> data);

    public Collection<AltimetryPoint> getClimbData(Climb climb,
	    Set<AltimetryPoint> data);
    
}
