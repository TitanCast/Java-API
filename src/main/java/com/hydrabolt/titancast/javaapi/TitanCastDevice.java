package com.hydrabolt.titancast.javaapi;

import com.hydrabolt.titancast.javaapi.utils.Packet;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
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
    public final void onMessage(String rawMessage) {
        // Create new packet with the raw data provided
        Packet packet = new Packet(rawMessage);

        // Find out what type of packet has been sent over
        switch (packet.getType()) {
            case "device_details":
                // Details of the device have been sent, so start a connection request
                if (connectionState == ConnectionState.notConnected) {
                    sendPacket(new Packet("request_connect", new String[]{
                            application.getAppName(),
                            application.getAppDesc(),
                            application.getIcon().getImage()
                    }));

                    // Make it clear that we're currently awaiting a response
                    connectionState = ConnectionState.awaitingResponse;
                }
                break;
            case "accept_connect_request":
                // Yay, our request has been accepted
                if (connectionState == ConnectionState.awaitingResponse) {
                    // Send a packet to the android device to make it load the cast page
                    sendPacket(new Packet("cast_view_data", new String[]{
                            application.getAppCastURL()
                    }));

                    // Make it clear that we're connected
                    connectionState = ConnectionState.connected;
                    accepted();
                }
                break;
            case "reject_connect_request":
                // Aww, it hasn't been accepted
                if (connectionState == ConnectionState.awaitingResponse) {
                    connectionState = ConnectionState.notConnected;
                    rejected();
                }
                break;
            case "custom_data":
                customData(new Packet(StringUtils.newStringUtf8(Base64.decodeBase64(packet.getDataAsString().getBytes())), true));
                break;
            default:
                System.out.println(packet.getType());
                break;
        }
    }

    public void sendPacket(Packet p) {
        send(p.serialize());
    }
}
