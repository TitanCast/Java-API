package com.hydrabolt.titancast.javaapi;

import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;

public interface TitanCastDeviceInterface {

    public abstract TitanCastApplication getApplication();
    public abstract ConnectionState getConnectionState();
    public abstract void onConnectionOpen( ServerHandshake handshakeData );
    public abstract void onConnectionEnd( String reason );
    public abstract void onConnectionError( Exception e );
    public abstract void onConnectionClose( int code, String reason, boolean remote );
    public abstract void onSentRequest();
    public abstract void onConnectAccept();
    public abstract void onConnectReject();
    public abstract void onCustomData( ArrayList<String> data );
    public abstract void onAccelerometerData( float x, float y, float z );
    public abstract void onViewLoaded();
    public abstract void onDeviceDetails( ArrayList<String> details );
    public abstract void enableAccelerometer();
    public enum ConnectionState {
        CONNECTED, NOT_CONNECTED, AWAITING_RESPONSE
    }

}
