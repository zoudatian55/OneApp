package com.example.oneapplication.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.fragment.HomeFragment;
import com.example.oneapplication.fragment.HomeMusicFragment;
import com.example.oneapplication.fragment.MovieFragment;
import com.example.oneapplication.fragment.ReadingFragment;
import com.example.oneapplication.utils.MyCustomTabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private  boolean flag = true;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ImageView search;
    private ImageView individual;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flag = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabs();
        search = (ImageView) findViewById(R.id.search_main);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
            }
        });
        individual = (ImageView) findViewById(R.id.indiv_main);
        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "individual", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (flag){
                flag = false;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(0,2000);
            }else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //使用FlycoTabLayout制作底部的选项卡
    private void initTabs() {
        CommonTabLayout tabLayout = (CommonTabLayout) findViewById(R.id.tabLayout_main);

        ArrayList<CustomTabEntity> tabEntitys = new ArrayList<>();

        tabEntitys.add(new MyCustomTabEntity(null,R.drawable.home,R.drawable.home_active));
        tabEntitys.add(new MyCustomTabEntity(null,R.drawable.reading,R.drawable.reading_active));
        tabEntitys.add(new MyCustomTabEntity(null,R.drawable.music,R.drawable.music_active));
        tabEntitys.add(new MyCustomTabEntity(null,R.drawable.movie,R.drawable.movie_active));

        initFragments();

        //把fragment添加到容器中，并让选项卡与碎片关联
        tabLayout.setTabData(tabEntitys,this,R.id.container_main,fragments);
    }

    //Fragment初始化
    private void initFragments() {
        fragments.add(new HomeFragment());
        fragments.add(new ReadingFragment());
        fragments.add(new HomeMusicFragment());
        fragments.add(new MovieFragment());
    }
}
