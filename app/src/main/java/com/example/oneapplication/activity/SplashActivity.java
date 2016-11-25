package com.example.oneapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.oneapplication.R;
import com.example.oneapplication.utils.MyApp;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if (MyApp.firstLogin()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
