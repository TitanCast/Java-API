package com.hydrabolt.titancast.javaapi;

public class TitanCastApplication {

    private String appName, appDesc, appCastURL;
    private ApplicationIcon applicationIcon;

    /**
     * The TitanCastApplication is just a collection of data about your application. It stores metadata ready to pass on Device classes.
     *
     * @param appName
     * @param appDesc
     * @param appCastURL
     * @param applicationIcon
     */
    public TitanCastApplication( String appName, String appDesc, String appCastURL, ApplicationIcon applicationIcon ) {
        this.appName = appName;
        this.appDesc = appDesc;
        this.appCastURL = appCastURL;
        this.applicationIcon = applicationIcon;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName( String appName ) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return this.appDesc;
    }

    public void setAppDesc( String appDesc ) {
        this.appDesc = appDesc;
    }

    public String getAppCastURL() {
        return this.appCastURL;
    }

    public void setAppCastURL( String appCastURL ) {
        this.appCastURL = appCastURL;
    }

    public ApplicationIcon getApplicationIcon() {
        return this.applicationIcon;
    }

    public void setApplicationIcon( ApplicationIcon applicationIcon ) {
        this.applicationIcon = applicationIcon;
    }

}
