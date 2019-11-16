package com.heebin.smartroute.bean.userData;

import android.util.Log;

import com.heebin.smartroute.bean.raw.Bus;
import com.heebin.smartroute.bean.raw.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

}
