package com.heebin.smartroute.data.inMemory.initRelated;

import com.heebin.smartroute.data.bean.raw.Path;
import com.heebin.smartroute.data.bean.refined.RefinedPath;

import java.util.HashSet;
import java.util.Iterator;

public class RefinedPathDataFactory {
    private RefinedPathDataFactory(){}

    private static class RefinedPathDataFactoryHolder{
        public static final RefinedPathDataFactory INSTANCE = new RefinedPathDataFactory();
    }

    public static RefinedPathDataFactory getInstance(){
        return RefinedPathDataFactoryHolder.INSTANCE;
    }

    HashSet<RefinedPath> paths = new HashSet<>();


    public RefinedPath createPath(String takeId,
                                  String takeName,
                                  double takeX,
                                  double takeY,
                                  String offId,
                                  String offName,
                                  double offX,
                                  double offY){

        RefinedPath result =getPath(takeId,offId);
        if(result!=null){
            return result;
        }
        else{
            result = new RefinedPath(takeId,takeName,takeX,takeY,offId,offName,offX,offY);
            paths.add(result);
            return result;
        }

    }
    public RefinedPath createPath(Path path){

        RefinedPath result =getPath(path.getTakeId(),path.getOffId());
        if(result!=null){
            return result;
        }
        else{
            result = new RefinedPath(path);
            paths.add(result);
            return result;
        }

    }

    public HashSet<RefinedPath> getPaths() {
        return paths;
    }

    private RefinedPath getPath(String takeId, String offId) {
       Iterator<RefinedPath> itr = paths.iterator();
       while (itr.hasNext()){
           RefinedPath path =itr.next();
           if(path.getTakeId().equals(takeId) && path.getOffId().equals(offId)){
               return path;
           }
       }
       return null;
    }
}
