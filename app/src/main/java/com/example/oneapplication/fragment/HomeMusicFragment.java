package com.example.oneapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oneapplication.R;
import com.example.oneapplication.activity.MonthListActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeMusicFragment extends Fragment {

    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_music,null);
        TextView title = (TextView) view.findViewById(R.id.text_main);
        title.setText("音乐");
        viewPager = ((ViewPager) view.findViewById(R.id.vp_mains));
        initData();
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
//        viewPager.setOffscreenPageLimit(fragments.size()-1);//顺序很重要啊

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //在ViewPager最后一页跳转到新的Activity中
                if (position == fragments.size()-1){
                    viewPager.setCurrentItem(position-1);
                    Intent intent = new Intent(getActivity(),MonthListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }
    private void initData() {
        for (int i = 1088; i < 1096; i++) {
            MusicFragment musicFragment = MusicFragment.getInstance(i);
            fragments.add(musicFragment);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

}
