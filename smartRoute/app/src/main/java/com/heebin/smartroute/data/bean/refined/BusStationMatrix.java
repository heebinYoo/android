package com.heebin.smartroute.data.bean.refined;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;

import java.util.ArrayList;
import java.util.HashSet;

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
            relatedBusSet.addAll(station.getStopBusList(true));
        });
        int numOfBus = relatedBusSet.size();

        busStationMatrix = new boolean[numOfBus][numOfStation];

        detailStations.forEach(station ->{
            station.getStopBusList(true).forEach(bus -> {
                busStationMatrix [relatedBus.indexOf(bus)][detailStations.indexOf(station)] = true;
            });
        });

    }


}
