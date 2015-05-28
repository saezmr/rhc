package an.dpr;

public class CalculoPotencia {
    
    public static void main(String...args){
	double po = 0;//potencia
	double rpm = 0;
	double m = 78;//masa
	double d=3000;//distancia en metros
	double h=300;//desnivel en metros
	double tm=20;//minutos
	double ts=47;//segundos
	
	double tt = tm*60+ts;
	double fu = 0;
	if (d > 0 && tt > 0){
	    fu = 15 + (d/tt) / 3 + 9.8*m*h/d;
	} 
	double tr = fu * d;
	if (tt != 0 ){
	    po = tr/tt;
	}
	if (m!= 0){
	    rpm = po/m;
	}
	System.out.println("fuerza: "+fu);
	System.out.println("trabajo: "+ tr);
	System.out.println("julios: "+tr/4180+"Kc");
	System.out.println("Potencia media: "+po+"W");
	System.out.println("relacion potencia/peso: "+rpm+" W/Kg");
    }
    
}


/*
 *  <!--
  
   function Formatea( n, d)
      If Not isNull(n) THEN
	 If isNumeric( CStr(n) ) then
	    x = FormatNumber( CStr(n) , d )
	 Else
	    x = "0"
	 End If
      Else
	 x = "0"			
      End If
      Formatea = x
   End Function
  
    Sub RealizaCalculos()
    	if not isNumeric(m.Value) then
    	   msgbox("Debes introducir un nº válido en la masa.")
    	   exit sub
    	end if
    	if not isNumeric(d.Value) then
    	   msgbox("Debes introducir un nº válido en la distancia (d).")
    	   exit sub
    	end if    	
    	if not isNumeric(h.Value) then
    	   msgbox("Debes introducir un nº válido en el desnivel (h).")
    	   exit sub
    	end if
    	if not isNumeric(tm.Value) then
    	   msgbox("Debes introducir un nº válido en el tiempo (m).")
    	   exit sub
    	end if
    	if not isNumeric(ts.Value) then
    	   msgbox("Debes introducir un nº válido en el tiempo (s).")
    	   exit sub    	   
    	end if
    	TT = tm.Value * 60 + ts.Value
    	if d.Value <> 0 and TT <> 0 then
    	    FU = 15 + ( d.Value / TT ) / 3 + 9.8 * m.Value * h.Value / d.Value
    	else	
    	    FU = 0
    	end if 
    	TR = FU * d.Value
    	IF TT <> 0 then
    	   PO = TR / TT
    	else 
    	   PO = 0
    	end if 
    	if ( m.Value <> 0 ) then
    	   RPM = PO / m.Value
    	end if
    	Resultado1.innerText = "Fuerza Aplicada: " & Formatea(FU, 0 ) + " Newton."
    	Resultado2.innerText = "Trabajo: " & Formatea(TR,0) & " Julios ( " & Formatea(TR/4180,0) & " Kilocalorías )" 
    	Resultado3.innerText = "Potencia Media Desarrollada:" & Formatea(PO,0) & " Watios " 
    	Resultado4.innerText = "Relación Potencia/Peso:" & Formatea(RPM,2) & " Watios/Kg. "
    end sub
  -->
 */
