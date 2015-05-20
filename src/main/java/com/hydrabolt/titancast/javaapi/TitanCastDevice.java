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
public abstract class TitanCastDevice extends WebSocketClient {
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
    public final void onMessage(String rawMessage) {
        // Create new packet with the raw data provided
        Packet packet = new Packet(rawMessage);

        // Find out what type of packet has been sent over
        switch (packet.getType()) {
            case "device_details":
                // Details of the device have been sent, so start a connection request
                if (connectionState == ConnectionState.notConnected) {
                    sendRawPacket(new Packet("request_connect", new String[]{
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
                    sendRawPacket(new Packet("cast_view_data", new String[]{
                            application.getAppCastURL()
                    }));

                    // Make it clear that we're connected
                    connectionState = ConnectionState.connected;
                    onConnectionRequestAccepted();
                }
                break;
            case "reject_connect_request":
                // Aww, it hasn't been accepted
                if (connectionState == ConnectionState.awaitingResponse) {
                    // Make it clear that we're not connected
                    connectionState = ConnectionState.notConnected;
                    onConnectionRequestRejected();
                }
                break;
            case "custom_data":
                // We received a packet with custom data, so obtain the data...
                String serializedPacketData = packet.getDataAsString();

                // ...deserialize it...
                String deserializedPacketData = StringUtils.newStringUtf8(
                        Base64.decodeBase64(serializedPacketData.getBytes()));

                // ...make a Packet...
                Packet customPacket = new Packet(deserializedPacketData, true);

                // ...and call onCustomPacketReceived with the new packet
                onCustomPacketReceived(customPacket);
                break;
        }
    }

    private void sendRawPacket(Packet packet) {
        send(packet.serialize());
    }

    /**
     * Sends a custom packet by serializing and encoding it and wrapping it in a custom_data packet.
     * @param packet The custom packet that should be sent, not serialized or encoded in any way.
     */
    public void sendPacket(Packet packet) {
        // To send a custom packet, we have to serialize it...
        String serializedPacket = packet.serialize();

        // ...create a new custom_data packet with the serialized packet as the data...
        Packet customPacket = new Packet("custom_data", new String[]{serializedPacket});

        // ...and send it
        sendRawPacket(customPacket);
    }

    /**
     * Method that is called if a previous connection request to an android device has been accepted. Should be
     * overridden by an application developer.
     */
    public void onConnectionRequestAccepted() {

    }

    /**
     * Method that is called if a previous connection request to an android device has been rejected. Should be
     * overridden by an application developer.
     */
    public void onConnectionRequestRejected() {

    }

    /**
     * Method that is called if a custom packet (i.e. one that is not handled as a special case in onMessage) is
     * received from the android device. Should be overwritten by a developer.
     *
     * @param customPacket The packet that has been received, properly deserialized and decoded.
     */
    public void onCustomPacketReceived(Packet customPacket) {

    }
}
