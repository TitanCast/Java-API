package com.hydrabolt.titancast.java_api;

public class TitanCastApplication {

    private String appName, appDesc, appCastURL;
    private ApplicationIcon icon;

    /**
     * TitanCastApplication is the main object that stores all the information about your application. It is used to provide information about your application when you connect to devices running TitanCast.
     *
     * @param appName    this is the name of your application. It does not have to be alphanumeric and so as a result application names such as "Test#@!?" can be used. However, it is preferred to not use special characters in excess
     * @param appDesc    this is the description of your application. It does not have to be alphanumeric but it is capped on the Android application to 240 characters.
     * @param appCastURL this is the location where your actual application content/receiver is. URLs can be supplied (ensure to prefix 'http://') or files stored locally on the device (if required). This content will be loaded if the device allows a connection to your application.
     * @param icon       this is the icon that your application can use. This will feature on the device's "Request Connection" screen alongside your application's title and description. If an empty ApplicationIcon is supplied, the device will not render anything.
     */
    public TitanCastApplication( String appName, String appDesc, String appCastURL, ApplicationIcon icon ) {
        this.appName = appName;
        this.appDesc = appDesc;
        this.appCastURL = appCastURL;
        this.icon = icon;
    }

    public ApplicationIcon getIcon() {
        return icon;
    }

    public void setIcon( ApplicationIcon icon ) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName( String appName ) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc( String appDesc ) {
        this.appDesc = appDesc;
    }

    public String getAppCastURL() {
        return appCastURL;
    }

    public void setAppCastURL( String appCastURL ) {
        this.appCastURL = appCastURL;
    }

}
