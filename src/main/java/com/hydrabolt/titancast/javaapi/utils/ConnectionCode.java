package com.hydrabolt.titancast.javaapi.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ConnectionCode {

    private URI uri;

    public ConnectionCode( String addr ) {
        setURI( addr );

    }

    public URI getURI() {
        return uri;
    }

    private void setURI( String addr ) {
        if (addr.indexOf( "." ) == -1) {
            //using a connection code
            String[] chunks = new String[4],
                    pchunks = new String[4];
            chunks = addr.split( "\\s+" );
            for (int i = 0; i < 4; i++) {
                pchunks[i] = Integer.parseInt( chunks[i], 16 ) + "";
            }
            addr = join( pchunks, "." );
            try {
                uri = new URI( "wss://" + addr + ":25517" );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else {
            //using an IP
            try {
                uri = new URI( "wss://" + addr + ":25517" );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    private String join(String[] array, String joiner){

        String toRet = "";
        for(String s : array){
            toRet += s + joiner;
        }

        return toRet.substring( 0, toRet.length() - 1 );

    }

}
