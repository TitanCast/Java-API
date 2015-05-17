package com.hydrabolt.titancast.java_api.utils;

import org.java_websocket.util.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Packet {

    private String type;
    private ArrayList<String> data;

    /**
     * Constructor to create a new Packet object that has the type and data that are supplied.
     *
     * @param packetType The type of packet that you would like to instantiate
     * @param packetData The data that you would like to insert into this packet
     *                   {@code new Packet("customData", MyStringArrayList)} would create a packet of the type 'customData' and the data would be the entire contents of MyStringArrayList
     */
    public Packet( String packetType, ArrayList<String> packetData ) {
        this.type = packetType;
        this.data = packetData;
    }

    /**
     * Constructor to create a new Packet object that has the type and data that are supplied. When later referencing the Packet created by this constructor, please note that the data variable will be an ArrayList and no longer a String array.
     *
     * @param packetType The type of packet that you would like to instantiate
     * @param packetData The data that you would like to insert into this packet
     *                   {@code new Packet("customData", new String[]{'a', 'b'})} would create a packet of the type 'customData' and the data values 'a' and 'b'.
     */
    public Packet( String packetType, String[] packetData ) {
        this.type = packetType;
        this.data = new ArrayList<String>( Arrays.asList( packetData ) );
    }

    /**
     * Constructor to create a Packet using either the type of the packet or a compiled packet string. If the constructor was used with just a packet type, the Packet's data will be a blank initialised ArrayList. If the constructor was used with a serialised Packet string, it will be deserialised into the Packet.
     * {@code new Packet("enableAccelerometer")} would create a Packet with the type "enableAccelerometer" and no data - although the data variable will be initialised.
     * {@code new Packet("packet YXBwbGUNCg== ZXhhbXBsZQ==")} would create a Packet with the type "packet" and the data would be an ArrayList containing 'apple' and 'example'.
     *
     * @param data If used as a packetType, it may include characters that are not whitespace (e.g abc_def is valid as well as a#b?c). Otherwise, the data variable would be a serialised packet that would be deserialised.
     */
    public Packet( String data ) {

        String[] chunks = data.split( "\\s+" );

        if (chunks.length == 0) {

            this.type = data;
            this.data = new ArrayList<String>();

            return;
        }

        String[] dataChunks = new String[chunks.length - 1];

        int iteration = -1;

        for (String s : chunks) {

            iteration++;

            if (iteration == 0) {
                this.type = s;
                break;
            }

            try {
                dataChunks[iteration] = new String( Base64.decode( s.getBytes() ), "UTF-8" );
            } catch (IOException e) {
                dataChunks[iteration] = "";
            }

        }

        this.data = new ArrayList<String>( Arrays.asList( dataChunks ) );
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData( ArrayList<String> data ) {
        this.data = data;
    }
}
