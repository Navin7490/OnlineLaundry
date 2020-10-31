package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import navin.laundry.myproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home_Activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toast toast;
    Myreciver myreciver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home_);
        bottomNavigationView=findViewById(R.id.BtomNav);
        getSupportActionBar().setTitle("Admin");
        myreciver=new Myreciver();


    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myreciver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myreciver);
    }

    public class Myreciver extends BroadcastReceiver {
        public boolean noconnectivity;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                noconnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                if (noconnectivity) {
                    toast = Toast.makeText(Admin_Home_Activity.this, "Please connect internet !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                } else {
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
        }
    }
}