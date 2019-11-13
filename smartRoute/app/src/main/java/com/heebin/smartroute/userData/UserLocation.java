package com.heebin.smartroute.userData;

public class UserLocation {
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
}
