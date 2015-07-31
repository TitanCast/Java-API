package com.hydrabolt.titancast.javaapi;

import com.hydrabolt.titancast.javaapi.utils.*;
import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class representing the connection logic of a local application connecting to an android device. It should be
 * overwritten with an own class and the relevant onXYZ method overridden with own logic.
 */
public abstract class TitanCastDevice {

    private TitanCastApplication application;
    private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;
    private TitanCastDeviceWebSocket webSocket;
    private ConnectionCode connectionCode;
    private DeviceDetails deviceDetails;

    public TitanCastDevice(TitanCastApplication application, ConnectionCode code) {
        this.setApplication(application);
        this.setConnectionCode(code);

        webSocket = new TitanCastDeviceWebSocket(code.getURI(), this);

        try {

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, TitanCastSSL.getTrustCert(), new java.security.SecureRandom());
            webSocket.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));

        } catch (Exception e) {

            onConnectionError(e);

        }

        webSocket.connect();

    }

    public TitanCastApplication getApplication() {
        return this.application;
    }

    public void setApplication(TitanCastApplication application) {
        this.application = application;
    }

    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public void sendPacket(Packet packet) {
        webSocket.send(packet.serialize());
    }

    public void enableAccelerometer() {
        sendPacket(new Packet("enable_accelerometer"));
    }

    public void disableAccelerometer() {
        sendPacket(new Packet("disable_accelerometer"));
    }

    public void setOrientation(String orientation) {
        sendPacket(new Packet("set_orientation", orientation));
    }

    public void setAccelerometerSpeed(String speed) {
        sendPacket(new Packet("set_accelerometer_speed", speed));
    }

    /*

        EVENT METHODS

    */

    /**
     * Called when there is an initial connection to a WebSocket Server on port 25517. If this event is called,
     * it does not necessarily mean that you are connected to TitanCast. If you want to use events for that,
     * see <code>onDeviceDetails</code>
     *
     * @param handshakeData - the handshake data provided by the WebSocket client.
     */
    public void onConnectionOpen(ServerHandshake handshakeData) {

    }

    /**
     * Called whenever an error happens between the connection.
     *
     * @param e An exception containing details about the error.
     */
    public abstract void onConnectionError(Exception e);

    /**
     * Called when the connection is closed
     *
     * @param code   The close code
     * @param reason The reason as to why the connection was closed.
     */
    public abstract void onConnectionClose(int code, String reason, boolean remote);

    /**
     * Called whenever a request to connect with the app is sent.
     */
    public void onSentRequest() {

    }

    /**
     * Called whenever the request to connect is accepted.
     */
    public abstract void onConnectAccept();

    /**
     * Called whenever the request to connect is rejected.
     */
    public abstract void onConnectReject();

    /**
     * Called whenever custom data from the app is received.
     *
     * @param data an ArrayList containing the parsed received data.
     */
    public void onCustomData(ArrayList<String> data) {

    }

    /**
     * Called whenever accelerometer data is received. To start receiving accelerometer data, use <code>enableAccelerometer()</code>
     *
     * @param x The x value of acceleration
     * @param y The y value of acceleration
     * @param z The z value of acceleration
     */
    public void onAccelerometerData(float x, float y, float z) {

    }

    /**
     * Called when the casted content has loaded on the TitanCast app.
     */
    public void onViewLoaded() {

    }

    /**
     * Called when details about the device are received, such as Android version, app version and availability of sensors.
     *
     * @param deviceDetails
     */
    public abstract void onDeviceDetails(DeviceDetails deviceDetails);

    public ConnectionCode getConnectionCode() {
        return connectionCode;
    }

    public void setConnectionCode(ConnectionCode connectionCode) {
        this.connectionCode = connectionCode;
    }

    public DeviceDetails getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(DeviceDetails deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public void disconnect() {
        this.webSocket.close();
    }
}