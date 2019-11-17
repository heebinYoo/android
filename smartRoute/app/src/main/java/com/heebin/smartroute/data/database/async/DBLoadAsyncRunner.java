package com.heebin.smartroute.data.database.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.bean.raw.Station;
import com.heebin.smartroute.data.bean.refined.RefinedPath;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;
import com.heebin.smartroute.data.database.DBHelper;
import com.heebin.smartroute.data.inMemory.caching.StopBusData;
import com.heebin.smartroute.data.inMemory.caching.WayPointData;
import com.heebin.smartroute.data.inMemory.initRelated.RefinedPathDataFactory;
import com.heebin.smartroute.data.inMemory.userData.LodgmentData;
import com.heebin.smartroute.data.inMemory.userData.RefinedRouteData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.DataBaseConstatns;

import java.util.ArrayList;

import static com.heebin.smartroute.util.constants.NomalConstants.DB_LOAD_good;


public class DBLoadAsyncRunner extends AsyncTask<Integer, String, Integer> {

    private Context context;
    private AsyncTaskCallback asyncTaskCallback;
    private SQLiteDatabase db;

    public DBLoadAsyncRunner(Context context, AsyncTaskCallback asyncTaskCallback){
        this.context = context;
        this.asyncTaskCallback = asyncTaskCallback;
        DBHelper helper = new DBHelper(this.context);
        db = helper.getReadableDatabase();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        setLodgment();
        setWayPoint();
        setStopBus();
        setRoute();
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        asyncTaskCallback.onSuccess(DB_LOAD_good);

    }


