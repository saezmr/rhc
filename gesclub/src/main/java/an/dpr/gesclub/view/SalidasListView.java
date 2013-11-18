/**
 * 
 */
package an.dpr.gesclub.view;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import an.dpr.gesclub.GestionClubUI;
import an.dpr.gesclub.beans.Difficulty;
import an.dpr.gesclub.beans.TipoSalida;
import an.dpr.gesclub.contracts.MunicipioContracts;
import an.dpr.gesclub.contracts.SalidaContracts;
import an.dpr.gesclub.dao.MunicipiosDao;
import an.dpr.gesclub.dao.SalidasDao;
import an.dpr.gesclub.dao.SociosDao;
import an.dpr.gesclub.domain.Municipio;
import an.dpr.gesclub.domain.Salida;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.security.AppSecurity;
import an.dpr.gesclub.security.Permiso;
import an.dpr.gesclub.util.GesclubContracts;
import an.dpr.gesclub.util.I18nText;
import an.dpr.gesclub.util.UtilFecha;
import an.dpr.gesclub.util.ViewUtils;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author rsaez
 * 
 */
public class SalidasListView extends CustomComponent implements View {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(SalidasListView.class);
    public static final String NAME = "SalidasList";
    private ResourceBundle bundle = I18nText.getBundle();
    @Autowired SalidasDao dao;
    @Autowired MunicipiosDao municipiosDao;
    @Autowired SociosDao sociosDao;

    // componentes visuales
    private Label titulo;
    private Layout form;
    private FieldGroup fieldGroup;
    private DateField dfFecha;
    private TextField ruta;
    private TextField retorno;
    private TextField parada;
    private TextField km;
    private TextField altitud;
    private ComboBox cmbDificultad;
    private ComboBox cmbTipo;
    private ComboBox cmbAemet;
    private Button btnGuardar;
    private Button btnNuevo;
    private Button btnDelete;
    private Button btnAddSocios;
    private Table tabla;
    private Button btnVolver;
    private VerticalLayout mainLayout;
    private Layout filtros;
    private ComboBox filtroTipo;
    private ComboBox filtroMes;
    private ComboBox filtroAnyo;
    private boolean componentesIniciados;
    private TextField txSociosSalida;

    public SalidasListView() {
	log.debug("new instance");
    }

