package com.heebin.smartroute.learning.Matrix;

import com.heebin.smartroute.data.bean.refined.BusStationMatrix;

public class BusStationMatrixHolder {
   private static BusStationMatrix data;

    public static void Hold(BusStationMatrix b){
        BusStationMatrixHolder.data = b;
    }

    public static BusStationMatrix getInstance(){
        return data;
    }

}
