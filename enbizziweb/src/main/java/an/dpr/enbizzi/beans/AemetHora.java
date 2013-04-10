package an.dpr.enbizzi.beans;

public enum AemetHora {
	p06,
	p12,
	p18,
	p24;
	
	public static AemetHora getPeriodo(String periodo){
		AemetHora ret = null;
		for(AemetHora ap : AemetHora.values()){
			if (ap.aCadena().equals(periodo)){
				ret = ap;
				break;
			}
		}
		return ret;
	}
	
	private String aCadena(){
		StringBuilder retValue = new StringBuilder();
		retValue.append(name().substring(1,3));
		return retValue.toString();
	}
}
