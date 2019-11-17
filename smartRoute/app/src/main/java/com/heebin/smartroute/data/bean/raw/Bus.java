package com.heebin.smartroute.data.bean.raw;

import androidx.annotation.NonNull;

public class Bus {
    private String busId;
    private String busName;

    public Bus(String busId, String busName){
        this.busId = busId;
        this.busName = busName;
    }

    public String getBusId() {
        return busId;
    }

    public String getBusName() {
        return busName;
    }

    @NonNull
    @Override
    public String toString() {
        return "bus Id : " + busId + " name : " + busName;
    }
}
