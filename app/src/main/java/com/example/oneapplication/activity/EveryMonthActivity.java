package com.example.oneapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.oneapplication.R;
import com.example.oneapplication.fragment.BymonthFragment;

public class EveryMonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_month);

        int position = getIntent().getIntExtra("month",-1);
        int month = position +1;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (month < 10){
            BymonthFragment bymonthFragment = BymonthFragment.getInstance("0"+month);
            ft.replace(R.id.containerId,bymonthFragment);
        }else {
            BymonthFragment bymonthFragment = BymonthFragment.getInstance(month+"");
            ft.replace(R.id.containerId,bymonthFragment);
        }

        ft.commit();


    }
}
