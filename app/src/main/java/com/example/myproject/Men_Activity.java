package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class Men_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Men Clothe");
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpage);

        tabLayout.addTab(tabLayout.newTab().setText("shirts"));
        tabLayout.addTab(tabLayout.newTab().setText("suits"));
        tabLayout.addTab(tabLayout.newTab().setText("outdoor"));
        tabLayout.addTab(tabLayout.newTab().setText("trousers"));
        tabLayout.addTab(tabLayout.newTab().setText("Ethnic"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Tab_Men_Adapter adapter = new Tab_Men_Adapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
