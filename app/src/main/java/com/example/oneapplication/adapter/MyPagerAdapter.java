package com.example.oneapplication.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.HomeBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 轩 on 2016/10/31.
 */

public class MyPagerAdapter extends PagerAdapter {

    public void setCallback(HomeImageClickedCallback callback) {
        this.callback = callback;
    }

    public interface HomeImageClickedCallback{
        public void HomeImageClicked(Drawable drawable);
    }

    private HomeImageClickedCallback callback;

    private Context context;
    private LayoutInflater inflater;
    private List<HomeBean.DataBean> list = new ArrayList<>();
    private int layoutId;
    private int variableId;

    public MyPagerAdapter(Context context, List<HomeBean.DataBean> list, int layoutId, int variableId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //第0页位刷新页面，第11页位跳转页面
        if (position == 0){
            View view = inflater.inflate(R.layout.right_refresh,null);
            container.addView(view);
            return view;
        }else if (position == 11){
            View view = inflater.inflate(R.layout.left_skip,null);
            container.addView(view);
            return view;
        }else {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, null, false);
            binding.setVariable(variableId, list.get(position));
            final ImageView imageView = (ImageView) binding.getRoot().findViewById(R.id.image_home);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.HomeImageClicked(imageView.getDrawable());
                    }
                }
            });
            container.addView(binding.getRoot());
            return binding.getRoot();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
