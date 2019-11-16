package com.heebin.smartroute.busAPI.async;

public interface AsyncTaskCallback {

    void onSuccess(String result);
    void onFailure(Exception e);

}
