package com.heebin.smartroute.data.database.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.heebin.smartroute.data.bean.meta.StopBus;
import com.heebin.smartroute.data.bean.raw.Bus;
import com.heebin.smartroute.data.database.DBHelper;
import com.heebin.smartroute.data.inMemory.caching.StopBusData;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.DataBaseConstatns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DBInsertStopBusAsyncRunner extends AsyncTask<StopBus, String, Integer> {
    private Context context;

    private SQLiteDatabase db;
    public DBInsertStopBusAsyncRunner(Context context){
        this.context = context;



    }
    @Override
    protected Integer doInBackground(StopBus... stopBuses) {
        DBHelper helper = new DBHelper(this.context);
        db = helper.getWritableDatabase();

        for (StopBus stopBus : stopBuses) {
            updateBus(stopBus);
        }
        return 0;
    }

    private void updateBus(StopBus stopBus){
        ContentValues contentValues;

        for (Bus bus : stopBus.busArrayList) {
            contentValues = new ContentValues();
            contentValues.put(DataBaseConstatns.busId, bus.getBusId());
            contentValues.put(DataBaseConstatns.arsId, stopBus.arsId);
            db.insert(DataBaseConstatns.bus,null, contentValues);
        }


    }
}
