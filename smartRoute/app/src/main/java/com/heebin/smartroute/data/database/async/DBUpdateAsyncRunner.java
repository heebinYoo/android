package com.heebin.smartroute.data.database.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.database.DBHelper;
import com.heebin.smartroute.data.inMemory.caching.StopBusData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.DataBaseConstatns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DBUpdateAsyncRunner extends AsyncTask<Integer, String, Integer> {

    private Context context;

    private SQLiteDatabase db;
    public DBUpdateAsyncRunner(Context context){
        this.context = context;

    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        DBHelper helper = new DBHelper(this.context);
        db = helper.getWritableDatabase();

        updateBus();


        return 0;
    }
    private void updateBus(){
        ContentValues contentValues;

        HashSet<Bus> buses = new HashSet();

        Iterator<ArrayList<Bus>> stopBusesListIterator = StopBusData.getInstance().getStopBusesListIterator();
        while(stopBusesListIterator.hasNext()){
            buses.addAll(stopBusesListIterator.next());
        }
        Iterator<Bus> busIterator = buses.iterator();
        while (busIterator.hasNext()){
            Bus bus =busIterator.next();
            contentValues = new ContentValues();
            contentValues.put(DataBaseConstatns.busId, bus.getBusId());
            contentValues.put(DataBaseConstatns.busName,bus.getBusName());
            try {
                db.insertWithOnConflict(DataBaseConstatns.bus, null, contentValues,SQLiteDatabase.CONFLICT_IGNORE);
            }
            catch (SQLiteConstraintException e){

            }
        }
    }
}
