package com.heebin.smartroute.data.bean.raw;


import java.util.ArrayList;

public class Route {

    private int distance;
    private int time;
    private ArrayList<Path> pathList;

    public int getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }



    public Route(int time, int distance){
        this.distance=distance;
        this.time=time;
    pathList = new ArrayList<Path>();
    }
    public void addNewPath(String takeId,
                            String takeName,
                           double takeX,
                           double takeY,
                           String routeID,
                           String routeName,
                           String offId,
                           String offName,
                           double offX,
                           double offY){
        pathList.add(new Path(takeId,takeName,takeX,takeY,routeID,routeName,offId,offName,offX,offY));

    }

    public ArrayList<Path> getPathList() {
        return pathList;
    }
    public boolean equivalents(Route target){
       if(this.getPathList().size() == target.getPathList().size()) {
           for (int i = 0; i < this.getPathList().size(); i++) {

               if (!this.getPathList().get(i).equivalents(target.getPathList().get(i))) {
                   return false;
               }

           }
           return true;
       }
        return false;
    }
}

