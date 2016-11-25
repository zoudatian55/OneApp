package com.example.oneapplication.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.adapter.GridRvAdapter;
import com.example.oneapplication.bean.HomeBean;
import com.example.oneapplication.utils.MyApp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class HomeGridActivity extends AppCompatActivity implements GridRvAdapter.GridItemClickedCallback {

    private ImageView loading;
    private AnimationDrawable anim;
    private String dateUrl;
    private String date;
    private RecyclerView recyclerView;
    private List<HomeBean.DataBean> dataBeen;
    private String url;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GridRvAdapter adapter = new GridRvAdapter(HomeGridActivity.this, dataBeen, date);
            adapter.setCallback(HomeGridActivity.this);
            recyclerView.setAdapter(adapter);
            anim.stop();
            loading.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_grid);
        Intent intent = getIntent();
        dateUrl = intent.getStringExtra("dateUrl");
        date = intent.getStringExtra("date");
        url = "http://v3.wufazhuce.com:8000/api/hp/bymonth/"+dateUrl+"%2000:00:00?version=3.5.0&platform=android";
        recyclerView = (RecyclerView) findViewById(R.id.grid_home);
        TextView dateText = (TextView) findViewById(R.id.date_toolbar);
        loading = (ImageView) findViewById(R.id.loading_home_grid);
        anim = (AnimationDrawable) loading.getBackground();
        anim.start();
        dateText.setText(date);
        ImageView imageView = (ImageView) findViewById(R.id.home_grid_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        getDataFromNetWork(url);
    }

    //发送网络请求
    private void getDataFromNetWork(String url) {
        Request request = new Request.Builder().get().url(url).build();
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                HomeBean homeBean = new Gson().fromJson(result,HomeBean.class);
                dataBeen = homeBean.getData();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void GridItemClicked(int position) {
        Intent intent = new Intent(this,GridActivity.class);
        String maketime = (dataBeen.size()-position)+" "+date;
        intent.putExtra("dataBean",dataBeen.get(position));
        intent.putExtra("date",maketime);
        startActivity(intent);
    }
}
