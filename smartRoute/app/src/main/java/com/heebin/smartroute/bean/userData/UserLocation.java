package com.heebin.smartroute.bean.userData;

import com.heebin.smartroute.bean.raw.Route;
import com.heebin.smartroute.bean.raw.Station;

import java.util.ArrayList;

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

    @Deprecated
    private ArrayList<Station> homeStationList;
    @Deprecated
    private ArrayList<Station> officeStationList;

    private ArrayList<Route> h2oRouteList;
    private ArrayList<Route> o2hRouteList;

    public void setHome(double homeLat, double homeLong) {
        this.homeLat = homeLat;
        this.homeLong = homeLong;
    }

    public void setOffice(double officeLat, double officeLong) {
        this.officeLat = officeLat;
        this.officeLong = officeLong;
    }

    @Deprecated
    public void setStationLists(ArrayList<Station> homeStationList, ArrayList<Station> officeStationList){
        this.homeStationList=homeStationList;
        this.officeStationList=officeStationList;
    }

    public void setRouteList(ArrayList<Route> h2oRouteList, ArrayList<Route> o2hRouteList) {
        this.h2oRouteList = h2oRouteList;
        this.o2hRouteList = o2hRouteList;
    }

    public ArrayList<Route> getO2hRouteList() {
        return o2hRouteList;
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
