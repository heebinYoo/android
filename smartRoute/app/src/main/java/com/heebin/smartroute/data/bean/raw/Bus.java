package com.heebin.smartroute.data.bean.raw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Bus){
            Bus target = (Bus) obj;
           return (this.busId.equals(target.busId) && this.busName.equals(target.busName));
        }
         return false;
    }

    @Override
    public int hashCode() {
        return this.busName.hashCode() + this.busId.hashCode();
    }
}
