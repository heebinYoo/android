package com.heebin.smartroute.bean.raw;

import androidx.annotation.NonNull;

import com.heebin.smartroute.busAPI.connector.StopBusSearcherConnector;

import java.util.ArrayList;


public class Station {
    private String stationId;
    private String stationName;
    private double gpsX;
    private double gpsY;
    private String arsId;
    private ArrayList<Bus> stopBusList;

    public Station(String stationId, String stationName, double gpsX, double gpsY, String arsId){

        this.stationId=stationId;
        this.stationName=stationName;
        this.gpsX=gpsX;
        this.gpsY=gpsY;
        this.arsId=arsId;

        makeStopBusList();
    }
    private void makeStopBusList(){
        StopBusSearcherConnector stopBusSearcherConnector = new StopBusSearcherConnector();
        stopBusSearcherConnector.preRun(this.arsId);
        stopBusSearcherConnector.run();
        stopBusList = stopBusSearcherConnector.postRun();
    }


    public ArrayList<Bus> getStopBusList() {
        return stopBusList;
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

    public String getArsId() {
        return arsId;
    }

    @NonNull
    @Override
    public String toString() {
        return stationName;
    }
}
