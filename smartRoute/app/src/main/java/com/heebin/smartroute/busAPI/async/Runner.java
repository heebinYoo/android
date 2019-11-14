package com.heebin.smartroute.busAPI.async;

import android.os.AsyncTask;

import com.heebin.smartroute.busAPI.connector.Connector;

public class Runner extends AsyncTask<Integer, String, Integer> {

    Connector connector;
    AsyncTaskCallback asyncTaskCallback;

    public Runner(AsyncTaskCallback asyncTaskCallback, Connector connector){
        this.asyncTaskCallback = asyncTaskCallback;
        this.connector = connector;


    }



    @Override
    protected Integer doInBackground(Integer... integers) {


        connector.run();

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        asyncTaskCallback.onSuccess("good");
    }
}
