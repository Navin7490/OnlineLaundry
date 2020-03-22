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
        switch (position){
            case  0:
                KidsShirt_Fragment kidsShirtFragment=new KidsShirt_Fragment();
                return kidsShirtFragment;
            case 1:
                KidsOutdoor_Fragment kidsOutdoorFragment=new KidsOutdoor_Fragment();
                return kidsOutdoorFragment;
            case 2:
                KidsDress_Fragment kidsDressFragment=new KidsDress_Fragment();
                return kidsDressFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return totaltab;
    }
}
