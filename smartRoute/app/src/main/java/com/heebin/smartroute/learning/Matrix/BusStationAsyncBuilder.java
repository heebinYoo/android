package com.heebin.smartroute.learning.Matrix;

import android.content.Context;
import android.os.AsyncTask;

import com.heebin.smartroute.data.bean.refined.BusStationMatrix;
import com.heebin.smartroute.data.bean.refined.RefinedRoute;
import com.heebin.smartroute.data.database.async.DBUpdateAsyncRunner;
import com.heebin.smartroute.util.async.AsyncTaskCallback;

public class BusStationAsyncBuilder extends AsyncTask <Integer, String, Integer>{

    public RefinedRoute refinedRoute;

   public AsyncTaskCallback asyncTaskCallback;

    public BusStationAsyncBuilder(RefinedRoute refinedRoute, Context context, AsyncTaskCallback asyncTaskCallback){
        this.refinedRoute =refinedRoute;
        this.asyncTaskCallback=asyncTaskCallback;

    }
    @Override
    protected Integer doInBackground(Integer... integer) {
        BusStationMatrixHolder.Hold( new BusStationMatrix(refinedRoute));

        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        asyncTaskCallback.onSuccess("good");
    }
}