    private void initComponents() {
	log.debug("inicio");
	titulo = new Label(bundle.getString("salidas.titulo"));
	tabla = new Table(bundle.getString("salidas.tabla"));
	tabla.setImmediate(true);
	tabla.setSelectable(true);
	tabla.setWidth("100%");
	btnVolver = new Button(bundle.getString("salida.volver"));
	btnVolver.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		getUI().getNavigator().navigateTo(MainView.NAME);
	    }
	});
	
	addFiltrosBusqueda();
	
	if (AppSecurity.isPermited(Permiso.salidas_write)){
	    initComponentsWrite();
	} else if( AppSecurity.isPermited(Permiso.salidas_read)){
	    initComponentsRead();
	}
	    
	mainLayout.setMargin(true);
	setCompositionRoot(mainLayout);
	manejoErrores();
	componentesIniciados = true;
    }
    
    private void manejoErrores() {
//	btnGuardar.setComponentError(new UserError(bundle.getString("error.inesperado")));
//	btnNuevo.setComponentError(new UserError(bundle.getString("error.inesperado")));
//	btnVolver.setComponentError(new UserError(bundle.getString("error.inesperado")));
//	btnDelete.setComponentError(new UserError(bundle.getString("error.inesperado")));
    }

    private void addFiltrosBusqueda() {
	filtros = new HorizontalLayout();
	ValueChangeListener changeListener = new ValueChangeListener() {
	    
	    @Override
	    public void valueChange(ValueChangeEvent event) {
		updateTable();
	    }
	};
	
	filtroTipo = new ComboBox(bundle.getString("filtro.busqueda.tipo.salida"));
	filtroTipo.setImmediate(true);
	filtroTipo.setInvalidAllowed(true);
	filtroTipo.setNullSelectionAllowed(true);
	for(TipoSalida tipo :TipoSalida.values()){
	    filtroTipo.addItem(tipo);
	    filtroTipo.setItemCaption(tipo, tipo.toString());
	}
	filtroTipo.setValue(TipoSalida.ROAD_CLUB);
	filtroTipo.addValueChangeListener(changeListener);
	
	filtroAnyo = new ComboBox(bundle.getString("filtro.busqueda.anyo"));
	filtroAnyo.setImmediate(true);
	filtroAnyo.setInvalidAllowed(true);
	filtroAnyo.setNullSelectionAllowed(true);
	Integer anyoActual = Calendar.getInstance().get(Calendar.YEAR);
	Integer anyoInicial = 2000;
	for(int i= anyoActual; i>=anyoInicial; i--){
	    filtroAnyo.addItem(i);
	}
	filtroAnyo.setValue(anyoActual);
	filtroAnyo.addValueChangeListener(changeListener);

	filtroMes = new ComboBox(bundle.getString("filtro.busqueda.mes"));
	filtroMes.setImmediate(true);
	filtroMes.setInvalidAllowed(true);
	filtroMes.setNullSelectionAllowed(true);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
	for(int i= 0; i<12; i++){
	    filtroMes.addItem(i);
	    filtroMes.setItemCaption(i,months[i]);
	}
	filtroMes.addValueChangeListener(changeListener);
	
	filtros.addComponent(filtroTipo);
	filtros.addComponent(filtroMes);
	filtros.addComponent(filtroAnyo);
    }
    
    private void initComponentsWrite(){
	log.debug("inicio");
	tabla.addValueChangeListener(new ValueChangeListener(){
	    
	    @Override
	    public void valueChange(ValueChangeEvent event) {
		Salida salida = (Salida)tabla.getValue();
		editSalida(salida);
	    }
	    
	});
	createForm();
	mainLayout = new VerticalLayout(titulo, form, filtros, tabla, btnVolver);
	nuevaSalida();
    }
    
    private void initComponentsRead(){
	log.debug("inicio");
	mainLayout = new VerticalLayout(titulo, filtros, tabla, btnVolver);
    }

    private void createForm() {
	log.debug("inicio");
	fieldGroup = new FieldGroup();
	createFieldsFormulario();
	botonesFormulario();

	fieldGroup.bind(dfFecha, "date");
	fieldGroup.bind(ruta, "route");
	fieldGroup.bind(retorno, "returnRoute");
	fieldGroup.bind(parada, "stop");
	fieldGroup.bind(km, "km");
	fieldGroup.bind(altitud, "elevationGain");
	fieldGroup.bind(cmbDificultad, "difficulty");
	fieldGroup.bind(cmbTipo, "tipo");
	fieldGroup.bind(cmbAemet, "aemetCode");

	form = new VerticalLayout();
	HorizontalLayout f1 = new HorizontalLayout();
	HorizontalLayout f2 = new HorizontalLayout();
	HorizontalLayout f3 = new HorizontalLayout();
	f1.addComponent(dfFecha);
	f1.addComponent(km);
	f1.addComponent(ruta);
	f1.addComponent(parada);
	f1.addComponent(retorno);
	f2.addComponent(altitud);
	f2.addComponent(cmbDificultad);
	f2.addComponent(cmbTipo);
	f2.addComponent(cmbAemet);
	f3.addComponent(btnGuardar);
	f3.addComponent(btnNuevo);
	f3.addComponent(btnDelete);
	f3.addComponent(btnAddSocios);
	form.addComponent(f1);
	form.addComponent(f2);
	form.addComponent(f3);
	
    }
    
    private void createFieldsFormulario(){

	dfFecha = new PopupDateField(bundle.getString("salida.date"));
	dfFecha.setDateFormat("dd/MM/yyyy HH:mm");
	dfFecha.setResolution(Resolution.MINUTE);
	dfFecha.setWidth("16em");
	dfFecha.setRequired(true);
	dfFecha.setRequiredError(bundle.getString("salida.date.obligatorio"));

	ruta = new TextField(bundle.getString("salida.route"));
	ruta.setNullRepresentation("");
	ruta.setMaxLength(100);
	ruta.setRequired(true);
	ruta.setRequiredError(bundle.getString("salida.route.obligatorio"));

	retorno = new TextField(bundle.getString("salida.return.route"));
	retorno.setNullRepresentation("");
	retorno.setMaxLength(20);

	parada = new TextField(bundle.getString("salida.stop"));
	parada.setNullRepresentation("");
	parada.setMaxLength(100);
	parada.setRequired(true);
	parada.setRequiredError(bundle.getString("salida.stop.obligatorio"));
	
	km = new TextField(bundle.getString("salida.km"));
	km.setNullRepresentation("");
	km.setConverter(Double.class);
	km.setRequired(true);
	km.setRequiredError(bundle.getString("salida.km.obligatorio"));

	altitud = new TextField(bundle.getString("salida.elevation.gain"));
	altitud.setNullRepresentation("");
	altitud.setConverter(Integer.class);

	cmbDificultad = new ComboBox(bundle.getString("salida.dificultad"));
	cmbDificultad.setInvalidAllowed(true);
	cmbDificultad.setNullSelectionAllowed(true);
	for(Difficulty dif :Difficulty.values()){
	    cmbDificultad.addItem(dif.name());
	    cmbDificultad.setItemCaption(dif.name(), dif.toString());
	}
	cmbDificultad.setRequired(true);
	cmbDificultad.setRequiredError(bundle.getString("salida.dificultad.obligatorio"));
	
	cmbAemet = new ComboBox(bundle.getString("salida.aemet"));
	cmbAemet.setInvalidAllowed(false);
	cmbAemet.setNullSelectionAllowed(false);
	List<Municipio> municipios = municipiosDao.findByProvincia(MunicipioContracts.CODIGO_PROVINCIA_ZARAGOZA);
	for(Municipio municipio : municipios){
	    cmbAemet.addItem(municipio.getIneCode());
	    cmbAemet.setItemCaption(municipio.getIneCode(), municipio.getNombre());
	}
	cmbAemet.setRequired(true);
	cmbAemet.setRequiredError(bundle.getString("salida.aemet.obligatorio"));

	cmbTipo = new ComboBox(bundle.getString("salida.tipo"));
	cmbTipo.setInvalidAllowed(false);
	cmbTipo.setNullSelectionAllowed(true);
	for(TipoSalida ts :TipoSalida.values()){
	    cmbTipo.addItem(ts.name());
	    cmbTipo.setItemCaption(ts.name(), ts.toString());
	}
	cmbTipo.setRequired(true);
	cmbTipo.setRequiredError(bundle.getString("salida.tipo.obligatorio"));

    }
    
    private void botonesFormulario(){
	btnGuardar = new Button(bundle.getString("salida.guardar"));
	btnGuardar.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		guardarSalida();
	    }
	});
	btnNuevo = new Button(bundle.getString("salida.nuevo"));
	btnNuevo.addClickListener(new ClickListener() {
	    
	    @Override
	    public void buttonClick(ClickEvent event) {
		nuevaSalida();
	    }
	});
	btnDelete= new Button(bundle.getString("salida.borrar"));
	btnDelete.addClickListener(new ClickListener() {

	    @Override
	    public void buttonClick(ClickEvent event) {
		// The quickest way to confirm
		ConfirmDialog.show(SalidasListView.this.getUI(),
			bundle.getString("salida.confirmacion.borrar"),
			new ConfirmDialog.Listener() {
		    
		    public void onClose(ConfirmDialog dialog) {
			if (dialog.isConfirmed()){
			    borrarSalida();
			}
		    }
		});
	    }

	});
	btnAddSocios= new Button(bundle.getString("salida.add.socios"));
	btnAddSocios.addClickListener(new ClickListener() {
	    
	    @Override
	    public void buttonClick(ClickEvent event) {
		addSocios();
	    }
	});
    }

    @Override
    public void enter(ViewChangeEvent event) {
	log.debug("inicio");
	if (componentesIniciados){
	    updateTable();
	    if (AppSecurity.isPermited(Permiso.salidas_write)) {
		editSalida(new Salida());
	    }
	} else 	if (AppSecurity.isPermited(Permiso.salidas_write)
		|| AppSecurity.isPermited(Permiso.salidas_read)){
	    initComponents();
	    updateTable();
	} else {
	    Label lbl = new Label("no tienes permisos para acceder a esta pantalla");
	    setCompositionRoot(lbl);
	}
    }
    
    private void addSocios() {
	Window win = new Window(bundle.getString("salida.window.socios.title"));
	VerticalLayout subContent = new VerticalLayout();
	txSociosSalida = new TextField(bundle.getString("salida.window.socios.textfield"));
	txSociosSalida.setNullRepresentation("");
	txSociosSalida.setMaxLength(1000);
	txSociosSalida.setRequired(true);
	Button btn = new Button(bundle.getString("salida.window.socios.button"));
	btn.addClickListener(new ClickListener(){
	    @Override
	    public void buttonClick(ClickEvent event) {
		guardarSociosSalida();
	    }});
	subContent.addComponent(txSociosSalida);
	subContent.addComponent(btn);
	subContent.setMargin(true);
	win.setContent(subContent);
	getUI().addWindow(win);
    }
    
    private void guardarSociosSalida() {
	Salida salida = ((BeanItem<Salida>) fieldGroup.getItemDataSource())
		.getBean();
	for (Socio socio : getSociosIntroducidos()) {
	    salida.getSocios().add(socio);
	}
	dao.save(salida);
	updateTable();
	editSalida(new Salida());
    }
    
    private Set<Socio> getSociosIntroducidos() {
	Set<Socio> ret = new HashSet<Socio>();
	String texto = txSociosSalida.getValue();
	StringBuilder errores = new StringBuilder();
	if (texto != null) {
	    String[] nums = texto.split(",");
	    for(String num : nums){
		try{
		    Socio socio = sociosDao.getSocioByNumSocio(Integer.valueOf(num.trim()));
		    if (socio != null){
			ret.add(socio);
		    } else {
			errores.append(num).append("; ");
		    }
		} catch(Exception e){
		    log.error("num socio "+num+" invalido",e);
		    errores.append(num).append("; ");
		}
	    }
	}
	if (errores.length() > 0){
	    String intro = bundle.getString("error.numeros.socio.salida");
	    errores.append(intro,0, intro.length());
	    ViewUtils.mostrarAviso(errores.toString());
	}
	return ret;
    }

    private void guardarSalida(){
	try {
	    fieldGroup.commit();
	    Salida salida = ((BeanItem<Salida>) fieldGroup.getItemDataSource())
		    .getBean();
	    User user = (User) getSession().getAttribute(GesclubContracts.SESSION_USER);
	    salida.setClub(user.getClub());
	    dao.save(salida);
	    updateTable();
	    editSalida(new Salida());
	} catch (CommitException e) {
	    log.debug("Error obteniendo informacion del formulario", e);
	}
    }
    
    private void borrarSalida() {
	final Salida salida = (Salida) tabla.getValue();
	boolean borrado = dao.delete(salida);
	if (borrado) {
	    log.info("Borrada la salida "
		    + salida.getIdSalida());
	    updateTable();
	}
    }
    
    private void nuevaSalida(){
	editSalida(new Salida());
	cmbAemet.setValue(MunicipioContracts.CODIGO_INE_ZARAGOZA);
	cmbDificultad.setValue(Difficulty.MEDIUM.name());
	cmbTipo.setValue(TipoSalida.ROAD_CLUB.name());
    }

    private void updateTable() {
	log.debug("inicio");
	List<Salida> list = getListadoTabla();
	
	BeanItemContainer<Salida> container = new BeanItemContainer<Salida>(
		Salida.class, list);
	tabla.setContainerDataSource(container);

	tabla.setVisibleColumns(new String[] { 
		SalidaContracts.COLUMN_DATE,
		SalidaContracts.COLUMN_ROUTE,
		SalidaContracts.COLUMN_RETURN_ROUTE,
		SalidaContracts.COLUMN_STOP, SalidaContracts.COLUMN_KM,
		SalidaContracts.COLUMN_ELEVATION_GAIN,
		SalidaContracts.COLUMN_DIFFICULTY_PRESENTACION,
		SalidaContracts.COLUMN_TIPO_PRESENTACION });

	tabla.setColumnHeaders(new String[] {
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_DATE),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_ROUTE),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_RETURN_ROUTE),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_STOP),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_KM),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_ELEVATION_GAIN),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_DIFICULTAD),
		bundle.getString(SalidaContracts.CK_SALIDA_LIST_TIPO) });
	tabla.sort(new Object[] { SalidaContracts.COLUMN_DATE }, new boolean[] { true, true });

	tabla.setConverter(SalidaContracts.COLUMN_DATE, new Converter<String, Date>(){

	    @Override
	    public Class<Date> getModelType() {
		// TODO Auto-generated method stub
		return Date.class;
	    }

	    @Override
	    public Class<String> getPresentationType() {
		// TODO Auto-generated method stub
		return String.class;
	    }

	    @Override
	    public Date convertToModel(String value, Locale locale)
		    throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public String convertToPresentation(Date value, Locale locale)
		    throws com.vaadin.data.util.converter.Converter.ConversionException {
		return UtilFecha.format(value);
	    }});
    }

    private List<Salida> getListadoTabla() {
	List<Salida> list = null;
	TipoSalida tipo = (TipoSalida) filtroTipo.getValue();
	User user = (User) getSession().getAttribute(
		GesclubContracts.SESSION_USER);
	Integer anyo = (Integer) filtroAnyo.getValue();
	Integer mes = (Integer) filtroMes.getValue();
	
	log.debug("Filtro tipo salida seleccionado:" + tipo);
	log.debug("Filtro anyo seleccionado:" + anyo);
	log.debug("Filtro mes seleccionado:" + mes);
	
	Date fechaIni = null;
	Date fechaFin = null;
	if (anyo != null && mes != null) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, anyo);
	    c.set(Calendar.MONTH, mes);
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    fechaIni = c.getTime();
	    c.add(Calendar.MONTH, 1);
	    fechaFin = c.getTime();
	} else if (anyo != null ){
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, anyo);
	    c.set(Calendar.MONTH, 1);
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    fechaIni = c.getTime();
	    c.add(Calendar.YEAR, 1);
	    fechaFin = c.getTime();
	}
	
	if (tipo != null && fechaIni != null) {
	    list = dao.findByDateAndTipo(fechaIni, fechaFin, tipo.name(), user.getClub().getId());
	} else if (tipo != null ) {
	    list = dao.findByTipo(tipo.name(), user.getClub());
	} else if (fechaIni != null){
	    list = dao.findByDate(fechaIni, fechaFin, user.getClub().getId());
	} else {
	    list = dao.findAll(user.getClub());
	}
	return list;
    }
    
    private void editSalida(Salida salida) {
	log.debug("inicio");
	if (salida == null) {
	    salida = new Salida();
	}
	BeanItem<Salida> item = new BeanItem<Salida>(salida);
	fieldGroup.setItemDataSource(item);
	dfFecha.focus();
    }
}
