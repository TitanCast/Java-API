package com.hydrabolt.titancast.java_api;

import com.hydrabolt.titancast.java_api.utils.Packet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Device extends WebSocketClient {

    private TitanCastApplication application;

    public Device(URI uri, TitanCastApplication application){
        super(uri);
        this.application = application;
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {

    }

    @Override
    public void onMessage( String message ) {

        Packet p = new Packet(message);

    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {

    }

    @Override
    public void onError( Exception ex ) {

    }
}
