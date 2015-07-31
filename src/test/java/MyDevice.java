import com.hydrabolt.titancast.javaapi.TitanCastApplication;
import com.hydrabolt.titancast.javaapi.TitanCastDevice;
import com.hydrabolt.titancast.javaapi.utils.ConnectionCode;
import com.hydrabolt.titancast.javaapi.utils.DeviceDetails;
import com.sun.deploy.util.ArrayUtil;
import org.java_websocket.handshake.ServerHandshake;

import java.awt.event.KeyEvent;
import java.net.URI;
import java.security.Key;
import java.util.ArrayList;

public class MyDevice extends TitanCastDevice {

    URI myAddress;

    public MyDevice(TitanCastApplication application, ConnectionCode code) {

        super(application, code);
        myAddress = code.getURI();

    }

    @Override
    public void onConnectionError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConnectionClose(int code, String reason, boolean remote) {
        System.out.println("Connection to " + myAddress + " closed.");
    }

    @Override
    public void onConnectAccept() {
        System.out.println("Connection to " + myAddress + " accepted.");

    }

    @Override
    public void onConnectReject() {
        System.out.println("Connection to " + myAddress + " rejected.");
    }

    @Override
    public void onDeviceDetails(DeviceDetails details) {

        System.out.println(details.constructDeviceName() + " is running on Android " + details.getAndroidVersion());

    }

}
