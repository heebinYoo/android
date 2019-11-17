package com.heebin.smartroute.data.inMemory.caching;

import android.content.ContentValues;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.util.constants.DataBaseConstatns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StopBusData {
    private StopBusData(){}

    private static class StationDataHolder{
        public static final StopBusData INSTANCE = new StopBusData();
    }

    public static StopBusData getInstance(){
        return StationDataHolder.INSTANCE;
    }

    private HashMap<String, ArrayList<Bus>> stopBus = new HashMap<String, ArrayList<Bus>>();

    public boolean isExist(String arsId){

        if(stopBus.get(arsId) != null){
            return true;
        }

        return false;
    }

    public void add(String arsId, ArrayList<Bus> busList){
        stopBus.put(arsId,busList);
    }
    public ArrayList<Bus> get(String arsId){
        return stopBus.get(arsId);
    }

    public Iterator<ArrayList<Bus>> getStopBusesListIterator(){
        return stopBus.values().iterator();
    }

    public HashMap<String, ArrayList<Bus>> getStopBus() {
        return stopBus;
    }
}
