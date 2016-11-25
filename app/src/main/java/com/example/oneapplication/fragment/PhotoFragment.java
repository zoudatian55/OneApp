package com.example.oneapplication.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.oneapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PhotoFragment extends Fragment {


    private LinearLayout linearlayout;
    private int position;
    private ArrayList<String> photos;
    private List<View> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        linearlayout = ((LinearLayout) view.findViewById(R.id.container));
        Bundle bundle = getArguments();
        if (bundle != null) {
            photos = bundle.getStringArrayList("photo");

            final int size = photos.size();
            for (int i = 0; i < size; i++) {
                View inflate = inflater.inflate(R.layout.fragment_photo_image, null);
                final ImageView imageView = (ImageView) inflate.findViewById(R.id.item_image);

                imageView.setTag(i);
                list.add(imageView);
                Picasso.with(getContext()).load(photos.get(i)).into(imageView);
                linearlayout.addView(inflate);
                linearlayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
                        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
                       ImageView img = (ImageView) imgEntryView.findViewById(R.id.image_large);

//这里是获取网络图片，可能没法用。可自行更改成自己的图片
                        int pos = (Integer) imageView.getTag();
                        Picasso.with(getContext()).load(photos.get(pos)).into(img);
                        dialog.setView(imgEntryView); // 自定义dialog
                        dialog.show();
// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                        imgEntryView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View paramView) {
                                dialog.cancel();
                            }
                        });
                    }
                });
            }
        }

        return view;

        }

    }

