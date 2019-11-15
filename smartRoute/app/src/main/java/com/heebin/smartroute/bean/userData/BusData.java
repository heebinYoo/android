package com.heebin.smartroute.bean.userData;

import com.heebin.smartroute.bean.raw.Path;

import java.util.HashMap;

public class BusData {
    private BusData(){}

    private static class BusDataHolder{
        public static final BusData INSTANCE = new BusData();
    }

    public static BusData getInstance(){
        return BusDataHolder.INSTANCE;
    }

    private HashMap<String, String> busNameToBusId = new HashMap<String, String>();
    private HashMap<String, String> busIdToBusName = new HashMap<String, String>();

    public void addFromPath(Path path){
        busIdToBusName.put(path.getRouteID(), path.getRouteName());
        busNameToBusId.put(path.getRouteName(), path.getRouteID());
    }
    public void add(String busName, String BusId){
        busIdToBusName.put(BusId,busName);
        busNameToBusId.put(busName, BusId);
    }
    public String getBusId(String busName){
        return busNameToBusId.get(busName);
    }
    public String getBusName(String busId){
        return busIdToBusName.get(busId);
    }


}
