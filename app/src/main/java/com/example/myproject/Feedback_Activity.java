package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Feedback_Activity extends AppCompatActivity {
    EditText etname,etemail,etfeedback;
    Button btnsend;
    String name,email,feedback;
    Toast toast;
    String FEEDBACK_URL="http://192.168.43.65/admin/api/user_feedback.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_);
        etname=findViewById(R.id.EtFname);
        etemail=findViewById(R.id.EtFemail);
        etfeedback=findViewById(R.id.EtFeedback);
        btnsend=findViewById(R.id.BtnFsend);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=etname.getText().toString();
                email=etemail.getText().toString();
                feedback=etfeedback.getText().toString();

                if (name.isEmpty()){
                    etname.requestFocus();
                    etname.setError("please enter your name");
                }
                else if (name.length()<4){
                    etname.requestFocus();
                    etname.setError("Name Must Be Long");
                }
                else if (email.isEmpty()){
                    etemail.requestFocus();
                    etemail.setError("please enter your email");
                }
                else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    etemail.requestFocus();
                    etemail.setError("Invalide email");
                }
                else if (feedback.isEmpty()){
                    etfeedback.requestFocus();
                    etfeedback.setError("please enter your feedback");
                }
                else if (feedback.length()<15){
                    etfeedback.requestFocus();
                    etfeedback.setError("feedback must be 15 charecter more");
                }
                else {
                    final ProgressDialog progressDialog=new ProgressDialog(Feedback_Activity.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setContentView(R.layout.progrees_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, FEEDBACK_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String status=jsonObject.getString("status");

                                if (status.equals("Success")){

                                  toast=  Toast.makeText(Feedback_Activity.this, "feedback send successful", Toast.LENGTH_SHORT);
                                  toast.setGravity(Gravity.CENTER,0,0);
                                  toast.show();
                                    Intent intent=new Intent(getApplicationContext(),Navigation_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            toast=  Toast.makeText(Feedback_Activity.this, "connection fail", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>parms=new HashMap<>();

                            parms.put("u_name",name);
                            parms.put("u_email",email);
                            parms.put("u_feedback",feedback);
                            return parms;

                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(Feedback_Activity.this);
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
