package com.heebin.smartroute.data.inMemory.userData;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Route;
import com.heebin.smartroute.data.bean.refined.RefinedPath;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;
import com.heebin.smartroute.data.inMemory.initRelated.RefinedPathDataFactory;
import com.heebin.smartroute.data.inMemory.initRelated.RelatedBusData;

import java.util.ArrayList;

public class RefinedRouteData {
    private RefinedRouteData(){}

    private static class RefinedRouteDataHolder{
        public static final RefinedRouteData INSTANCE = new RefinedRouteData();
    }

    public static RefinedRouteData getInstance(){
        return RefinedRouteDataHolder.INSTANCE;
    }


    private ArrayList<RefinedRoute> refinedh2oRoutes;
    private ArrayList<RefinedRoute> refinedo2hRoutes;

    public void setData(ArrayList<Route> h2oRouteList, ArrayList<Route> o2hRouteList){
        this.refinedh2oRoutes = this.refine(h2oRouteList);
        this.refinedo2hRoutes = this.refine(o2hRouteList);
    }

    public void setDataFromDB(ArrayList<RefinedRoute> refinedh2oRoutes, ArrayList<RefinedRoute> refinedo2hRoutes){
        this.refinedh2oRoutes = refinedh2oRoutes;
        this.refinedo2hRoutes =refinedo2hRoutes;
    }


    private ArrayList<RefinedRoute> refine(ArrayList<Route> raw){

        return refineRoute(routeDeduplication(raw));
    }


    private ArrayList<Route> routeDeduplication(ArrayList<Route> raw){
        ArrayList<Route> uniqueRoute = new ArrayList<Route>();

        for(int i=0; i<raw.size();i++){
            boolean isNotAdded = true;

            for(int j=0; j<uniqueRoute.size(); j++){
                isNotAdded = isNotAdded && !raw.get(i).equivalents(uniqueRoute.get(j));
            }

            if(isNotAdded){
                uniqueRoute.add(raw.get(i));
            }
        }
        return uniqueRoute;
    }
    private ArrayList<RefinedRoute> refineRoute(ArrayList<Route> uniqueRoute){
        ArrayList<RefinedRoute> refinedRoutes = new ArrayList<RefinedRoute>();

        for(Route route:uniqueRoute) {
            RefinedRoute refinedRoute;
            ArrayList<RefinedPath> mainPathes = new ArrayList<RefinedPath>();
            ArrayList<String> relatedBusId = new ArrayList<String>();
            ArrayList<String> relatedBusName = new ArrayList<String>();
            route.getPathList().forEach(path -> {
                mainPathes.add(RefinedPathDataFactory.getInstance().createPath(path));
                relatedBusId.add(path.getRouteID());
                relatedBusName.add(path.getRouteName());
            });
            for (int i = 0; i < relatedBusId.size(); i++) {
                RelatedBusData.getInstance().add(new Bus(relatedBusId.get(i), relatedBusName.get(i)));
            }

            refinedRoute = new RefinedRoute(mainPathes, route.getDistance(), route.getTime(), relatedBusId);

            refinedRoutes.add(refinedRoute);
        }
        return refinedRoutes;
    }

    public ArrayList<RefinedRoute> getRefinedh2oRoutes() {
        return refinedh2oRoutes;
    }

    public ArrayList<RefinedRoute> getRefinedo2hRoutes() {
        return refinedo2hRoutes;
    }

}
