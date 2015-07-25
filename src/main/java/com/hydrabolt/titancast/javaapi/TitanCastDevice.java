package com.hydrabolt.titancast.javaapi;

import com.hydrabolt.titancast.javaapi.utils.ConnectionCode;
import com.hydrabolt.titancast.javaapi.utils.Packet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;

/**
 * Class representing the connection logic of a local application connecting to an android device. It should be
 * overwritten with an own class and the relevant onXYZ method overridden with own logic.
 */
public abstract class TitanCastDevice extends WebSocketClient implements TitanCastDeviceInterface {

    private TitanCastApplication application;
    private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;

    public TitanCastDevice( TitanCastApplication application, ConnectionCode code ) {
        super( code.getURI() );
        this.application = application;
    }

    public TitanCastApplication getApplication() {
        return this.application;
    }

    public void setApplication( TitanCastApplication application ) {
        this.application = application;
    }

    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    public void onMessage( String s ) {
        Packet packet = new Packet( s );
        switch (connectionState) {
            case NOT_CONNECTED:
                if (packet.getType().equals( "device_details" )) {
                    sendPacket( new Packet( "request_connect", new String[]{
                            application.getAppName(),
                            application.getAppDesc(),
                            application.getApplicationIcon().getImage()
                    } ) );
                    onDeviceDetails( packet.getData() );
                    connectionState = ConnectionState.AWAITING_RESPONSE;
                    onSentRequest();
                    break;
                }
                break;
            case AWAITING_RESPONSE:
                if (packet.getType().equals( "accept_connect_request" )) {
                    sendPacket( new Packet( "cast_view_data", application.getAppCastURL() ) );
                    connectionState = ConnectionState.CONNECTED;
                    onConnectAccept();
                }
                if (packet.getType().equals( "reject_connect_request" )) {
                    connectionState = ConnectionState.NOT_CONNECTED;
                    onConnectReject();
                    close();

                }
                break;
            case CONNECTED:
                //use switch here are there are many more options
                switch (packet.getType()) {
                    case "custom_data":
                        onCustomData( packet.getData() );
                        break;
                    case "accelerometer-update":
                        System.out.println( packet.getData() );
                        ArrayList<String> data = packet.getData();
                        onAccelerometerData(
                                Float.parseFloat( data.get( 0 ) ),
                                Float.parseFloat( data.get( 1 ) ),
                                Float.parseFloat( data.get( 2 ) )
                        );
                        break;
                    case "view-loaded":
                        onViewLoaded();
                        break;
                    default:
                        break;

                }
                break;
        }

    }

    public void sendPacket( Packet packet ) {
        send( packet.serialize() );
    }

    public void onOpen( ServerHandshake handshakeData ) {
        onConnectionOpen( handshakeData );
    }

    public void onError( Exception e ) {
        onConnectionError( e );
        onConnectionEnd( e.getLocalizedMessage() );
    }

    public void onClose( int code, String reason, boolean remote ) {
        onConnectionClose( code, reason, remote );
        onConnectionEnd( reason );
    }

    public void onConnectionOpen( ServerHandshake handshakeData ) {
    }

    public void onConnectionError( Exception e ) {
    }

    public void onConnectionClose( int code, String reason, boolean remote ) {
    }

    public void onAccelerometerData( float x, float y, float z ) {
    }

    public void enableAccelerometer() {
        sendPacket( new Packet( "enable_accelerometer" ) );
    }

    public void disableAccelerometer() {
        sendPacket( new Packet( "disable_accelerometer" ) );
    }

    public void setOrientation( String orientation ) {
        sendPacket( new Packet( "set_orientation", orientation ) );
    }

    public void setAccelerometerSpeed( String speed ) {
        sendPacket( new Packet( "set_accelerometer_speed", speed ) );
    }

}