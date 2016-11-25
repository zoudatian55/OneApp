package com.example.oneapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.adapter.RvAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeListActivity extends AppCompatActivity implements RvAdapter.ItemClickCallback {

    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    private List<String> listDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        recyclerView = (RecyclerView) findViewById(R.id.home_list);
        ImageView imageView = (ImageView) findViewById(R.id.home_list_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        RvAdapter adapter = new RvAdapter(this,list);
        adapter.setItemClickCallback(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    //将接口日期(如2016-10-01)和列表显示日期（如：Oct.2016）分别加入list和listdate中
    private void initData() {
        DateFormat df1 = new SimpleDateFormat("MM");
        String nowMon = df1.format(new Date());
        int mon = Integer.parseInt(nowMon);
        DateFormat df2 = new SimpleDateFormat("yyyy");
        String nowYear = df2.format(new Date());
        int year = Integer.parseInt(nowYear);
        for (int i = year;i>2011;i--){
            for (int j = 12;j>0;j--){
                if (list.size() == 0){
                    j = mon;
                    listDate.add(i+"-"+j+"-"+"01");
                    list.add("本月");
                    continue;
                }else if (i == 2012){
                    if (j<10){
                        break;
                    }
                }
                listDate.add(i+"-"+j+"-"+"01");
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM");
                    Date date = sdf.parse(j+"");
                    sdf = new SimpleDateFormat("MMM",Locale.ENGLISH);
                    String text = sdf.format(date) + "."+i;
                    list.add(text);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //接口回调，跳转到相对应Item的Activity下
    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this,HomeGridActivity.class);
        intent.putExtra("dateUrl",listDate.get(position));
        intent.putExtra("date",list.get(position));
        startActivity(intent);
    }
}
