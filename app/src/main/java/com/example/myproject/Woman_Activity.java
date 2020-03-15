package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class Woman_Activity extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woman_);

        tabLayout=findViewById(R.id.Tablayout_woman);
        viewPager=findViewById(R.id.Viewpage_woman);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Woman Clothe");

        tabLayout.addTab(tabLayout.newTab().setText("saree"));
        tabLayout.addTab(tabLayout.newTab().setText("Lehenga"));
        tabLayout.addTab(tabLayout.newTab().setText("LadiesSuit"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Tab_woman_Adapter tab_woman_adapter=new Tab_woman_Adapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(tab_woman_adapter);


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
