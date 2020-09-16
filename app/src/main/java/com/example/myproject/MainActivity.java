package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User_login_modal userLoginModal=new User_login_modal(getApplicationContext());
                String email=userLoginModal.sharedPreferencesLogin.getString("LName",null);

                if (email !=null){
                    Intent intent=new Intent(getApplicationContext(),Navigation_Activity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                    startActivity(intent);
                    finish();

                }



            }
        },2000);
    }
}
