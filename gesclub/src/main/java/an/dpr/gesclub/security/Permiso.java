package an.dpr.gesclub.security;

/**
 * Permisos de la aplicacion
 * @author rsaez
 *
 */
public enum Permiso {

    menu_salidas,
    menu_socios,
    salidas_write,
    salidas_read,
    presidente_permiso
    ;
    
    
    public String getPermiso() {
	return name().replace("_", ":");
    }
}
