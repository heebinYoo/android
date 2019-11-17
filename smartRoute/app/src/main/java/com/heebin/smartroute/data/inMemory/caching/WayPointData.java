package com.heebin.smartroute.data.inMemory.caching;

import com.heebin.smartroute.data.bean.raw.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WayPointData {
    private WayPointData(){}

    private static class WayPointDataHolder{
        public static final WayPointData INSTANCE = new WayPointData();
    }

    public static WayPointData getInstance(){
        return WayPointDataHolder.INSTANCE;
    }

    private HashMap<String, ArrayList<Station>> wayPoint = new HashMap<String, ArrayList<Station>>();

    public boolean isExist(String busId){

        if(wayPoint.get(busId) != null){
            return true;
        }

        return false;
    }

    public void add(String busId, ArrayList<Station> stationList){
        wayPoint.put(busId,stationList);
    }
    public ArrayList<Station> get(String busId){
        return wayPoint.get(busId);
    }

    public Iterator<ArrayList<Station>> getStationsListIterator(){
        return wayPoint.values().iterator();
    }

    public HashMap<String, ArrayList<Station>> getWayPoint() {
        return wayPoint;
    }
}
