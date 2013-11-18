package an.dpr.gesclub.view;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import an.dpr.gesclub.contracts.MunicipioContracts;
import an.dpr.gesclub.dao.MunicipiosDao;
import an.dpr.gesclub.dao.SociosDao;
import an.dpr.gesclub.domain.Municipio;
import an.dpr.gesclub.security.AppSecurity;
import an.dpr.gesclub.security.Permiso;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {

    private static final Logger log = Logger.getLogger(MainView.class);
    private static final long serialVersionUID = 1L;
    public static final String NAME = "";
    private static final ResourceBundle bundle;
    static {
	bundle = ResourceBundle.getBundle("/i18n");
    }

    @Autowired
    private SociosDao sociosDao;
    @Autowired
    private MunicipiosDao municipiosDao;

    private Label text = new Label();
    private MenuBar mb = new MenuBar();

    Button logout = new Button("Logout", new Button.ClickListener() {

	@Override
	public void buttonClick(ClickEvent event) {

	    AppSecurity.logout();

	    // Refresh this view, should redirect to login view
	    getUI().getNavigator().navigateTo(LogoutView.NAME);
	}
    });

    public MainView() {
	VerticalLayout mainLayout = new VerticalLayout(mb, text, logout);
	mainLayout.setMargin(true);
	setCompositionRoot(mainLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	text.setValue("Hello " + AppSecurity.getUserName());
	createMenu();
    }

    /**
     * TODO refactorizar para que en cuenta de permisos rol_menu sean 
     * permisos socios_menu, salidas_menu etc...
     */
    private void createMenu() {
	if (Integer.valueOf(0).equals(Integer.valueOf(mb.getItems().size()))) {
	    if (AppSecurity.isPermited(Permiso.menu_socios)) {
		addMenuSocios();
	    } 
	    if (AppSecurity.isPermited(Permiso.menu_salidas)) {
		addMenuSalidas();
	    }
	    if (AppSecurity.isPermited(Permiso.presidente_permiso)){
		addMenuPresidente();
	    }
	}
    }

    private void addMenuPresidente() {
//	menuCargaMunicipios();
    }
    
    
    private void menuCargaMunicipios(){
	MenuBar.Command muniCom = new MenuBar.Command(){

	    @Override
	    public void menuSelected(MenuItem selectedItem) {
		int index = 0;
		for(Municipio municipio : MunicipioContracts.getMunicipiosZaragoza()){
		    municipiosDao.insert(municipio);
		    index ++;
		    log.debug("insert "+municipio);
		}
		Notification.show("Municipios",
	                  "Insertados "+index+" municipios",
	                  Notification.Type.WARNING_MESSAGE);
	    }};
	    mb.addItem("Carga Municipios", muniCom);
    }

    private void addMenuSalidas() {
	MenuBar.Command salidasCom = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
		getUI().getNavigator().navigateTo(SalidasListView.NAME);
	    }
	};
	mb.addItem(bundle.getString("salidas.menu"), salidasCom);
    }

    private void addMenuSocios() {
	MenuBar.Command sociosCom = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
		getUI().getNavigator().navigateTo(SociosListView.NAME);
	    }
	};
	mb.addItem(bundle.getString("socios.menu"), sociosCom);
	MenuBar.Command clasificacionCom = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
		getUI().getNavigator().navigateTo(ClasificacionSalidasView.NAME);
	    }
	};
	mb.addItem(bundle.getString("clasificacion.menu"), clasificacionCom);
    }

    public SociosDao getSociosDao() {
	return sociosDao;
    }

    public void setSociosDao(SociosDao sociosDao) {
	this.sociosDao = sociosDao;
    }

    public MunicipiosDao getMunicipiosDao() {
        return municipiosDao;
    }

    public void setMunicipiosDao(MunicipiosDao municipiosDao) {
        this.municipiosDao = municipiosDao;
    }

}
