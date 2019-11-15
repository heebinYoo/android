package com.heebin.smartroute.bean.meta;

import com.heebin.smartroute.bean.raw.Station;

public class SrcDstStation {
    public Station src;
    public Station dst;
    public SrcDstStation(Station src, Station dst){
        this.src=src;
        this.dst=dst;
    }
}
