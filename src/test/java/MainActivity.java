import com.hydrabolt.titancast.javaapi.ApplicationIcon;
import com.hydrabolt.titancast.javaapi.TitanCastApplication;

/*

This is a test application and is used to test the API.

 */

public class MainActivity {

    public static void main( String[] args ) {

        TitanCastApplication myApplication = new TitanCastApplication(
                "TitanCast API Test Application",
                "Used to test the Java API",
                "http://titancast.github.io/apis/java/test", //as of now this will 404
                new ApplicationIcon()
        );


    }

}
