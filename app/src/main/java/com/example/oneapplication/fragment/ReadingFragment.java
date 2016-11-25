package com.example.oneapplication.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.adapter.ReadingIndexAdapter;
import com.example.oneapplication.bean.ReadingCarouselBean;
import com.example.oneapplication.bean.ReadingIndexBean;
import com.example.oneapplication.utils.Constant;
import com.example.oneapplication.utils.MyApp;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class ReadingFragment extends Fragment {

    private ViewPager imagePager;
    private ViewPager readingPager;
    private List<ReadingCarouselBean.DataBean> imageList = new ArrayList<>();
    private ReadingIndexBean.DataBean dataBean;
    private LinearLayout dotContainer;

    private Handler setImageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (imagePager.getCurrentItem() < imageList.size()-1){
                imagePager.setCurrentItem(imagePager.getCurrentItem()+1);
            }else {
                imagePager.setCurrentItem(0);
            }
            setImageHandler.sendEmptyMessageDelayed(0,3000);
        }
    };

    private Handler imageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initDot();
            imagePager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return imageList.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.reading_image_layout,null);
                    Picasso.with(imageView.getContext()).load(imageList.get(position).getCover()).into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
            setImageHandler.sendEmptyMessage(0);
        }
    };

    private Handler indexHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ReadingIndexAdapter adapter = new ReadingIndexAdapter(getActivity(),dataBean);
            readingPager.setAdapter(adapter);
            readingPager.setCurrentItem(1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading,null);
        TextView title = (TextView) view.findViewById(R.id.text_main);
        title.setText("阅读");
        imagePager = (ViewPager) view.findViewById(R.id.image_pager);
        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0;i<dotContainer.getChildCount();i++){
                    if (i == position){
                        dotContainer.getChildAt(i).setEnabled(false);
                    }else {
                        dotContainer.getChildAt(i).setEnabled(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        readingPager = (ViewPager) view.findViewById(R.id.read_pager);
        readingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    readingPager.setCurrentItem(1);
                    Toast.makeText(getActivity(), "正在刷新", Toast.LENGTH_SHORT).show();
                    getImageNetWork();
                    getIndexNetWork();
                }else if (position == 11){
                    readingPager.setCurrentItem(10);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        dotContainer = (LinearLayout) view.findViewById(R.id.dot_container);
        getImageNetWork();
        getIndexNetWork();
        return view;
    }

    private void getIndexNetWork() {
        Request request = new Request.Builder().get().url(Constant.READING_INDEX_URL).build();
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                ReadingIndexBean indexBean = new Gson().fromJson(result,ReadingIndexBean.class);
                dataBean = indexBean.getData();
                indexHandler.sendEmptyMessage(0);
            }
        });
    }

    private void initDot() {
        dotContainer.removeAllViews();
        for (int i = 0;i < imageList.size();i++){
            ImageView dotImage = new ImageView(getActivity());
            dotImage.setBackgroundResource(R.drawable.guide_index_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20,0,20,0);
            dotImage.setLayoutParams(layoutParams);
            if (i == 0){
                dotImage.setEnabled(false);
            }else {
                dotImage.setEnabled(true);
            }
            dotContainer.addView(dotImage);
        }
    }

    public void getImageNetWork() {
        Request request = new Request.Builder().get().url(Constant.READING_IMAGE_URL).build();
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                ReadingCarouselBean carouseBean = new Gson().fromJson(result,ReadingCarouselBean.class);
                imageList = carouseBean.getData();
                imageHandler.sendEmptyMessage(0);
            }
        });
    }
}
