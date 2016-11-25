package com.example.oneapplication.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by 轩 on 2016/10/29.
 */

public class MyApp extends Application {

    public static final String SHREF_NAME = "oneconfig";
    public static final String KEY_ISFIRSTLOGIN = "firstlogin";
    public static SharedPreferences sharedPreferences;
    public static OkHttpClient okHttpClient;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initShref();
        initOkHttpConfig();
    }

    private void initOkHttpConfig() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    private void initShref() {
        sharedPreferences = getSharedPreferences(SHREF_NAME, Context.MODE_PRIVATE);
    }
    public static boolean firstLogin(){
        boolean value = sharedPreferences.getBoolean(KEY_ISFIRSTLOGIN,true);
        if (value){
            sharedPreferences.edit().putBoolean(KEY_ISFIRSTLOGIN,false).apply();
        }
        return value;
    }
}
