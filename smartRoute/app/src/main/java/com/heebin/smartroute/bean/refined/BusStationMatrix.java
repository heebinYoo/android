package com.heebin.smartroute.bean.refined;

import androidx.annotation.NonNull;

import com.heebin.smartroute.bean.raw.Bus;
import com.heebin.smartroute.bean.raw.Station;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class BusStationMatrix {
    private RefinedRoute targetRoute;
    private ArrayList<Station> detailStations;
    private ArrayList<Bus> relatedBus = new ArrayList<Bus>();

    private boolean[][] busStationMatrix;

    public BusStationMatrix(RefinedRoute refinedRoute){
        this.targetRoute = refinedRoute;
        detailStations =targetRoute.getDetailStations();
        int numOfStation = targetRoute.getDetailStations().size();

        HashSet<Bus> relatedBusSet = new HashSet<Bus>();

        targetRoute.getDetailStations().forEach(station -> {
            relatedBusSet.addAll(station.getStopBusList());
        });
        int numOfBus = relatedBusSet.size();

        busStationMatrix = new boolean[numOfBus][numOfStation];

        detailStations.forEach(station ->{
            station.getStopBusList().forEach(bus -> {
                busStationMatrix [relatedBus.indexOf(bus)][detailStations.indexOf(station)] = true;
            });
        });

    }


}
