package com.example.oneapplication.utils;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by 轩 on 2016/10/31.
 */

public class MyCustomTabEntity implements CustomTabEntity {

    private String title;
    private int selectIcon;
    private int unselectIcon;

    public MyCustomTabEntity(String title,int unselectIcon,int selectIcon){
        this.title = title;
        this.selectIcon = selectIcon;
        this.unselectIcon = unselectIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unselectIcon;
    }
}
