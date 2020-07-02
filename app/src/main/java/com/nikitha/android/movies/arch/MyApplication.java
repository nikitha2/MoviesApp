package com.nikitha.android.movies.arch;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//singleton class
public  class MyApplication extends Application {
    private static MyApplication myApplication;
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static MyApplication getInstance() {
        if (myApplication==null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

}
