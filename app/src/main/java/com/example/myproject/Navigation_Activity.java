package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
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

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Navigation_Activity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    Animation aniblanki;
    RecyclerView recyclerView;
    ImageSlider imageSlider;
    TextView navname,navemail;
    CircleImageView navimage;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle abdt;

    ArrayList<Category_modal> product;
    RecyclerView.LayoutManager layoutManager;
    TextView tvmqree;
    Toast toast;
    Bitmap bitmap;
    TextView textView;
    View header;
    Menu myorder,mycard,logout,share,terms,about,feedback;
    String MARQUETEXT_URL="http://192.168.43.65/laundry_service/api/marque_title.php";
    String CATEGORY_URL="http://192.168.43.65/laundry_service/api/category.php";
    String VIEWPAGER_URL="http://192.168.43.65/laundry_service/api/viewpager.php";
    String category;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);
        product=new ArrayList<>();
        //swipeRefreshLayout=findViewById(R.id.refreshlayout);

        imageSlider = findViewById(R.id.imageSlider);
        recyclerView=findViewById(R.id.Rvcategory);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        tvmqree=findViewById(R.id.Tvmaruee);
        tvmqree.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvmqree.setSelected(true);

        myorder=findViewById(R.id.item_1);
        mycard=findViewById(R.id.item_2);
        logout=findViewById( R.id.item_3 );
        share=findViewById(R.id.item_4);
        terms=findViewById(R.id.item_5);
        about=findViewById(R.id.item_6);

        feedback=findViewById(R.id.item_7);

        drawerLayout=findViewById( R.id.drawer );
        navigationView=findViewById( R.id.navigation );


         header=navigationView.getHeaderView( 0 );
        navimage=header.findViewById(R.id.ImLProfile);
        navname=header.findViewById( R.id.nav_name);
        navemail=header.findViewById( R.id .nav_email);
        aniblanki= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        tvmqree.setVisibility(View.VISIBLE);
        tvmqree.startAnimation(aniblanki);
       // tvmqree.setTextColor(R.color.red);


        getSupportActionBar().setTitle("Home");
        abdt = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        User_login_modal userLogin=new User_login_modal( Navigation_Activity.this );
        String n=userLogin.sharedPreferencesLogin.getString( "LName" ,null);
        String e= userLogin.sharedPreferencesLogin.getString( "LEmail",null );


        Glide.with(this).load(userLogin.sharedPreferencesLogin.getString("LImage",null)).into(navimage);
        navname.setText( n );
        navemail.setText( e );




        tvmqree.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(), Offer_Activity.class);
        startActivity(intent);
    }
});
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                boolean connection=isNetworkAvailable();
//                if (connection) {

                    final ProgressDialog progressDialog = new ProgressDialog(Navigation_Activity.this);
                   // progressDialog.setMessage("please waite");
                    progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    StringRequest stringpager = new StringRequest(Request.Method.GET, VIEWPAGER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("view_pager");
                                List<SlideModel> slideModels = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject category = jsonArray.getJSONObject(i);


                                    String image = category.getString("v_image");
                                    String name = category.getString("v_title");
                                    slideModels.add(new SlideModel(image, name));
                                    imageSlider.setImageList(slideModels, true);

                                }
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                        }
                    });
                    RequestQueue viewpare = Volley.newRequestQueue(Navigation_Activity.this);
                    viewpare.add(stringpager);


                    StringRequest stringmarque = new StringRequest(Request.Method.GET, MARQUETEXT_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("category_product");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject category = jsonArray.getJSONObject(i);

                                    String name = category.getString("m_text");
                                    tvmqree.setText(name);



                                }
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();

                        }
                    });
                    RequestQueue mrequset = Volley.newRequestQueue(Navigation_Activity.this);
                    mrequset.add(stringmarque);
//
                    StringRequest stringRequest=new StringRequest(Request.Method.GET,CATEGORY_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.d("categoryresult",response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray jsonArray=jsonObject.getJSONArray("category_product");


                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject category=jsonArray.getJSONObject(i);
                                    String image=category.getString("category_image");
                                    String name=category.getString("category_name");

                                    Category_modal productModal=new Category_modal();
                                    productModal.setImage(image);
                                    productModal.setName(name);


                                    product.add(productModal);
                                    Category_Adapter categoryAdapter=new Category_Adapter(getApplicationContext(),product);
                                    recyclerView.setAdapter(categoryAdapter);

                                }
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                           toast= Toast.makeText(Navigation_Activity.this, "connection fail", Toast.LENGTH_SHORT);
                           toast.setGravity(Gravity.CENTER,0,0);
                           toast.show();

                        }
                    });
                    RequestQueue requestQueue= Volley.newRequestQueue(Navigation_Activity.this);
                    requestQueue.add(stringRequest);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.item_1:

                        Intent order=new Intent(getApplicationContext(),MyOrder_Activity.class);
                        startActivity(order);
                        return true;
                    case R.id.item_2:
                        Intent mycard=new Intent(getApplicationContext(),My_cart_Activity.class);
                        startActivity(mycard);
                        return true;

                    case R.id.item_3:
                        showlogoutdailog();
                        return true;

                    case R.id.item_4:
                        Intent intent1shre=new Intent(Intent.ACTION_SEND);
                        intent1shre.setType("text/plain");
                        intent1shre.putExtra(Intent.EXTRA_SUBJECT,"Laundry service");
                        intent1shre.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps?hl=en");
                        startActivity(Intent.createChooser(intent1shre,"share app"));
                        return true;

                    case R.id.item_5:
                        Intent terms=new Intent(getApplicationContext(),Terms_and_condition_Activity.class);
                        startActivity(terms);
                        return true;

                    case R.id.item_6:
                        Intent about=new Intent(getApplicationContext(),About_Activity.class);
                        startActivity(about);
                        return true;

                    case R.id.item_7:
                        Intent feedback=new Intent(getApplicationContext(),Feedback_Activity.class);
                        startActivity(feedback);

                        return true;

                }
                return true;
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
         if (drawerLayout.isDrawerOpen(Gravity.START)){
             drawerLayout.closeDrawer(Gravity.START);
         }
         else {
//             showalertdialog();
             super.onBackPressed();
         }
    }
    public void showalertdialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
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

        Dialog dialog= builder.create();
        dialog.show();

       // dialog.getWindow().setBackgroundDrawableResource(R.color.blue);
        Button no=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        no.setTextColor(Color.BLACK);
        Button yes=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(Color.BLACK);

    }
    @SuppressLint("ResourceAsColor")
    public void showlogoutdailog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("you want to logout");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User_login_modal userLogin=new User_login_modal( Navigation_Activity.this );
                userLogin.removeuser();
                toast =Toast.makeText( Navigation_Activity.this,"Logout successful",Toast.LENGTH_SHORT );
                toast.setGravity( Gravity.CENTER,0,0 );
                toast.show();
                Intent intent=new Intent( getApplicationContext(), Login_Activity.class );
                startActivity( intent );
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
       Dialog dialog= builder.create();
       dialog.show();
      // dialog.getWindow().setBackgroundDrawableResource(R.color.blue);
        Button no=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        no.setTextColor(Color.BLACK);
        Button yes=((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        yes.setTextColor(Color.BLACK);

    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

}

