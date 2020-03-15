package com.example.myproject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tab_woman_Adapter extends FragmentPagerAdapter {

    private Context context;
    int totaltab;
    private  final List<Fragment> lstfragment=new ArrayList<>();
    private  final List<String> lstTitle=new ArrayList<>();
    public Tab_woman_Adapter(FragmentManager fm,Context context,int totaltab) {
        super(fm);
        this.context=context;
        this.totaltab=totaltab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                Saree_Fragment sareeFragment=new Saree_Fragment();
                return sareeFragment;
            case 1:
                Lehenga_Fragment lehenga_fragment=new Lehenga_Fragment();
                return lehenga_fragment;
            case 2:
                LadiesSuit_Fragment ladiesSuit_fragment=new LadiesSuit_Fragment();
                return ladiesSuit_fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return totaltab;
    }
}
