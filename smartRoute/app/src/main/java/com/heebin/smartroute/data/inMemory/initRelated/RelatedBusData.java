package com.heebin.smartroute.data.inMemory.initRelated;


import com.heebin.smartroute.data.bean.raw.Bus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class RelatedBusData {
    private RelatedBusData(){}

    private static class RelatedBusDataHolder{
        public static final RelatedBusData INSTANCE = new RelatedBusData();
    }

    public static RelatedBusData getInstance(){
        return RelatedBusDataHolder.INSTANCE;
    }

    private HashSet<Bus> buses = new HashSet<Bus>();

    public void add(Bus bus){
        buses.add(bus);
    }

    public HashSet<Bus> getBuses() {
        return buses;
    }
}
