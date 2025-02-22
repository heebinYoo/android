package com.heebin.smartroute.busAPI.async;

import android.os.AsyncTask;

import com.heebin.smartroute.busAPI.connector.Connector;
import com.heebin.smartroute.util.async.AsyncTaskCallback;
import com.heebin.smartroute.util.constants.NomalConstants;

public class HTTPAsyncRunner extends AsyncTask<Integer, String, Integer> {

    Connector[] connector;
    AsyncTaskCallback asyncTaskCallback;

    public HTTPAsyncRunner(AsyncTaskCallback asyncTaskCallback, Connector[] connector){
        this.asyncTaskCallback = asyncTaskCallback;
        this.connector = connector;
    }



    @Override
    protected Integer doInBackground(Integer... integers) {

        for(Connector x : connector) {
            x.run();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        asyncTaskCallback.onSuccess(NomalConstants.HTTP_good);
    }
}
