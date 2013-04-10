package an.dpr.util;

import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        pruebaI18n();
    }
    
    private static void pruebaI18n(){
	System.out.println(I18n.getText("customer.name"));
    }
}
