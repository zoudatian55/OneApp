package com.example.oneapplication.service;

import com.example.oneapplication.bean.HomeBean;
import com.example.oneapplication.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by 轩 on 2016/10/31.
 */

public interface HomeService {
    @GET(Constant.HOME_MONTH_URL)
    Call<HomeBean> getHomeData(@Query("version") String version,@Query("platform") String platform);
}
