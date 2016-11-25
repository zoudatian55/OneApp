package com.example.oneapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import com.example.oneapplication.R;
import com.example.oneapplication.adapter.MonthListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MonthListActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_list);

        listView = ((ListView) findViewById(R.id.lv_monthlist));
        ivBack = ((ImageView) findViewById(R.id.iv_back_monthList));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        MonthListAdapter adapter = new MonthListAdapter(this,list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonthListActivity.this,EveryMonthActivity.class);
                intent.putExtra("month",position);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            list.add("2016-"+i);
        }
    }
}
