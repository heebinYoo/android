package com.heebin.smartroute.bean.refined;

import androidx.annotation.NonNull;

import com.heebin.smartroute.bean.raw.Path;
import com.heebin.smartroute.bean.raw.Station;

public class RefinedPath {

    private String takeId;
    private String takeName;
    private double takeX;
    private double takeY;

    private String offId;
    private String offName;
    private double offX;
    private double offY;

    public boolean takeEquals(Station station){
        if(takeId.equals(station.getStationId()) && takeName.equals(station.getStationName())){
            return true;
        }
        return false;
    }
    public boolean offEquals(Station station){
        if(offId.equals(station.getStationId()) && offName.equals(station.getStationName())){
            return true;
        }
        return false;
    }
    public RefinedPath( String takeId,
                        String takeName,
                        double takeX,
                        double takeY,
                        String offId,
                        String offName,
                        double offX,
                        double offY) {

        this.takeId = takeId;
        this.takeName = takeName;
        this.takeX = takeX;
        this.takeY = takeY;
        this.offId = offId;
        this.offName = offName;
        this.offX = offX;
        this.offY  =offY;

    }
    public RefinedPath(Path path){
        this.takeId = path.getTakeId();
        this.takeName = path.getTakeName();
        this.takeX = path.getTakeX();
        this.takeY = path.getTakeY();
        this.offId = path.getOffId();
        this.offName = path.getOffName();
        this.offX = path.getOffX();
        this.offY  =path.getOffY();

    }

    @NonNull
    @Override
    public String toString() {
        return takeName + " => " +offName;
    }
}
