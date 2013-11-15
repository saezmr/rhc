package an.dpr.routeanalyzer.beans;

public class HRBean implements Comparable{

    @Override
    public String toString() {
	return "HRBean [time=" + time + ", hr=" + hr + "]";
    }

    public Long time;
    public Integer hr;

    public Long getTime() {
	return time;
    }

    public void setTime(Long time) {
	this.time = time;
    }

    public Integer getHr() {
	return hr;
    }

    public void setHr(Integer hr) {
	this.hr = hr;
    }

    @Override
    public int compareTo(Object obj) {
	int ret = -1;
	if (obj instanceof HRBean){
	    HRBean t = this;
	    HRBean o = (HRBean)obj;
	    return t.getTime().compareTo(o.getTime());
	}
	return ret;
    }

}
