package com.heebin.smartroute.data.bean.refined;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;

import java.util.ArrayList;
import java.util.HashSet;

public class BusStationMatrix {
    private RefinedRoute targetRoute;
    private ArrayList<Station> detailStations;
    private ArrayList<Bus> relatedBus;

    private boolean[][] busStationMatrix;

    public BusStationMatrix(RefinedRoute refinedRoute){
        this.targetRoute = refinedRoute;
        this.detailStations =this.targetRoute.getDetailStations();
        this.relatedBus = new ArrayList<Bus>();

        HashSet<Bus> relatedBusSet = new HashSet<Bus>();
        targetRoute.getDetailStations().forEach(station -> {
            relatedBusSet.addAll(station.getStopBusList(true));
        });
        relatedBus.addAll(relatedBusSet);

        int numOfBus = relatedBus.size();
        int numOfStation = this.targetRoute.getDetailStations().size();


        busStationMatrix = new boolean[numOfBus][numOfStation];

        detailStations.forEach(station ->{
            station.getStopBusList(true).forEach(bus -> {
                busStationMatrix [relatedBus.indexOf(bus)][detailStations.indexOf(station)] = true;
            });
        });

    }

    public ArrayList<Station> getDetailStations() {
        return detailStations;
    }

    public RefinedRoute getTargetRoute() {
        return targetRoute;
    }

    public ArrayList<Bus> getRelatedBus() {
        return relatedBus;
    }

    public ArrayList<Bus> getAvailableBus(int indexOfStation){
        ArrayList<Bus> result = new ArrayList<Bus>();
        for(int i = 0; i<relatedBus.size(); i++) {
           if(busStationMatrix[i][indexOfStation]) {
               result.add(relatedBus.get(i));
           }
        }
        return result;
    }
}
