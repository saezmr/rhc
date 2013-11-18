package an.dpr.gesclub.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import an.dpr.gesclub.security.AppSecurity;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

public class LogoutView  extends CustomComponent implements View {

    private static final long serialVersionUID = 1L;
    private Label txt = new Label();
    private Link link = new Link();
    public static final String NAME = "logout";
    
    public LogoutView(){
        setCompositionRoot(new CssLayout(txt, link));
    }
    @Override
    public void enter(ViewChangeEvent event) {
	String username = AppSecurity.getUserName() ;
	if (username == null){
	    txt.setValue("Logout correcto");
	    link.setCaption("Tornar a Pachina Prencipal");
	    String basepath = VaadinServletService.getCurrentServletRequest().getServletPath();
	    link.setResource(new ExternalResource(basepath));
	} else {
	    txt.setValue(username+" sigue logeado");
	    getUI().getNavigator().navigateTo(MainView.NAME);
	}
    }

}
