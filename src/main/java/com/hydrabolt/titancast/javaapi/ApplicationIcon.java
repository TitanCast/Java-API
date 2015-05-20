package com.hydrabolt.titancast.javaapi;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ApplicationIcon {

    private String image;

    /**
     * This constructor should only be used if you do not yet have an image ready to be used for your application or if you wish to not have have an image for your application. The image will be set as "none" and the TitanCast Android application will not display any application icon as a result. Application Icon should be used in conjunction with TitanCastApplication to provide an icon for your application.
     */
    public ApplicationIcon() {
        this.image = "none";
    }

    /**
     * This constructor will allow you to create an Application Icon with an already base64-encoded image. Application Icon should be used in conjunction with TitanCastApplication to provide an icon for your application.
     *
     * @param base64 this is the base64-encoded image. Please make sure that the image is actually base64 encoded - and properly done - otherwise you may experience trouble getting it to render on the TitanCast Android application.
     */
    public ApplicationIcon( String base64 ) {
        this.image = base64;
    }

    /**
     * This constructor will allow you to create an Application Icon from a File object. However, be aware that IOExceptions may occur and if so, the Application Icon image would be set to "none". It may be useful to check the Application Icon's image after declaration to see whether any errors were experienced. Application Icon should be used in conjunction with TitanCastApplication to provide an icon for your application.
     *
     * @param file this is a File object that points to an image. This object will be base64-encoded and an image will be generated from it.
     */
    public ApplicationIcon( File file ) {
        try {
            this.image = new String( Base64.encodeBase64( FileUtils.readFileToByteArray( file ) ) );
        } catch (IOException e) {
            this.image = "none";
        }
    }

    public String getImage() {
        return this.image;
    }

    public void setImage( String base64 ) {
        this.image = base64;
    }

}
