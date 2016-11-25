package com.example.oneapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.oneapplication.R;
import com.example.oneapplication.activity.EverySongDetailActivity;
import com.example.oneapplication.adapter.BymonthAdapter;
import com.example.oneapplication.bean.BymonthBean;
import com.example.oneapplication.utils.Constant;
import com.example.oneapplication.utils.MyApp;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BymonthFragment extends Fragment {

    private ListView listView;
    private String month;
    private BymonthBean bymonthBean;
    private ImageView ivBack;


    public static BymonthFragment getInstance(String month){
        Bundle bundle = new Bundle();
        BymonthFragment bymonthFragment = new BymonthFragment();

        bundle.putString("month",month);
        bymonthFragment.setArguments(bundle);

        return bymonthFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        month = bundle.getString("month");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bymonth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = ((ListView) view.findViewById(R.id.lv_bymonth_fragment));
        ivBack = ((ImageView) view.findViewById(R.id.iv_back_bymonth));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initData(month);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到另一个Activity中
                Intent intent = new Intent(getContext(), EverySongDetailActivity.class);
                intent.putExtra("id",bymonthBean.getData().get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData(String month) {
        //发送请求
        Request request = new Request.Builder().get()
                .url(Constant.BYMONTH_COMMON_HEAD + month + Constant.BYMONTH_COMMON_FOOT)
                .build();
        //网络连接，并且异步下载
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(),"网络请求失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获得网络请求的数据结果
                String result = response.body().string();
                //对数据结果进行json解析
                bymonthBean = new Gson().fromJson(result, BymonthBean.class);

                final BymonthAdapter adapter = new BymonthAdapter(getContext(), bymonthBean);

                //在网络线程中是不可以直接修改UI控件的
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(adapter);
                    }
                });

            }
        });
    }
}
