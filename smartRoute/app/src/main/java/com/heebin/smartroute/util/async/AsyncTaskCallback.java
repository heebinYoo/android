package com.heebin.smartroute.util.async;

public interface AsyncTaskCallback {

    void onSuccess(String result);
    void onFailure(Exception e);

}
