package com.example.myproject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Tab_kids_Adapter extends FragmentPagerAdapter {
    private Context context;
    int totaltab;
    public Tab_kids_Adapter(FragmentManager fm,Context context,int totaltab) {
        super(fm);
        this.context=context;
        this.totaltab=totaltab;
    }

    @Override
    public Fragment getItem(int position) {

        return null;
    }

    @Override
    public int getCount() {
        return totaltab;
    }
}
