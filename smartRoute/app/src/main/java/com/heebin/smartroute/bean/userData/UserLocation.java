package com.heebin.smartroute.bean.userData;

import com.heebin.smartroute.bean.Station;

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

    private ArrayList<Station> homeStationList;
    private ArrayList<Station> officeStationList;

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

    public ArrayList<Station> getOfficeStationList() {
        return officeStationList;
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
