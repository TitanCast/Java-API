import com.hydrabolt.titancast.javaapi.TitanCastApplication;
import com.hydrabolt.titancast.javaapi.TitanCastDevice;
import com.hydrabolt.titancast.javaapi.utils.ConnectionCode;

import java.util.ArrayList;

public class MyDevice extends TitanCastDevice {

    public MyDevice( TitanCastApplication application, ConnectionCode code ) {
        super( application, code );
    }

    @Override
    public void onConnectionEnd( String reason ) {
    }

    @Override
    public void onSentRequest() {
    }

    @Override
    public void onConnectAccept() {
    }

    @Override
    public void onConnectReject() {
    }

    @Override
    public void onCustomData( ArrayList<String> data ) {
    }

    @Override
    public void onViewLoaded() {
    }

    @Override
    public void onDeviceDetails( ArrayList<String> details ) {
    }

    @Override
    public void onAccelerometerData( float x, float y, float z ) {
    }
}
