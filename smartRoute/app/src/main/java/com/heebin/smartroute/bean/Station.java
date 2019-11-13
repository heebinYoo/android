package com.heebin.smartroute.bean;

public class Station {
    private String stationId;
    private String stationName;
    private double gpsX;
    private double gpsY;
    private String arsId;
    private int distTo;
    public Station(String stationId, String stationName, double gpsX, double gpsY, String arsId, int distTo){
        this.stationId=stationId;
        this.stationName=stationName;
        this.gpsX=gpsX;
        this.gpsY=gpsY;
        this.arsId=arsId;
        this.distTo=distTo;
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

}
