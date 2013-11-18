package an.dpr.gesclub.view;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import an.dpr.gesclub.beans.ClasificacionSalidas;
import an.dpr.gesclub.beans.ItemClasificacionSalidas;
import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.dao.SociosDao;
import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.util.GesclubContracts;
import an.dpr.gesclub.util.I18nText;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ClasificacionSalidasView extends CustomComponent implements View {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger
	    .getLogger(ClasificacionSalidasView.class);
    public static final String NAME = "ClasificacionList";
    private SociosDao socioDao;
    private ResourceBundle bundle = I18nText.getBundle();
    // elementos vista
    private VerticalLayout mainLayout;
    private Table table;
    private Button btnVolver;

    public ClasificacionSalidasView() {
	log.debug("new instance");
	initComponents();
    }

    private void initComponents() {
	table = new Table(bundle.getString("clasificacion.titulo"));
	table.setSelectable(true);
	table.setImmediate(true);
	table.setWidth("70%");

	// botones
	btnVolver = new Button("Volver");
	btnVolver.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainView.NAME);
	    }
	});
	mainLayout = new VerticalLayout(table, btnVolver);
	mainLayout.setMargin(true);
	setCompositionRoot(mainLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	log.debug("inicio");
	updateTable();
    }
    
    private void updateTable(){
	User user = (User) getSession().getAttribute(GesclubContracts.SESSION_USER);
	log.debug("obtenemos clasificacion salidas"+user.getClub().getNombre());
	Long clubId = user.getClub().getId();
	ClasificacionSalidas cs = socioDao.getClasificacionSalidas(clubId, 2013, TipoSalida.ROAD_CLUB);

	BeanItemContainer<ItemClasificacionSalidas> container 
		= new BeanItemContainer<ItemClasificacionSalidas>(
		ItemClasificacionSalidas.class, cs.getClasificacion());
	table.setContainerDataSource(container);
	
	table.setVisibleColumns(new String[] { "posicion", "numSocio", "socio", "numeroSalidas"});
	table.setColumnHeaders(new String[] { 
		bundle.getString("clasificacion.posicion"),
		bundle.getString("socios.numero"),
		bundle.getString("socios.nombre"),
		bundle.getString("clasificacion.numero.salidas")});
	table.sort(new Object[] { "posicion"}, new boolean[] {
		true, true });
    }
    
    public SociosDao getSocioDao() {
	return socioDao;
    }

    public void setSocioDao(SociosDao socioDao) {
	this.socioDao = socioDao;
    }
}
