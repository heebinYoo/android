package com.heebin.smartroute.bean.raw;

import androidx.annotation.NonNull;

public class Station {
    private String stationId;
    private String stationName;
    private double gpsX;
    private double gpsY;
    private String arsId;

    public Station(String stationId, String stationName, double gpsX, double gpsY, String arsId){
        this.stationId=stationId;
        this.stationName=stationName;
        this.gpsX=gpsX;
        this.gpsY=gpsY;
        this.arsId=arsId;

    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public double getGpsX() {
        return gpsX;
    }

    public double getGpsY() {
        return gpsY;
    }

    @NonNull
    @Override
    public String toString() {
        return stationName;
    }
}
