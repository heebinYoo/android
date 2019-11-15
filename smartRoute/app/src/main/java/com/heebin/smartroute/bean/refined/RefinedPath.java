package com.heebin.smartroute.bean.refined;

import com.heebin.smartroute.bean.raw.Path;

public class RefinedPath {
    private String takeId;
    private String takeName;
    private double takeX;
    private double takeY;

    private String offId;
    private String offName;
    private double offX;
    private double offY;

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
}
