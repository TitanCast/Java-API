import com.hydrabolt.titancast.javaapi.ApplicationIcon;
import com.hydrabolt.titancast.javaapi.TitanCastApplication;
import com.hydrabolt.titancast.javaapi.TitanCastDevice;
import com.hydrabolt.titancast.javaapi.utils.ConnectionCode;

/*

This is a test application and is used to test the API.

 */

public class MainActivity {

    public static void main( String[] args ) {
        TitanCastApplication myApplication = new TitanCastApplication(
                "TitanCast API Test Application",
                "Used to test the Java API",
                "http://titancast.github.io/developer/java-controller.html", //as of now this will 404
                new ApplicationIcon()
        );
        TitanCastDevice device = new MyDevice( myApplication, new ConnectionCode( "c0 a8 00 08" ) );
        device.connect();

    }

}
