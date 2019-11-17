package com.heebin.smartroute.data.bean.refined;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.heebin.smartroute.data.bean.raw.Path;
import com.heebin.smartroute.data.bean.raw.Station;

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


    public String getTakeId() {
        return takeId;
    }

    public String getTakeName() {
        return takeName;
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

    public String getOffName() {
        return offName;
    }

    public double getOffX() {
        return offX;
    }

    public double getOffY() {
        return offY;
    }

    @Override
    public int hashCode() {
        return this.getTakeId().hashCode() + this.getOffId().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof RefinedPath){
            RefinedPath path = (RefinedPath) obj;
            return (this.takeId.equals(path.takeId) && this.offId.equals(path.offId));
        }
        else {
            return false;
        }


    }
}
