package com.heebin.smartroute.bean.raw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Path{
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


    public String getTakeId() {
        return takeId;
    }

    public double getTakeX() {
        return takeX;
    }

    public double getTakeY() {
        return takeY;
    }

    public String getOffId() {
        return offId;
    }

    public double getOffX() {
        return offX;
    }

    public double getOffY() {
        return offY;
    }


    public Path(String takeId, String takeName, double takeX, double takeY, String routeID, String routeName, String offId, String offName, double offX, double offY) {
        this.takeId = takeId;
        this.takeName = takeName;
        this.takeX = takeX;
        this.takeY = takeY;
        this.routeID = routeID;
        this.routeName = routeName;
        this.offId = offId;
        this.offName = offName;
        this.offX = offX;
        this.offY = offY;
    }

    @NonNull
    @Override
    public String toString() {
        return " takeName : " +takeName + " => routeName : " + routeName +" => offName: " + offName;
    }

    public String getTakeName() {
        return takeName;
    }
    public String getRouteName() {
        return routeName;
    }
    public String getOffName() {
        return offName;
    }


    public boolean equivalents(@Nullable Path target) {
            if( this.offId.equals(target.offId) && this.takeId.equals(target.takeId) ) {
                return true;
            }
        return false;
    }

}