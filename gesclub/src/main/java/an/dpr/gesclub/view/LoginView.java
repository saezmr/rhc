package an.dpr.gesclub.view;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.gesclub.dao.SociosDao;
import an.dpr.gesclub.dao.UsersDao;
import an.dpr.gesclub.domain.Socio;
import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.jpa.repository.UsersRepository;
import an.dpr.gesclub.security.AppSecurity;
import an.dpr.gesclub.util.GesclubContracts;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Vista de login de la aplicacion
 * @author rsaez
 *
 */
public class LoginView extends CustomComponent implements View,
        Button.ClickListener {
    
    private static final Logger log = Logger.getLogger(LoginView.class);

    public static final String NAME = "login";
    
    @Autowired UsersDao usersDao;

    private final TextField user;
    private final PasswordField password;
    private final Button loginButton;

    public LoginView() {
        setSizeFull();

        // Create the user input field
        user = new TextField("User:");
        user.setWidth("300px");
        user.setRequired(true);
        user.setInputPrompt("Your username ");
//        user.addValidator(new EmailValidator("Username must be an email address"));
        user.setInvalidAllowed(false);

        // Create the password input field
        password = new PasswordField("Password:");
        password.setWidth("300px");
        password.addValidator(new PasswordValidator());
        password.setRequired(true);
        password.setValue("");
        password.setNullRepresentation("");

        // Create login button
        loginButton = new Button("Login", this);

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setCaption("Please login to access the application. ");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }
    
    @Override
    public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        user.focus();
    }

    //
    // Validator for validating the passwords
    //
    private static final class PasswordValidator extends
            AbstractValidator<String> {

        public PasswordValidator() {
            super("The password provided is not valid");
        }

        @Override
        protected boolean isValidValue(String value) {
            //
            // Password must be at least 8 characters long and contain at least
            // one number
            //
            if (value != null
                    && (value.length() < 8 || !value.matches(".*\\d.*"))) {
                return false;
            }
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {

         //
         // Validate the fields using the navigator. By using validors for the
         // fields we reduce the amount of queries we have to use to the database
         // for wrongly entered passwords
         //
//        if (!user.isValid() || !password.isValid()) {
//            return;
//        }

        String username = user.getValue();
        String plainPass = this.password.getValue();
        String password = AppSecurity.getEncryptPassword(plainPass);

         //
         // Validate username and password with database here. For examples sake
         // I use a dummy username and password.
         //
        boolean loginSuccessful  = AppSecurity.login(username, password);
        User user = null;
        if (loginSuccessful){
            user = usersDao.getByUsername(username);
            log.debug("user:"+user);
        }

        if(user!= null){
//             Store the current user in the service session
            getSession().setAttribute(GesclubContracts.SESSION_USER, user);
            getSession().setAttribute("user", username);

            // Navigate to main view
            getUI().getNavigator().navigateTo(MainView.NAME);

        } else {

            // Wrong password clear the password field and refocuses it
            this.password.setValue(null);
            this.password.focus();
        }
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }
    
    
}