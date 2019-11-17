package com.heebin.smartroute.data.bean.meta;

import com.heebin.smartroute.data.bean.raw.Station;

public class SrcDstStation {
    public Station src;
    public Station dst;
    public SrcDstStation(Station src, Station dst){
        this.src=src;
        this.dst=dst;
    }
}
