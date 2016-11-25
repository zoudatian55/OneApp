package com.example.oneapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.oneapplication.R;
import com.example.oneapplication.utils.MyAnimationDrawable;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<ImageView> images = new ArrayList<>();
    private int[] resId;
    private int[] imageId;
    private ImageView image;
    private LinearLayout dotLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.pager_Gudie);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        image = (ImageView) findViewById(R.id.image_guide);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //初始化图片和帧动画数据
        initImages();

        //初始化小圆点容器
        initDot();

        //监听滑动事件，当滑动到下一页时，开始下一页的动画
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {

                for (int i = 0;i<dotLayout.getChildCount();i++){
                    if (i == position){
                        dotLayout.getChildAt(i).setEnabled(false);
                    }else {
                        dotLayout.getChildAt(i).setEnabled(true);
                    }
                }

                if (((Boolean) images.get(position).getTag())) {
                    MyAnimationDrawable.animateRawManuallyFromXML(resId[position], images.get(position),

                            new Runnable() {
                                @Override
                                public void run() {
                                    images.get(position).setImageResource(imageId[position]);
                                    images.get(position).setTag(false);
                                }
                            },
                            new Runnable() {
                                @Override
                                public void run() {
                                    //滑到最后一页时且动画结束是，显示Button按钮
                                    if (position == 3){
                                        image.setVisibility(View.VISIBLE);
                                        dotLayout.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(images.get(position));
                return images.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    private void initDot() {
        for (int i = 0;i<images.size();i++){
            ImageView dotImage = new ImageView(this);
            dotImage.setBackgroundResource(R.drawable.guide_index_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20,0,20,0);
            dotImage.setLayoutParams(layoutParams);
            if (i == 0){
                dotImage.setEnabled(false);
            }else {
                dotImage.setEnabled(true);
            }
            dotLayout.addView(dotImage);
        }
    }

    private void initImages() {
        resId = new int[]{R.drawable.reading_guide_anim,R.drawable.music_guide_anim,
                R.drawable.movie_guide_anim,R.drawable.one_guide_anim};
        imageId = new int[]{R.drawable.reading_guide_56,R.drawable.music_guide_68,
                R.drawable.movie_guide_63,R.drawable.one_guide_94};
        ImageView image1 = new ImageView(this);
        images.add(image1);
        //第一页开始动画
        MyAnimationDrawable.animateRawManuallyFromXML(resId[0], images.get(0),
                new Runnable() {
                    @Override
                    public void run() {
                    }
                },
                new Runnable() {
                    @Override
                    public void run() {
                        images.get(0).setImageResource(imageId[0]);
                        images.get(0).setTag(false);
                    }
                });
        ImageView image2 = new ImageView(this);
        image2.setTag(true);
        images.add(image2);
        ImageView image3 = new ImageView(this);
        image3.setTag(true);
        images.add(image3);
        ImageView image4 = new ImageView(this);
        image4.setTag(true);
        images.add(image4);
        
    }
}
