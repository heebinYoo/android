package com.heebin.smartroute.bean;

// 2개만 보여주는 문제가 있군, 그 이상이면 API가 계산 포기

import java.util.ArrayList;

public class Route {

    private int distance;
    private int time;
    private ArrayList<Path> pathList = new ArrayList<Path>();


}
class Path{
    private String takeId;
    private String takeName;
    private double takeX;
    private double takeY;

    private String routeID;
    private String routeName;

    private String offId;
    private String offName;
    private double offX;
    private double offY;

}
