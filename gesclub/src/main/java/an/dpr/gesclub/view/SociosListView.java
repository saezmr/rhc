package an.dpr.gesclub.view;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import an.dpr.gesclub.beans.ClasificacionSalidas;
import an.dpr.gesclub.beans.CyclingType;
import an.dpr.gesclub.beans.ItemClasificacionSalidas;
import an.dpr.gesclub.beans.Role;
import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.dao.SociosDao;
import an.dpr.gesclub.dao.UsersDao;
import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.domain.UserRole;
import an.dpr.gesclub.security.AppSecurity;
import an.dpr.gesclub.util.GesclubContracts;
import an.dpr.gesclub.util.I18nText;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SociosListView extends CustomComponent implements View {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(SociosListView.class);
    public static final String NAME = "SociosList";
    private ResourceBundle bundle = I18nText.getBundle();
    @Autowired SociosDao socioDao;
    @Autowired UsersDao userDao;

    private VerticalLayout mainLayout;
    private Table table;
    private GridLayout form;
    private Button btnVolver;
    private FieldGroup fieldGroup;
    private TextField id;
    private TextField nif;
    private TextField nombre;
    private TextField apellido1;
    private TextField apellido2;
    private TextField numSocio;
    private DateField fechaAlta;
    private DateField fechaBaja;
    private DateField fechaNacimiento;
    private TextField email;
    private TextArea direccion;
    private TextField telefono;
    

    public SociosListView() {
	log.debug("instanciando");
	initComponents();
    }

    private void initComponents() {
	log.debug("inicio");

	HorizontalLayout tableControls = new HorizontalLayout();
	Button nuevo = new Button(bundle.getString("socios.nuevo"));
	Button delete = new Button(bundle.getString("socios.borrar"));
	tableControls.addComponent(nuevo);
	tableControls.addComponent(delete);

	table = new Table(bundle.getString("socios.titulo.listado"));
	table.setSelectable(true);
	table.setImmediate(true);
	table.setWidth("50%");
	table.addValueChangeListener(new ValueChangeListener() {
	    public void valueChange(ValueChangeEvent event) {
		editSocio((Socio) table.getValue());
	    }
	});

	// formulario edit
	fieldGroup = new FieldGroup();
	id = new TextField(bundle.getString("socios.id"));
	id.setConverter(Long.class);
	id.setVisible(false);
	nif = new TextField(bundle.getString("socios.nif"));
	nif.setNullRepresentation("");
	nombre = new TextField(bundle.getString("socios.nombre"));
	nombre.setNullRepresentation("");
	apellido1 = new TextField(bundle.getString("socios.apellido1"));
	apellido1.setNullRepresentation("");
	apellido2 = new TextField(bundle.getString("socios.apellido2"));
	apellido2.setNullRepresentation("");
	numSocio = new TextField(bundle.getString("socios.numero"));
	numSocio.setNullRepresentation("");
	numSocio.setConverter(Integer.class);
	fechaAlta = new DateField(bundle.getString("socios.fecha.alta"));
	fechaBaja = new DateField(bundle.getString("socios.fecha.baja"));
	email = new TextField(bundle.getString("socios.email"));
	email.setNullRepresentation("");
	telefono = new TextField(bundle.getString("socios.telefono"));
	telefono.setNullRepresentation("");
	direccion = new TextArea(bundle.getString("socios.direccion"));
	direccion.setNullRepresentation("");
	fechaNacimiento = new DateField(bundle.getString("socios.fecha.nacimiento"));
	Button save = new Button(bundle.getString("boton.guardar"));
	Button cancel = new Button(bundle.getString("boton.cancelar"));

	fieldGroup.bind(numSocio, "numSocio");
	fieldGroup.bind(nif, "nif");
	fieldGroup.bind(nombre, "nombre");
	fieldGroup.bind(apellido1, "apellido1");
	fieldGroup.bind(apellido2, "apellido2");
	fieldGroup.bind(fechaAlta, "fechaAlta");
	fieldGroup.bind(fechaBaja, "fechaBaja");
	fieldGroup.bind(fechaNacimiento, "fechaNacimiento");
	fieldGroup.bind(email, "email");
	fieldGroup.bind(telefono, "telefono");
	fieldGroup.bind(direccion, "direccion");
	
	form = new GridLayout(3, 4);
	form.addComponent(numSocio);
	form.addComponent(nif);
	form.addComponent(new Label(""));
	form.addComponent(nombre);
	form.addComponent(apellido1);
	form.addComponent(apellido2);
	form.addComponent(email);
	form.addComponent(telefono);
	form.addComponent(direccion);
	form.addComponent(fechaAlta);
	form.addComponent(fechaBaja);
	form.addComponent(fechaNacimiento);
	form.addComponent(save);
	form.addComponent(new Label(""));
	form.addComponent(cancel);
	form.addComponent(id);
	nuevo.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		editSocio(new Socio());
	    }

	});
	save.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		guardarSocio();
	    }

	});
	delete.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		ConfirmDialog.show(SociosListView.this.getUI(),
			bundle.getString("socios.confirmacion.borrar"),
			new ConfirmDialog.Listener() {

			    public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()){
				    borrarSocio();
				}
			    }
			});
	    }
	});

	// botones
	btnVolver = new Button("Volver");
	btnVolver.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainView.NAME);
	    }
	});
	mainLayout = new VerticalLayout(tableControls, table, form, btnVolver);
	mainLayout.setMargin(true);
	setCompositionRoot(mainLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
	log.debug("inicio");
	updateTable();
    }
    
    public void borrarSocio(){
	final Socio socio= (Socio) table.getValue();
	boolean borrado = socioDao.delete(socio);
	if (borrado) {
	    log.info("Borrado el socio"+ socio.getIdSocio());
	    updateTable();
	}
    }
    
    private void guardarSocio() {
	log.debug("guardar socio");
	try {
	    fieldGroup.commit();
	    Socio socio = ((BeanItem<Socio>) fieldGroup.getItemDataSource()).getBean();
	    User user = (User) getSession().getAttribute(GesclubContracts.SESSION_USER);
	    socio.setClub(user.getClub());
	    boolean nuevo = socio.getIdSocio()==null;
	    socioDao.save(socio);
	    if (nuevo){
		User nu = new User();
		nu.setClub(user.getClub());
		nu.setEsSocio(true);
		nu.setUsername(user.getClub().getNombre()+socio.getNumSocio());
		nu.setPassword(AppSecurity.getEncryptPassword(socio.getNif()));
		nu = userDao.save(nu);
		UserRole ur = new UserRole();
		ur.setRoleName(Role.SOCIO.name());
		ur.setUsername(nu.getUsername());
		ur = userDao.saveRole(ur);
		log.info("nuevo usuario "+nu);
	    }
	    updateTable();
	    editSocio(null);
	} catch (CommitException e) {
	    log.error("", e);
	}		
    }

    private void updateTable() {
	User user = (User) getSession().getAttribute(GesclubContracts.SESSION_USER);
	log.debug("obtenemos socios activos del club "+user.getClub().getNombre());
	Long clubId = user.getClub().getId();
	List<Socio> list = socioDao.getSociosActivos(clubId);
	BeanItemContainer<Socio> container = new BeanItemContainer<Socio>(
		Socio.class, list);
	table.setContainerDataSource(container);
	
	table.setVisibleColumns(new String[] { "numSocio", "nombreCompleto", "numeroSalidas"});
	table.setColumnHeaders(new String[] { "Numero", "Nombres", "Nº Salidas"});
	table.sort(new Object[] { "numSocio"}, new boolean[] {
		true, true });
	logeaClasSal();
    }
    
    private void logeaClasSal(){
	User user = (User) getSession().getAttribute(GesclubContracts.SESSION_USER);
	Long clubId = user.getClub().getId();
	ClasificacionSalidas cs = socioDao.getClasificacionSalidas(clubId, 2013, TipoSalida.ROAD_CLUB);
	StringBuilder sb = new StringBuilder("\n\r");
	for(ItemClasificacionSalidas item : cs.getClasificacion()){
	    sb.append(item.getPosicion())
	    .append("-").append(item.getNumeroSalidas())
	    .append("-").append(item.getNumSocio())
	    .append("-").append(item.getSocio())
	    .append("\n\r");
	}
	log.debug(sb.toString());
    }

    private void editSocio(Socio socio) {
	if (socio == null) {
	    socio = new Socio();
	}
	BeanItem<Socio> item = new BeanItem<Socio>(socio);
	fieldGroup.setItemDataSource(item);
    }

}
