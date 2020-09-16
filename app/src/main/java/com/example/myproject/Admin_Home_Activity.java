package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home_);
        bottomNavigationView=findViewById(R.id.BtomNav);
        getSupportActionBar().setTitle("Admin");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.Category){
                    Admin_Home_category_Fragment homeFragment=new Admin_Home_category_Fragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frame_layout,homeFragment);
                    fragmentTransaction.commit();
                }
//                if (id==R.id.Product){
//                    ProductFragment productFragment=new ProductFragment();
//                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.Frame_layout,productFragment);
//                    fragmentTransaction.commit();
//                }
                if (id==R.id.Order){
                    Admin_Home_Order_Fragment listOrderFragment=new Admin_Home_Order_Fragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frame_layout,listOrderFragment);
                    fragmentTransaction.commit();

                }
                if (id==R.id.User){
                    Admin_Home_Users_Fragment usersFragment=new Admin_Home_Users_Fragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frame_layout,usersFragment);
                    fragmentTransaction.commit();
                }
                if (id==R.id.Me){
                    Admin_Home_Profile_Fragment profileFragment=new Admin_Home_Profile_Fragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Frame_layout,profileFragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.Category);
    }
}