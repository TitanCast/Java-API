package com.hydrabolt.titancast.javaapi;

import com.hydrabolt.titancast.javaapi.utils.Packet;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Class representing the connection logic of a local application connecting to an android device. It should be
 * overwritten with an own class and the relevant onXYZ method overridden with own logic.
 */
public class TitanCastDevice extends WebSocketClient {
    /**
     * Enum representing the current connectivity state the Device is in.
     */
    public enum ConnectionState {
        /**
         * The device is currently not connected to a peer and no request has been sent.
         */
        notConnected,
        /**
         * A request has been sent, but no connection has been established yet.
         */
        awaitingResponse,
        /**
         * The device is connected to a peer.
         */
        connected
    }

    /**
     * Variable representing the local application for which this class does the connection logic
     */
    private TitanCastApplication application;

    /**
     * The connection state the device is currently in
     */
    private ConnectionState connectionState;

    public TitanCastDevice(URI uri, TitanCastApplication application) {
        super(uri);
        this.application = application;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

        Packet p = new Packet(message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
