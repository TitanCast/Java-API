package com.hydrabolt.titancast.javaapi.utils;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by amish on 30/07/2015.
 */
public class DeviceDetails {

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public String getTitanCastVersion() {
        return titanCastVersion;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public boolean hasAccelerometer() {
        return accelerometer;
    }

    public boolean hasGyroscope() {
        return gyroscope;
    }

    public boolean hasAmbientTemperature() {
        return ambientTemperature;
    }

    public boolean hasGravity() {
        return gravity;
    }

    public boolean hasHeartRate() {
        return heartRate;
    }

    public boolean hasLight() {
        return light;
    }

    public boolean hasMagneticField() {
        return magneticField;
    }

    public boolean hasPressure() {
        return pressure;
    }

    public boolean hasProximity() {
        return proximity;
    }

    public boolean hasRelativeHumidity() {
        return relativeHumidity;
    }

    public boolean hasRotation() {
        return rotation;
    }

    public boolean hasSignificantMotion() {
        return significantMotion;
    }

    public boolean hasStepCounter() {
        return stepCounter;
    }

    public boolean hasStepDetector() {
        return stepDetector;
    }

    public boolean hasVibrator() {
        return vibrator;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public ArrayList<String> getRaw() {
        return raw;
    }

    private String deviceModel;
    private String deviceManufacturer;
    private String titanCastVersion;
    private int androidVersion;
    private Boolean vibrator, accelerometer, gyroscope, ambientTemperature, gravity, heartRate, light, magneticField, pressure, proximity,
                    relativeHumidity, rotation, significantMotion, stepCounter, stepDetector;

    private ArrayList<String> raw;

    public DeviceDetails(ArrayList<String> details){

        this.raw = details;

        for(String detail : details){

            String[] part = StringUtils.splitString(detail, "=");
            String key = part[0],
                    value = part[1];

            switch(key){

                case "device_model":
                    this.deviceModel = value;
                    break;

                case "device_manufacturer":
                    this.deviceManufacturer = value;
                    break;

                case "device_android_version":
                    try {
                        this.androidVersion = Integer.parseInt(value);
                    }catch(Exception e){
                        this.androidVersion = -1;
                    }
                    break;

                case "titan_cast_version":
                    this.titanCastVersion = value;
                    break;

                case "has_vibrator":
                    this.vibrator = Boolean.parseBoolean(value);
                    break;

                case "has_accelerometer":
                    this.accelerometer = Boolean.parseBoolean(value);
                    break;

                case "has_gyroscope":
                    this.gyroscope = Boolean.parseBoolean(value);
                    break;

                case "has_ambient_temperature":
                    this.ambientTemperature = Boolean.parseBoolean(value);
                    break;

                case "has_gravity":
                    this.gravity = Boolean.parseBoolean(value);
                    break;

                case "has_heart_rate":
                    this.heartRate = Boolean.parseBoolean(value);
                    break;

                case "has_light":
                    this.light = Boolean.parseBoolean(value);
                    break;

                case "has_magnetic_field":
                    this.magneticField = Boolean.parseBoolean(value);
                    break;

                case "has_pressure":
                    this.pressure = Boolean.parseBoolean(value);
                    break;

                case "has_proximity":
                    this.proximity = Boolean.parseBoolean(value);
                    break;

                case "has_relative_humidity":
                    this.relativeHumidity = Boolean.parseBoolean(value);
                    break;

                case "has_rotation":
                    this.rotation = Boolean.parseBoolean(value);
                    break;

                case "has_significant_motion":
                    this.significantMotion = Boolean.parseBoolean(value);
                    break;

                case "has_step_counter":
                    this.stepCounter = Boolean.parseBoolean(value);
                    break;

                case "has_step_detector":
                    this.stepDetector = Boolean.parseBoolean(value);
                    break;
            }

        }

    }

    public String constructDeviceName(){

       return capitalize( getDeviceManufacturer() ) + " " + capitalize( getDeviceModel() );

    }

    private String capitalize(String s){

        return s.substring(0, 1).toUpperCase()+s.substring(1);

    }
}
