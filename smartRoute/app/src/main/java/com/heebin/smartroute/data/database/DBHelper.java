package com.heebin.smartroute.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.heebin.smartroute.util.constants.DataBaseConstatns;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, DataBaseConstatns.DB_NAME, null, DataBaseConstatns.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(DataBaseConstatns.create_station);
        db.execSQL(DataBaseConstatns.create_bus);
        db.execSQL(DataBaseConstatns.create_waypoint);
        db.execSQL(DataBaseConstatns.create_stopbus);
        db.execSQL(DataBaseConstatns.create_path);
        db.execSQL(DataBaseConstatns.create_route);
        db.execSQL(DataBaseConstatns.create_detailedstation);
        db.execSQL(DataBaseConstatns.create_mainpath);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_ver, int new_ver) {
        if(new_ver == DataBaseConstatns.DB_VERSION){
            //not cover.
        }
    }
}
