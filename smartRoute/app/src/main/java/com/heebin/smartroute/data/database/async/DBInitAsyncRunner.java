package com.heebin.smartroute.data.database.async;

import android.content.ContentValues;
import android.content.Context;
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
import com.heebin.smartroute.data.inMemory.userData.RefinedRouteData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.DataBaseConstatns;
import com.heebin.smartroute.util.constants.NomalConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DBInitAsyncRunner extends AsyncTask<Integer, String, Integer> {
    private Context context;
    private AsyncTaskCallback asyncTaskCallback;
    private SQLiteDatabase db;
    public DBInitAsyncRunner(Context context, AsyncTaskCallback asyncTaskCallback){
        this.context = context;
        this.asyncTaskCallback = asyncTaskCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Integer doInBackground(Integer... integers) {
        DBHelper helper = new DBHelper(this.context);
         db = helper.getWritableDatabase();

        insertStation();
        insertBus();
        insertWayPoint();
        insertStopBus();
        insertPath();
        insertRoute();

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        asyncTaskCallback.onSuccess(NomalConstants.DB_good);
    }
    private void insertStation(){
        ContentValues contentValues;

        Iterator<ArrayList<Station>> stationsListIteratoritr = WayPointData.getInstance().getStationsListIterator();
        HashSet<Station> stations = new HashSet();
        while(stationsListIteratoritr.hasNext()){
            stations.addAll(stationsListIteratoritr.next());
        }
       Iterator<Station> stationIterator = stations.iterator();
        while (stationIterator.hasNext()){
            Station station  = stationIterator.next();
            contentValues = new ContentValues();
            contentValues.put(DataBaseConstatns.stationId, station.getStationId());
            contentValues.put(DataBaseConstatns.stationName, station.getStationName());
            contentValues.put(DataBaseConstatns.gpsX, station.getGpsX());
            contentValues.put(DataBaseConstatns.gpsY, station.getGpsY());
            contentValues.put(DataBaseConstatns.arsId, station.getArsId());
            db.insert(DataBaseConstatns.station,null, contentValues);
        }
    }
    private void insertBus(){
        ContentValues contentValues;
        Iterator<ArrayList<Bus>> stopBusesListIterator = StopBusData.getInstance().getStopBusesListIterator();
        HashSet<Bus> buses = new HashSet();
        while(stopBusesListIterator.hasNext()){
            buses.addAll(stopBusesListIterator.next());
        }
        Iterator<Bus> busIterator = buses.iterator();
        while (busIterator.hasNext()){
            Bus bus =busIterator.next();
            contentValues = new ContentValues();
            contentValues.put(DataBaseConstatns.busId, bus.getBusId());
            contentValues.put(DataBaseConstatns.busName,bus.getBusName());
            db.insert(DataBaseConstatns.bus,null, contentValues);
        }
    }
    private void insertPath(){
        ContentValues contentValues;

        for (RefinedRoute route : RefinedRouteData.getInstance().getRefinedh2oRoutes()) {
            for (RefinedPath path : route.getMainPathes()) {
                contentValues = new ContentValues();
                contentValues.put(DataBaseConstatns.takeId, path.getTakeId());
                contentValues.put(DataBaseConstatns.takeName, path.getTakeName());
                contentValues.put(DataBaseConstatns.takeX, path.getTakeX());
                contentValues.put(DataBaseConstatns.takeY, path.getTakeY());
                contentValues.put(DataBaseConstatns.offId, path.getOffId());
                contentValues.put(DataBaseConstatns.offName, path.getOffName());
                contentValues.put(DataBaseConstatns.offX, path.getOffX());
                contentValues.put(DataBaseConstatns.offY, path.getOffY());
                db.insert(DataBaseConstatns.path,null, contentValues);
            }
        }
    }

    private void insertRoute(){
        ContentValues contentValues;
        for (RefinedRoute route : RefinedRouteData.getInstance().getRefinedh2oRoutes()) {
            contentValues = new ContentValues();
            contentValues.put(DataBaseConstatns.estimatedDistance, route.getEstimatedDistance());
            contentValues.put(DataBaseConstatns.estimatedTime, route.getEstimatedTime());
            contentValues.put(DataBaseConstatns.type, DataBaseConstatns.TYPE_H2O);
            db.insert(DataBaseConstatns.route,null, contentValues);
            int routePrime = getHighestID(DataBaseConstatns.route);

            for (int i = 0; i < route.getDetailStations().size(); i++) {
                Station station = route.getDetailStations().get(i);
                contentValues = new ContentValues();
                contentValues.put(DataBaseConstatns.rid, routePrime);
                contentValues.put(DataBaseConstatns.arsId, station.getArsId());
                contentValues.put(DataBaseConstatns.idx, i);
            }

            for (int i = 0; i < route.getMainPathes().size(); i++) {
                RefinedPath path = route.getMainPathes().get(i);
                contentValues = new ContentValues();
                contentValues.put(DataBaseConstatns.rid, routePrime);
                contentValues.put(DataBaseConstatns.takeId, path.getTakeId());
                contentValues.put(DataBaseConstatns.offId, path.getOffId());
                contentValues.put(DataBaseConstatns.idx, i);
            }

        }

    }

    public int getHighestID(String tableName) {
        final String MY_QUERY = "SELECT MAX(_id) FROM " + tableName;
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        cur.close();
        return ID;
    }



    private void insertWayPoint(){
        ContentValues contentValues;
       Iterator<String> keyitr =  WayPointData.getInstance().getWayPoint().keySet().iterator();
        while (keyitr.hasNext()){
            String busId = keyitr.next();
            ArrayList<Station> stations = WayPointData.getInstance().getWayPoint().get(busId);
            for (Station station : stations) {
                contentValues = new ContentValues();
                contentValues.put(DataBaseConstatns.busId, busId);
                contentValues.put(DataBaseConstatns.arsId, station.getArsId());
                db.insert(DataBaseConstatns.waypoint,null, contentValues);
            }
        }
    }
    private void insertStopBus(){
        ContentValues contentValues;
        Iterator<String> keyitr =  StopBusData.getInstance().getStopBus().keySet().iterator();
        while (keyitr.hasNext()){
            String arsId = keyitr.next();
            ArrayList<Bus> stations = StopBusData.getInstance().getStopBus().get(arsId);
            for (Bus bus : stations) {
                contentValues = new ContentValues();
                contentValues.put(DataBaseConstatns.arsId, arsId);
                contentValues.put(DataBaseConstatns.busId, bus.getBusId());
                db.insert(DataBaseConstatns.stopbus,null, contentValues);
            }
        }
    }

}
