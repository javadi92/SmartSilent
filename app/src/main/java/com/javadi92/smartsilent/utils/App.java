package com.javadi92.smartsilent.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {

    public static SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences=getApplicationContext().getSharedPreferences("my_shared",MODE_PRIVATE);
    }
}
