package an.dpr.gesclub.util;

import java.util.ResourceBundle;

import com.vaadin.ui.Notification;

public class ViewUtils {
    
    private static ResourceBundle bundle = I18nText.getBundle();
    
    public static void mostrarAviso(String mensaje) {
	Notification.show(bundle.getString("aviso.titulo"),
                mensaje,Notification.Type.WARNING_MESSAGE);
    }

}
