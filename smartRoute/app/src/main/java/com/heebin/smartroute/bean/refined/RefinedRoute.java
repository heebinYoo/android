package com.heebin.smartroute.bean.refined;

import com.heebin.smartroute.bean.raw.Station;

import java.util.ArrayList;

public class RefinedRoute {
    private ArrayList<RefinedPath> mainPathes;
    private ArrayList<Station> detailStations;
    private int estimatedDistance;
    private int estimatedTime;
    public RefinedRoute(ArrayList<RefinedPath> mainPathes, int estimatedDistance, int estimatedTime){
        this.mainPathes = mainPathes;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        makeDetailStations();
    }
    private void makeDetailStations() {

    }


}
