package com.example.myproject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tab_Men_Adapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    private  final List<Fragment> lstfragment=new ArrayList<>();
    private  final List<String> lstTitle=new ArrayList<>();
    public Tab_Men_Adapter(FragmentManager fm,Context myContext,int totalTabs) {
        super(fm);
        myContext=myContext;
        this.totalTabs=totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Shirt_Fragment shirtFragment = new Shirt_Fragment();
                return shirtFragment;
            case  1:
                MenSuit_Fragment menSuit_fragment=new MenSuit_Fragment();
                return menSuit_fragment;
            case 2:
                MenOutdoor_Fragment menOutdoor_fragment=new MenOutdoor_Fragment();
                return menOutdoor_fragment;
            case 3:
                Men_trousers_Fragment fragment=new Men_trousers_Fragment();
                return fragment;
            case 4:
                Men_EthnicFragment fragment1=new Men_EthnicFragment();
                return fragment1;

        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
