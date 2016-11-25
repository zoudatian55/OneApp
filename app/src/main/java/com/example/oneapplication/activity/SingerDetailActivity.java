package com.example.oneapplication.activity;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.oneapplication.R;
import com.example.oneapplication.bean.MusicBean;
import com.example.oneapplication.fragment.SingerDetailFragment;

import java.io.Serializable;

public class SingerDetailActivity extends AppCompatActivity {


    private MusicBean musicBean;
    private SingerDetailFragment singerDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_detail);

        musicBean = ((MusicBean) getIntent().getSerializableExtra("data"));
        singerDetailFragment = SingerDetailFragment.getInstance(musicBean);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.containerId,singerDetailFragment);
        //提交事务
        transaction.commit();
    }
}
