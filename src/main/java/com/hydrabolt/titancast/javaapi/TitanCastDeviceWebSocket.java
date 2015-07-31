package com.hydrabolt.titancast.javaapi;

import com.hydrabolt.titancast.javaapi.utils.ConnectionState;
import com.hydrabolt.titancast.javaapi.utils.DeviceDetails;
import com.hydrabolt.titancast.javaapi.utils.Packet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;

public class TitanCastDeviceWebSocket extends WebSocketClient {

    private TitanCastDevice device;

    public TitanCastDeviceWebSocket(URI serverURI, TitanCastDevice device) {
        super(serverURI);
        this.device = device;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {

        device.onConnectionOpen( handshakeData );

    }

    @Override
    public void onMessage(String message) {

        Packet packet = new Packet( message );

        switch (device.getConnectionState()) {
            case NOT_CONNECTED:

                TitanCastApplication application = device.getApplication();

                if (packet.getType().equals( "device_details" )) {
                    sendPacket( new Packet( "request_connect", new String[]{
                            application.getAppName(),
                            application.getAppDesc(),
                            application.getApplicationIcon().getImage()
                    } ) );

                    device.setDeviceDetails(new DeviceDetails(packet.getData()));

                    device.onDeviceDetails( device.getDeviceDetails() );
                    device.setConnectionState(ConnectionState.AWAITING_RESPONSE);
                    device.onSentRequest();
                    break;
                }
                break;
            case AWAITING_RESPONSE:
                if (packet.getType().equals( "accept_connect_request" )) {
                    sendPacket( new Packet( "cast_view_data", device.getApplication().getAppCastURL() ) );
                    device.setConnectionState(ConnectionState.CONNECTED);
                    device.onConnectAccept();
                }
                if (packet.getType().equals( "reject_connect_request" )) {
                    device.setConnectionState(ConnectionState.NOT_CONNECTED);
                    device.onConnectReject();
                    close();

                }
                break;
            case CONNECTED:
                //use switch here are there are many more options
                switch (packet.getType()) {
                    case "custom_data":
                        device.onCustomData( packet.getData() );
                        break;
                    case "accelerometer-update":
                        System.out.println( packet.getData() );
                        ArrayList<String> data = packet.getData();
                        device.onAccelerometerData(
                                Float.parseFloat( data.get( 0 ) ),
                                Float.parseFloat( data.get( 1 ) ),
                                Float.parseFloat( data.get( 2 ) )
                        );
                        break;
                    case "view-loaded":
                        device.onViewLoaded();
                        break;
                    default:
                        break;

                }
                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        device.onConnectionClose( code, reason, remote );
    }

    @Override
    public void onError(Exception e) {
        device.onConnectionError(e);
    }

    public void sendPacket(Packet packet){
        this.send(packet.serialize());
    }
}
