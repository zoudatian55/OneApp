package com.example.oneapplication.utils;

import android.os.Handler;
import android.os.Message;

import com.example.oneapplication.bean.HomeBean;
import com.example.oneapplication.service.HomeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 轩 on 2016/10/31.
 */

public class HomeRetrofitUtils {

    private static HomeRetrofitUtils instance;
    private Retrofit retrofit;

    public static HomeRetrofitUtils getInstance(){
        if (instance == null){
            instance = new HomeRetrofitUtils();
        }
        return instance;
    }

    private HomeRetrofitUtils(){
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://v3.wufazhuce.com:8000").build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
    public static void getHomeData(final Handler handler){
        HomeService homeService = getInstance().getRetrofit().create(HomeService.class);
        Call<HomeBean> dataBeanCall = homeService.getHomeData(Constant.VERSON,"android");
        dataBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                HomeBean dataBean = response.body();
                Message message = handler.obtainMessage();
                message.obj = dataBean;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }
}
