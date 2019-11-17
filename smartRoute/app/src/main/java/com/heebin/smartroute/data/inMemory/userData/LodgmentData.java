package com.heebin.smartroute.data.inMemory.userData;

public class LodgmentData {
    private LodgmentData(){}

    private static class UserLocationHolder{
        public static final LodgmentData INSTANCE = new LodgmentData();
    }

    public static LodgmentData getInstance(){
        return UserLocationHolder.INSTANCE;
    }

    private double homeLat;
    private double homeLong;
    private double officeLat;
    private double officeLong;

    public void setHome(double homeLat, double homeLong) {
        this.homeLat = homeLat;
        this.homeLong = homeLong;
    }

    public void setOffice(double officeLat, double officeLong) {
        this.officeLat = officeLat;
        this.officeLong = officeLong;
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
