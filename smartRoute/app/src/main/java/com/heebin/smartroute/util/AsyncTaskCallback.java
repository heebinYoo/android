package com.heebin.smartroute.util;

public interface AsyncTaskCallback {

    void onSuccess(String result);
    void onFailure(Exception e);

}
