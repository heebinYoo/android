package com.heebin.smartroute.util;

import com.heebin.smartroute.bean.raw.Path;
import com.heebin.smartroute.bean.raw.Route;
import com.heebin.smartroute.bean.raw.Station;
import com.heebin.smartroute.bean.refined.RefinedPath;
import com.heebin.smartroute.bean.refined.RefinedRoute;

import java.util.ArrayList;

public class RouteRefiner {

    public static ArrayList<RefinedRoute> refine(ArrayList<Route> raw){
        return refineRoute(routeDeduplication(raw));
    }

    private static ArrayList<Route> routeDeduplication(ArrayList<Route> raw){
        ArrayList<Route> uniqueRoute = new ArrayList<Route>();

        for(int i=0; i<raw.size();i++){
            boolean isNotAdded = true;

            for(int j=0; j<uniqueRoute.size(); j++){
                isNotAdded = isNotAdded && raw.get(i).equivalents(uniqueRoute.get(j));
            }

            if(isNotAdded){
                uniqueRoute.add(raw.get(i));
            }
        }
        return uniqueRoute;
    }
    private static ArrayList<RefinedRoute> refineRoute(ArrayList<Route> uniqueRoute){
        ArrayList<RefinedRoute> refinedRoutes = new ArrayList<RefinedRoute>();

        for(Route route:uniqueRoute) {
            RefinedRoute refinedRoute;
            ArrayList<RefinedPath> mainPathes = new ArrayList<RefinedPath>();
            route.getPathList().forEach(path -> {
                mainPathes.add(new RefinedPath(path));
            });

            refinedRoute = new RefinedRoute(mainPathes, route.getDistance(), route.getTime());

            refinedRoutes.add(refinedRoute);
        }
        return refinedRoutes;
    }

}
