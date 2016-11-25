package com.example.oneapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.oneapplication.R;
import com.example.oneapplication.fragment.MusicFragment;


public class EverySongDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_song_detail);

        String strId = getIntent().getStringExtra("id");

        int id = Integer.parseInt(strId);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        MusicFragment musicFragment = MusicFragment.getInstance(id);
        ft.replace(R.id.containerId_esda,musicFragment);

        ft.commit();

    }
}
