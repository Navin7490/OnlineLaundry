package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import navin.laundry.myproject.R;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Navigation_Activity extends AppCompatActivity {

    TextView navname, navemail;
    CircleImageView navimage;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle abdt;
    Toast toast;
    View header;
    Menu myorder, mycard, logout, share, terms, about, feedback;
    FrameLayout frameLayout;
    Myreciver myreciver;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);
        //swipeRefreshLayout=findViewById(R.id.refreshlayout);



        myorder = findViewById(R.id.item_1);
        mycard = findViewById(R.id.item_2);
        logout = findViewById(R.id.item_3);
        share = findViewById(R.id.item_4);
        terms = findViewById(R.id.item_5);
        about = findViewById(R.id.item_6);
        myreciver = new Myreciver();
        feedback = findViewById(R.id.item_7);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.Fram_cate);


        header = navigationView.getHeaderView(0);
        navimage = header.findViewById(R.id.ImLProfile);
        navname = header.findViewById(R.id.nav_name);
        navemail = header.findViewById(R.id.nav_email);

        // tvmqree.setTextColor(R.color.red);


        getSupportActionBar().setTitle("Home");
        abdt = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        User_login_modal userLogin = new User_login_modal(Navigation_Activity.this);
        String n = userLogin.sharedPreferencesLogin.getString("LName", null);
        String e = userLogin.sharedPreferencesLogin.getString("LEmail", null);


        Glide.with(this).load(userLogin.sharedPreferencesLogin.getString("LImage", null)).into(navimage);
        navname.setText(n);
        navemail.setText(e);






        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.item_1:

                        Intent order = new Intent(getApplicationContext(), MyOrder_Activity.class);
                        startActivity(order);
                        return true;
                    case R.id.item_2:
                        Intent mycard = new Intent(getApplicationContext(), My_cart_Activity.class);
                        startActivity(mycard);
                        return true;

                    case R.id.item_3:
                        showlogoutdailog();
                        return true;

                    case R.id.item_4:
                        Intent intent1shre = new Intent(Intent.ACTION_SEND);
                        intent1shre.setType("text/plain");
                        intent1shre.putExtra(Intent.EXTRA_SUBJECT, "Laundry service");
                        intent1shre.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps?hl=en");
                        startActivity(Intent.createChooser(intent1shre, "share app"));
                        return true;

                    case R.id.item_5:
                        Intent terms = new Intent(getApplicationContext(), Terms_and_condition_Activity.class);
                        startActivity(terms);
                        return true;

                    case R.id.item_6:
                        Intent about = new Intent(getApplicationContext(), About_Activity.class);
                        startActivity(about);
                        return true;

                    case R.id.item_7:
                        Intent feedback = new Intent(getApplicationContext(), Feedback_Activity.class);
                        startActivity(feedback);

                        return true;

                }
                return true;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
//             showalertdialog();
            super.onBackPressed();
        }
    }

    public void showalertdialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure want to leave");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();

        // dialog.getWindow().setBackgroundDrawableResource(R.color.blue);
        Button no = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        no.setTextColor(Color.BLACK);
        Button yes = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(Color.BLACK);

    }

    @SuppressLint("ResourceAsColor")
    public void showlogoutdailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("you want to logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User_login_modal userLogin = new User_login_modal(Navigation_Activity.this);
                userLogin.removeuser();
                toast = Toast.makeText(Navigation_Activity.this, "Logout successful", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        // dialog.getWindow().setBackgroundDrawableResource(R.color.blue);
        Button no = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        no.setTextColor(Color.BLACK);
        Button yes = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(Color.BLACK);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(myreciver,filter);
//    }


    @Override
    protected void onResume() {
        super.onResume();
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
                    toast = Toast.makeText(Navigation_Activity.this, "Please connect internet !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                } else {
//
                    User_Category_Fragment fragment=new User_Category_Fragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.Fram_cate,fragment);
                    fragmentTransaction.commit();
                }
            }
        }
    }

}

