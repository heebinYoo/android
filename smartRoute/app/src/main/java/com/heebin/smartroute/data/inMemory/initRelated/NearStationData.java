package com.heebin.smartroute.data.inMemory.initRelated;

import com.heebin.smartroute.data.bean.meta.SrcDstStation;
import com.heebin.smartroute.data.bean.raw.Station;

import java.util.ArrayList;

public class NearStationData {

    private NearStationData(){}

    private static class NearStationHolder{
        public static final NearStationData INSTANCE = new NearStationData();
    }

    public static NearStationData getInstance(){
        return NearStationHolder.INSTANCE;
    }

    private ArrayList<Station> homeStationList;
    private ArrayList<Station> officeStationList;

    public void setStationLists(ArrayList<Station> homeStationList, ArrayList<Station> officeStationList){
        this.homeStationList=homeStationList;
        this.officeStationList=officeStationList;
    }
    public ArrayList<SrcDstStation> geth2oCatesianSrcDst(){
        ArrayList<SrcDstStation> result = new ArrayList<SrcDstStation>();
        for(Station src: homeStationList){
            for(Station dst: officeStationList){
                result.add(new SrcDstStation(src,dst));
            }
        }
        /////////////////////////////////////////
        return result;
        //////////////////////////////////
    }

    public ArrayList<SrcDstStation> geto2hCatesianSrcDst(){
        ArrayList<SrcDstStation> result = new ArrayList<SrcDstStation>();
        for(Station src:officeStationList ){
            for(Station dst: homeStationList){
                result.add(new SrcDstStation(src,dst));
            }
        }
        /////////////////////////////////////////
        return result;
        /////////////////////////////////////////
    }


}
