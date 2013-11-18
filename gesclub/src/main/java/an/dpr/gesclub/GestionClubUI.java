package an.dpr.gesclub;

import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.view.ClasificacionSalidasView;
import an.dpr.gesclub.view.LoginView;
import an.dpr.gesclub.view.LogoutView;
import an.dpr.gesclub.view.MainView;
import an.dpr.gesclub.view.SalidasListView;
import an.dpr.gesclub.view.SociosListView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class GestionClubUI extends UI
{
    @Autowired LoginView loginView;
    @Autowired MainView mainView;
    @Autowired LogoutView logoutView;
    @Autowired SociosListView sociosListView;
    @Autowired SalidasListView salidasListView;
    @Autowired ClasificacionSalidasView clasificacionSalidasView;

    @Override
    protected void init(VaadinRequest request) {


        //
        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        //
        new Navigator(this, this);

        addViews();
                       
        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {	
                
                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof LoginView;
                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                
            }
        });
        
    }

    /**
    // The initial log view where the user can login to the application
    */
    private void addViews() {
        getNavigator().addView(LoginView.NAME, loginView);//LoginView.class);
        getNavigator().addView(MainView.NAME,mainView);//MainView.class);
        getNavigator().addView(LogoutView.NAME, logoutView);//LogoutView.class);
        getNavigator().addView(SociosListView.NAME, sociosListView);//SociosListView.class);
        getNavigator().addView(SalidasListView.NAME, salidasListView);	
        getNavigator().addView(ClasificacionSalidasView.NAME, clasificacionSalidasView);	
    }

//    public void manejoErrores(final Layout layout) {
//	ErrorHandler errorHandler = new DefaultErrorHandler() {
//	    @Override
//	    public void error(com.vaadin.server.ErrorEvent event) {
//	        // Find the final cause
//	        String cause = "<b>The click failed because:</b><br/>";
//	        for (Throwable t = event.getThrowable(); t != null;
//	             t = t.getCause())
//	            if (t.getCause() == null) // We're at final cause
//	                cause += t.getClass().getName() + "<br/>";
//	        
//	        // Display the error message in a custom fashion
//	        layout.addComponent(new Label(cause, ContentMode.HTML));
//	           
//	        // Do the default error handling (optional)
//	        doDefault(event);
//	    } 
//	};	
//	UI.getCurrent().setErrorHandler(errorHandler);
//    }
}
