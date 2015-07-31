import com.hydrabolt.titancast.javaapi.ApplicationIcon;
import com.hydrabolt.titancast.javaapi.TitanCastApplication;
import com.hydrabolt.titancast.javaapi.TitanCastDevice;
import com.hydrabolt.titancast.javaapi.utils.ConnectionCode;

import java.io.File;

/*

This is a test application and is used to test the API.

 */

public class MainActivity {

    static File f = new File( "C:/Users/Amish/Desktop/pikachu.png" );

    public static void main( String[] args ) {

        String code = "c0 a8 00 08";

        System.out.println("Starting PokeController...");
        System.out.println("Connection to '"+code+"'");

        TitanCastApplication myApplication = new TitanCastApplication(
                "Pokemon Controller",
                "Use a virtual controller to control emulators with Pokemon-styled mappings",
                "http://192.168.0.9:4000/developer/controller.html", //as of now this will 404
                new ApplicationIcon( f )
        );
        TitanCastDevice device = new MyDevice( myApplication, new ConnectionCode( code ) );

    }

}
