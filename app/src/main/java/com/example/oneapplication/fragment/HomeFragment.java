package com.example.oneapplication.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oneapplication.BR;
import com.example.oneapplication.Home;
import com.example.oneapplication.R;
import com.example.oneapplication.activity.HomeListActivity;
import com.example.oneapplication.activity.MainActivity;
import com.example.oneapplication.adapter.MyPagerAdapter;
import com.example.oneapplication.bean.HomeBean;
import com.example.oneapplication.utils.HomeRetrofitUtils;

import java.util.List;


public class HomeFragment extends Fragment implements MyPagerAdapter.HomeImageClickedCallback {

    private Home home;
    private ViewPager viewPager;
    private ImageView loading;
    private AnimationDrawable anim;
    //private AlertDialog.Builder dialog;
    List<HomeBean.DataBean> dataBeen;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HomeBean homeBean = (HomeBean) msg.obj;
            dataBeen = homeBean.getData();
            MyPagerAdapter adapter = new MyPagerAdapter(getContext(),dataBeen,R.layout.home_pager_layout, BR.home_data);
            adapter.setCallback(HomeFragment.this);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(1);
            anim.stop();
            loading.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        home = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        viewPager = home.viewPager;
        loading = home.loadingHome;


        anim = (AnimationDrawable) loading.getBackground();
        anim.start();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    viewPager.setCurrentItem(1);
                    Toast.makeText(getActivity(), "正在刷新", Toast.LENGTH_SHORT).show();
                    HomeRetrofitUtils.getHomeData(handler);
                }else if (position == 11){
                    viewPager.setCurrentItem(10);
                    startActivity(new Intent(getActivity(),HomeListActivity.class));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        home.setHome(HomeFragment.this);
        HomeRetrofitUtils.getHomeData(handler);
        return home.getRoot();
    }
    //设置dialog
    @Override
    public void HomeImageClicked(Drawable drawable) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        ImageView homeImage = new ImageView(getActivity());
        homeImage.setImageDrawable(drawable);
        homeImage.setAdjustViewBounds(true);
        dialog.setView(homeImage);
        dialog.show();
    }
}