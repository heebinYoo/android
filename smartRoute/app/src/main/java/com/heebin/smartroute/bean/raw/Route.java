package com.heebin.smartroute.bean.raw;

// 2개만 보여주는 문제가 있군, 그 이상이면 API가 계산 포기

import java.util.ArrayList;

public class Route {

    private int distance;
    private int time;
    private ArrayList<Path> pathList;

    public Route(int time, int distance){
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
}

