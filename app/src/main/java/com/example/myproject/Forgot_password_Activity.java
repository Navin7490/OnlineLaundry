package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot_password_Activity extends AppCompatActivity {
   Button btnokemail,btnchangpass;
   TextView tvcreatenewpass;
   EditText etemail,etpass,etcpass;
   String password,email;
   Toast toast;
    ProgressDialog progressDialog;
   String FETCH_EMAIL_URL="http://192.168.43.65/laundry_service/api/forgotpassword_emailfetch.php";
    String CHANGE_PASSWORD_URL="http://192.168.43.65/laundry_service/api/change_password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);
        etemail=findViewById(R.id.EtEmailForgot);
        btnokemail=findViewById(R.id.BTN_Forgook);
        btnchangpass=findViewById(R.id.BTN_ForgChangpass);
        tvcreatenewpass=findViewById(R.id.Tv_createnewpass);
        etpass=findViewById(R.id.ETForgotPassword1);
        etcpass=findViewById(R.id.ETforgotPassword2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        final User_login_modal userLoginModal=new User_login_modal(this);
        final String emailf=userLoginModal.sharedPreferencesLogin.getString("LPassword",null);

        btnokemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email=etemail.getText().toString();

                if (email.isEmpty()){
                    etemail.requestFocus();
                    etemail.setError("Enter email");
                }
                else if (!etemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    etemail.requestFocus();
                    etemail.setError("Invalid email");
                }
                else {
                     progressDialog=new ProgressDialog(Forgot_password_Activity.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setContentView(R.layout.progrees_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, FETCH_EMAIL_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             progressDialog.dismiss();
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String status=jsonObject.getString("status");
                                if (status.equals("failed")){
                                  toast=  Toast.makeText(Forgot_password_Activity.this, "Incorecct Email", Toast.LENGTH_SHORT);
                                  toast.setGravity(Gravity.CENTER,0,0);
                                  toast.show();
                                }
                                else if (status.equals("Success")){
                                    String fetemail=jsonObject.getString("u_email");
                                    // userLoginModal.setLPassword(fetemail);
                                    tvcreatenewpass.setVisibility(View.VISIBLE);
                                    etpass.setVisibility(View.VISIBLE);
                                    etcpass.setVisibility(View.VISIBLE);
                                    btnchangpass.setVisibility(View.VISIBLE);
                                    etemail.setVisibility(View.INVISIBLE);
                                    btnokemail.setVisibility(View.INVISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            toast=  Toast.makeText(Forgot_password_Activity.this, "connection fail", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>parms=new HashMap<>();
                            parms.put("u_email",email);
                            return parms;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(Forgot_password_Activity.this);
                    requestQueue.add(stringRequest);

                }
            }
        });
        btnchangpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=etpass.getText().toString();
                String cpass=etcpass.getText().toString();
                if (pass.isEmpty()){
                    etpass.requestFocus();
                    etpass.setError("Enter password");
                }
                else if (pass.length()<6){
                    etpass.requestFocus();
                    etpass.setError("Enter strong password");
                }
                else if (cpass.isEmpty()){
                    etcpass.requestFocus();
                    etcpass.setError("Enter confirm pasword");
                }
                else if (!pass.equals(cpass)){
                    etcpass.requestFocus();
                    etcpass.setError("Not match pasword");

                }
                else {
                    if (cpass.matches(pass)){
                        password=cpass;
                    }
                    else if (pass.matches(cpass)){
                        password=pass;
                    }
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setContentView(R.layout.progrees_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_PASSWORD_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressDialog.dismiss();
                            progressDialog.dismiss();
                            Log.d("update", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String status = jsonObject.getString("status");
                                if (status.equals("sucess")) {

                                    toast = Toast.makeText(Forgot_password_Activity.this, "Password change Successfully", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent forgotintent = new Intent(Forgot_password_Activity.this, Login_Activity.class);
                                    startActivity(forgotintent);
                                    finish();

                                } else {
                                    toast = Toast.makeText(Forgot_password_Activity.this, "fail", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             progressDialog.dismiss();
                            toast = Toast.makeText(Forgot_password_Activity.this, error.getMessage().toString()+"connection fail", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parms = new HashMap<>();
                            parms.put("u_email", email);
                            parms.put("u_password", password);
                            // parms.put("u_p_image", String.valueOf(image));

                            return parms;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Forgot_password_Activity.this);
                    requestQueue.add(stringRequest);

                }
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
