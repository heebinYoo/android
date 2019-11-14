package com.heebin.smartroute.busAPI.async;

import android.os.AsyncTask;

import com.heebin.smartroute.busAPI.parser.StationSearcher;

public class StationSearcherRunner extends AsyncTask<Integer, String, Integer> {

    StationSearcher ss;
    AsyncTaskCallback asyncTaskCallback;

    public StationSearcherRunner(AsyncTaskCallback asyncTaskCallback){
        this.asyncTaskCallback = asyncTaskCallback;



    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ss = new StationSearcher();



    }


    @Override
    protected Integer doInBackground(Integer... integers) {


        ss.run();

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        asyncTaskCallback.onSuccess("good");
    }
}
