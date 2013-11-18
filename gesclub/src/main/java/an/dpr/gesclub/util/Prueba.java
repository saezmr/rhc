package an.dpr.gesclub.util;

import an.dpr.gesclub.beans.ClasificacionSalidas;
import an.dpr.gesclub.beans.ItemClasificacionSalidas;

public class Prueba {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	ClasificacionSalidas cs = new ClasificacionSalidas();
	ItemClasificacionSalidas item = new ItemClasificacionSalidas();
	item.setNumeroSalidas(1);
	item.setNumSocio(78);
	cs.addItem(item);

	item = new ItemClasificacionSalidas();
	item.setNumeroSalidas(3);
	item.setNumSocio(45);
	cs.addItem(item);

	item = new ItemClasificacionSalidas();
	item.setNumeroSalidas(2);
	item.setNumSocio(14);
	cs.addItem(item);

	for (ItemClasificacionSalidas it : cs.getClasificacion()) {
	    System.out.println(it.getNumSocio() + "-" + it.getNumeroSalidas());
	}

    }

}
