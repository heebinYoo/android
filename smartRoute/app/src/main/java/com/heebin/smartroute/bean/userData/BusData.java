package com.heebin.smartroute.bean.userData;

import com.heebin.smartroute.bean.raw.Bus;
import com.heebin.smartroute.bean.raw.Path;

import java.util.ArrayList;
import java.util.HashSet;

public class BusData {
    private BusData(){}

    private static class BusDataHolder{
        public static final BusData INSTANCE = new BusData();
    }

    public static BusData getInstance(){
        return BusDataHolder.INSTANCE;
    }


    private HashSet<Bus> busList = new HashSet<Bus>();


    public void add(Path path){
       busList.add(new Bus(path.getRouteID(), path.getRouteName()));
    }
    public void add(String busId, String busName){
        busList.add(new Bus(busId, busName));
    }
    public void add(Bus bus){
        busList.add(bus);
    }

    public String getBusId(String busName){
        for (Bus bus : busList) {
            if(bus.getBusName().equals(busName)){
                return bus.getBusId();
            }
        }
        return null;
    }
    public String getBusName(String busId){
        for (Bus bus : busList) {
            if(bus.getBusId().equals(busId)){
                return bus.getBusName();
            }
        }
        return null;
    }


}
