package com.heebin.smartroute.bean.refined;

import androidx.annotation.NonNull;

import com.heebin.smartroute.bean.raw.Station;
import com.heebin.smartroute.busAPI.connector.WayPointSearcherConnect;

import java.util.ArrayList;
import java.util.Iterator;

public class RefinedRoute {
    private ArrayList<RefinedPath> mainPathes;
    private ArrayList<Station> detailStations;
    private int estimatedDistance;
    private int estimatedTime;
    public RefinedRoute(ArrayList<RefinedPath> mainPathes, int estimatedDistance, int estimatedTime, ArrayList<String> relatedBusId){
        this.mainPathes = mainPathes;
        this.estimatedDistance = estimatedDistance;
        this.estimatedTime = estimatedTime;
        this.detailStations = new ArrayList<Station>();
        makeDetailStations(relatedBusId);
    }
    private void makeDetailStations(ArrayList<String> relatedBusId) {

        WayPointSearcherConnect wayPointSearcherConnect = new WayPointSearcherConnect();
        ArrayList<ArrayList<Station>> wayPointsList = new ArrayList<ArrayList<Station>>();

        for (String s : relatedBusId) {
            wayPointSearcherConnect.preRun(s);
            wayPointSearcherConnect.run();
            wayPointsList.add(wayPointSearcherConnect.postRun());
        }
        Iterator<ArrayList<Station>> itrStation = wayPointsList.iterator();
        //Iterator<RefinedPath> itrRefinedPath = mainPathes.iterator();
        //mainPathes
        int indexMainPath = 0;
        while (  itrStation.hasNext() && indexMainPath < mainPathes.size()  ) {
            ArrayList<Station> stations = itrStation.next();

            boolean isfound = false;
            for (int i = 0; i < stations.size(); i++) {

                if (mainPathes.get(indexMainPath).takeEquals(stations.get(i))) {
                    isfound = true;
                    detailStations.add(stations.get(i));
                } else if (mainPathes.get(indexMainPath).offEquals(stations.get(i))) {
                    detailStations.add(stations.get(i));
                    indexMainPath++;
                    break;
                } else {
                    if (isfound) {
                        detailStations.add(stations.get(i));

                    }
                }

            }
        }




    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        mainPathes.forEach(refinedPath -> {
            stringBuilder.append(refinedPath.toString());
            stringBuilder.append("\t");
        });
        return  stringBuilder.toString();
    }
}
