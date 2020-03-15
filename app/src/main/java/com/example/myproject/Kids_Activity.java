package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Kids_Activity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_);
        tabLayout=findViewById(R.id.Tablayout_kids);
        viewPager=findViewById(R.id.Viewpage_kids);

        tabLayout.addTab(tabLayout.newTab().setText("kidsshirt"));
        tabLayout.addTab(tabLayout.newTab().setText("kidsoutdoor"));
        tabLayout.addTab(tabLayout.newTab().setText("kidsdress"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Tab_kids_Adapter adapter=new Tab_kids_Adapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
