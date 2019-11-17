package com.heebin.smartroute.data.inMemory.initRelated;

import com.heebin.smartroute.data.bean.raw.Route;
import com.heebin.smartroute.data.inMemory.userData.RefinedRouteData;

import java.util.ArrayList;

public class RouteData {
    private RouteData(){}

    private static class RouteDataHolder{
        public static final RouteData INSTANCE = new RouteData();
    }

    public static RouteData getInstance(){
        return RouteDataHolder.INSTANCE;
    }

    private ArrayList<Route> h2oRouteList = new ArrayList<Route>();
    private ArrayList<Route> o2hRouteList  = new ArrayList<Route>();


    public void appendRouteList(ArrayList<Route> h2oRouteList, ArrayList<Route> o2hRouteList) {


        this.h2oRouteList.addAll(h2oRouteList);
        this.o2hRouteList.addAll(o2hRouteList);

        RefinedRouteData.getInstance().setData(this.h2oRouteList, this.o2hRouteList);

    }

    public ArrayList<Route> getO2hRouteList() {
        return o2hRouteList;
    }
    public ArrayList<Route> getH2oRouteList() {
        return h2oRouteList;
    }



}
