package com.example.myproject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Tab_others_Adapter extends FragmentPagerAdapter {

    private Context context;
    int totaltab;
    public Tab_others_Adapter(FragmentManager fm,Context context,int totaltab) {
        super(fm);
        this.context=context;
        this.totaltab=totaltab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Other_Accessories_Fragment otherAccessoriesFragment=new Other_Accessories_Fragment();
                return otherAccessoriesFragment;
            case 1:
                Other_blanket_Fragment blanketFragment=new Other_blanket_Fragment();
                return blanketFragment;
            case 2:
                Others_bedding_Fragment beddingFragment=new Others_bedding_Fragment();
                return beddingFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return totaltab;
    }
}
