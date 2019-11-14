package com.heebin.smartroute.bean.raw;

import androidx.annotation.NonNull;

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
        return " takeName : " +takeName + " routeName : " + routeName + " offId : " + offId;
    }
}