package an.dpr.cycling.altimetria;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import an.dpr.cycling.beans.AltimetryPoint;
import an.dpr.cycling.beans.Climb;
import an.dpr.garmin.analize.AltitudBean;

public interface AltimetryBL {

    public List<Climb> getClimbs(Set<AltimetryPoint> data);

    public Collection<AltimetryPoint> getClimbData(Climb climb,
	    Set<AltimetryPoint> data);
    
}