   private void setLodgment(){
       SharedPreferences sharedPreferences = context.getSharedPreferences("init", Context.MODE_PRIVATE);

       LodgmentData.getInstance().setHome(
               Double.parseDouble(  sharedPreferences.getString("homeLat","")),
               Double.parseDouble( sharedPreferences.getString("homeLong","")));

       LodgmentData.getInstance().setOffice(
               Double.parseDouble( sharedPreferences.getString("officeLat","")),
               Double.parseDouble(  sharedPreferences.getString("officeLong","")));


   }
    private void setWayPoint(){
        Cursor c = db.query(DataBaseConstatns.waypoint, new String[]{DataBaseConstatns.busId},null,null,null,null,null);
        ArrayList<String> busIdList = new ArrayList();
        while(c.moveToNext()){
            busIdList.add(c.getString(0));
        }
        for (String s : busIdList) {
            ArrayList<Station> stations = new ArrayList<>();
            c = db.query(DataBaseConstatns.waypoint + " ," + DataBaseConstatns.station, new String[]{DataBaseConstatns.stationId, DataBaseConstatns.stationName, DataBaseConstatns.gpsX, DataBaseConstatns.gpsY, "waypoint."+DataBaseConstatns.arsId},
                    DataBaseConstatns.busId + "=? and " + DataBaseConstatns.waypoint + "." + DataBaseConstatns.arsId + "=" + DataBaseConstatns.station + "." + DataBaseConstatns.arsId, new String[]{s}, null,null, null);
            while(c.moveToNext()){
              Station station = new Station(c.getString(0),c.getString(1), c.getDouble(2), c.getDouble(3), c.getString(4));
                stations.add(station);
            }
            WayPointData.getInstance().add(s, stations);
        }

    }
    private void setStopBus(){

        Cursor c = db.query(DataBaseConstatns.stopbus, new String[]{DataBaseConstatns.arsId},null,null,null,null,null);
        ArrayList<String> ardIdLiat = new ArrayList();
        while(c.moveToNext()){
            ardIdLiat.add(c.getString(0));
        }
        for (String s : ardIdLiat) {
            ArrayList<Bus> buses = new ArrayList<>();
            c = db.query(DataBaseConstatns.stopbus + " ," + DataBaseConstatns.bus, new String[]{DataBaseConstatns.busId, DataBaseConstatns.busName},
                    DataBaseConstatns.arsId + "=? and " + DataBaseConstatns.stopbus + "." + DataBaseConstatns.busId + "=" + DataBaseConstatns.bus + "." + DataBaseConstatns.busId, new String[]{s}, null,null, null);
            while(c.moveToNext()){
                Bus bus = new Bus(c.getString(0),c.getString(1));
                buses.add(bus);
            }
            StopBusData.getInstance().add(s, buses);
        }



    }
    private void setRoute(){
        Cursor h2o = db.query(DataBaseConstatns.route, new String[]{DataBaseConstatns.rid, DataBaseConstatns.estimatedDistance, DataBaseConstatns.estimatedTime},
                 DataBaseConstatns.type +"=?", new String[]{DataBaseConstatns.TYPE_H2O+""},null,null,null);
        Cursor o2h = db.query(DataBaseConstatns.route, new String[]{DataBaseConstatns.rid, DataBaseConstatns.estimatedDistance, DataBaseConstatns.estimatedTime},
                DataBaseConstatns.type +"=?", new String[]{DataBaseConstatns.TYPE_O2H+""},null,null,null);


        ArrayList<RefinedRoute> refinedh2oRoutes = new ArrayList<RefinedRoute>();
        ArrayList<RefinedRoute> refinedo2hRoutes = new ArrayList<RefinedRoute>();

        while (h2o.moveToNext()){
            int rid =h2o.getInt(0);
            int estimatedDistance= h2o.getInt(1);
            int estimatedTime = h2o.getInt(2);
            ArrayList<Station> detailStations = new ArrayList<Station>();
            ArrayList<RefinedPath> mainPathes = new ArrayList<RefinedPath>();

            Cursor stations = db.rawQuery(
                    "select station.stationId, station.stationName, station.gpsX, station.gpsY, station.arsId" +
                            " from station inner join detailedstation on (station.arsId = detailedstation.arsId)" +
                            " where detailedstation.rid = ?" +
                            "order by detailedstation.idx asc", new String[]{rid+""});
            while(stations.moveToNext()){
                detailStations.add(new Station(stations.getString(0), stations.getString(1), stations.getDouble(2), stations.getDouble(3), stations.getString(4)));

            }
            Cursor pathes = db.rawQuery("select path.takeId, path.takeName, path.takeX, path.takeY, path.offId, path.offName, path.offX, path.offY " +
                    "from path inner join mainpath on(path.takeId=mainpath.takeId and path.offId = mainpath.offId)" +
                    "where mainpath.rid=?" +
                    "order by mainpath.idx asc", new String[]{rid+""});
            while(pathes.moveToNext()){
                mainPathes.add(RefinedPathDataFactory.getInstance().createPath(pathes.getString(0),pathes.getString(1),pathes.getDouble(2),pathes.getDouble(3),
                        pathes.getString(4),pathes.getString(5),pathes.getDouble(6),pathes.getDouble(7)));
            }
            refinedh2oRoutes.add(new RefinedRoute(mainPathes, estimatedDistance,estimatedTime,detailStations, 0));


        }

        while (o2h.moveToNext()){
            int rid =o2h.getInt(0);
            int estimatedDistance= o2h.getInt(1);
            int estimatedTime = o2h.getInt(2);
            ArrayList<Station> detailStations = new ArrayList<Station>();
            ArrayList<RefinedPath> mainPathes = new ArrayList<RefinedPath>();

            Cursor stations = db.rawQuery(
                    "select station.stationId, station.stationName, station.gpsX, station.gpsY, station.arsId" +
                    " from station inner join detailedstation on (station.arsId = detailedstation.arsId)" +
                    " where detailedstation.rid = ?" +
                    "order by detailedstation.idx asc", new String[]{rid+""});
            while(stations.moveToNext()){
                detailStations.add(new Station(stations.getString(0), stations.getString(1), stations.getDouble(2), stations.getDouble(3), stations.getString(4)));

            }
            Cursor pathes = db.rawQuery("select path.takeId, path.takeName, path.takeX, path.takeY, path.offId, path.offName, path.offX, path.offY " +
                    "from path inner join mainpath on(path.takeId=mainpath.takeId and path.offId = mainpath.offId)" +
                    "where mainpath.rid=?" +
                    "order by mainpath.idx asc", new String[]{rid+""});
            while(pathes.moveToNext()){
                mainPathes.add(RefinedPathDataFactory.getInstance().createPath(pathes.getString(0),pathes.getString(1),pathes.getDouble(2),pathes.getDouble(3),
                        pathes.getString(4),pathes.getString(5),pathes.getDouble(6),pathes.getDouble(7)));
            }
            refinedo2hRoutes.add(new RefinedRoute(mainPathes, estimatedDistance,estimatedTime,detailStations, 0));

        }

        RefinedRouteData.getInstance().setDataFromDB(refinedh2oRoutes,refinedo2hRoutes);


        }


}
