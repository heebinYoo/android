package com.heebin.smartroute.bean.userData;

import com.heebin.smartroute.bean.meta.SrcDstStation;
import com.heebin.smartroute.bean.raw.Route;
import com.heebin.smartroute.bean.raw.Station;
import com.heebin.smartroute.bean.refined.RefinedRoute;
import com.heebin.smartroute.util.RouteRefiner;

import java.util.ArrayList;
import java.util.List;

public class UserLocation {
    private UserLocation(){}

    private static class UserLocationHolder{
        public static final UserLocation INSTANCE = new UserLocation();
    }

    public static UserLocation getInstance(){
        return UserLocationHolder.INSTANCE;
    }

    private double homeLat;
    private double homeLong;
    private double officeLat;
    private double officeLong;


    private ArrayList<Station> homeStationList;
    private ArrayList<Station> officeStationList;

    private ArrayList<Route> h2oRouteList;
    private ArrayList<Route> o2hRouteList;

    private ArrayList<RefinedRoute> refinedh2oRoutes;
    private ArrayList<RefinedRoute> refinedo2hRoutes;

    public ArrayList<RefinedRoute> getRefinedh2oRoutes() {
        return refinedh2oRoutes;
    }

    public ArrayList<RefinedRoute> getRefinedo2hRoutes() {
        return refinedo2hRoutes;
    }

    public void setHome(double homeLat, double homeLong) {
        this.homeLat = homeLat;
        this.homeLong = homeLong;
    }

    public void setOffice(double officeLat, double officeLong) {
        this.officeLat = officeLat;
        this.officeLong = officeLong;
    }


    public void setStationLists(ArrayList<Station> homeStationList, ArrayList<Station> officeStationList){
        this.homeStationList=homeStationList;
        this.officeStationList=officeStationList;
    }

    public void setRouteList(ArrayList<Route> h2oRouteList, ArrayList<Route> o2hRouteList) {


        this.h2oRouteList = h2oRouteList;
        this.o2hRouteList = o2hRouteList;

        this.refinedh2oRoutes = RouteRefiner.refine(this.h2oRouteList);
        this.refinedo2hRoutes = RouteRefiner.refine(this.o2hRouteList);

    }

    public ArrayList<SrcDstStation> geth2oCatesianSrcDst(){
        ArrayList<SrcDstStation> result = new ArrayList<SrcDstStation>();
        for(Station src: homeStationList){
            for(Station dst: officeStationList){
                result.add(new SrcDstStation(src,dst));
            }
        }
        /////////////////////////////////////////
        return new ArrayList<SrcDstStation>(result.subList(0, 3)); //result 로 바꾸기
        //////////////////////////////////
    }
    public ArrayList<SrcDstStation> geto2hCatesianSrcDst(){
        ArrayList<SrcDstStation> result = new ArrayList<SrcDstStation>();
        for(Station src:officeStationList ){
            for(Station dst: homeStationList){
                result.add(new SrcDstStation(src,dst));
            }
        }
        /////////////////////////////////////////
        return new ArrayList<SrcDstStation>(result.subList(0, 3)); //result 로 바꾸기
        /////////////////////////////////////////
    }



    public double getHomeLat() {
        return homeLat;
    }

    public double getHomeLong() {
        return homeLong;
    }

    public double getOfficeLat() {
        return officeLat;
    }

    public double getOfficeLong() {
        return officeLong;
    }
}
