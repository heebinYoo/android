package com.heebin.smartroute.data.bean.raw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.heebin.smartroute.busAPI.connector.sync.StopBusSearcherConnector;

import java.util.ArrayList;


public class Station {
    private String stationId;
    private String stationName;
    private double gpsX;
    private double gpsY;
    private String arsId;

    //lazy inited
    private transient ArrayList<Bus> stopBusList;

    public Station(String stationId, String stationName, double gpsX, double gpsY, String arsId){

        this.stationId=stationId;
        this.stationName=stationName;
        this.gpsX=gpsX;
        this.gpsY=gpsY;
        this.arsId=arsId;


    }
    private void makeStopBusList(){
        StopBusSearcherConnector stopBusSearcherConnector = new StopBusSearcherConnector();
        stopBusSearcherConnector.preRun(this.arsId);
        stopBusSearcherConnector.run();
        stopBusList = stopBusSearcherConnector.postRun();
    }


    public ArrayList<Bus> getStopBusList(boolean newMake) {
        if(stopBusList == null && newMake){
            makeStopBusList();
        } //lazy init

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Station){
            Station station = (Station) obj;
            return (this.stationId.equals(station.stationId) && this.arsId.equals(station.arsId));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return stationId.hashCode()+ arsId.hashCode();
    }
}
