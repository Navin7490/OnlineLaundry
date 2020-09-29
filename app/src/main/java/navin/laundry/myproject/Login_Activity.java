package navin.laundry.myproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import navin.laundry.myproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends Activity {
    EditText etemail,etpassword;
    Button btnlogin;
    String LOGIN_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/user_login.php";

    // String LOGIN_URL="http://192.168.43.65/laundry_service/api/user_login.php";
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        etemail=findViewById(R.id.ETLoginEmail);
        etpassword=findViewById(R.id.ETLoginPassword);
        btnlogin=findViewById(R.id.BTNLogin);

    }

    public void gotoregistrationpage(View view) {
        Intent intent=new Intent(getApplicationContext(),Signup_Activity.class);
        startActivity(intent);
    }

    public void login(View view) {

        final String email=etemail.getText().toString();
        final String password=etpassword.getText().toString();

        if (email.isEmpty()){
            etemail.requestFocus();
            etemail.setError("Enter email");
        }
        else if (!etemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            etemail.requestFocus();
            etemail.setError("Invalid email");
        }
        else if (password.isEmpty()){
            etpassword.requestFocus();
            etpassword.setError("Enter password");
        }
        else if (password.length()<6){
            etpassword.requestFocus();
            etpassword.setError("password must be long");
        }
        else {

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setContentView(R.layout.progrees_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
           StringRequest stringRequest=new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String status=jsonObject.getString("status");
                    if (status.equals("failed")){

                        toast=Toast.makeText(Login_Activity.this,"incorrect email or password",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else if (status.equals("Success")){
                        User_login_modal userLoginModal=new User_login_modal(getApplicationContext());

                        String pimage=jsonObject.getString("u_profileimage");
                        String name=jsonObject.getString("u_name");
                        String email=jsonObject.getString("u_email");

                        userLoginModal.setLimage(pimage);
                        userLoginModal.setLName(name);
                        userLoginModal.setLEmail(email);
                        Intent intent=new Intent(getApplicationContext(),Navigation_Activity.class);
                        toast=Toast.makeText(Login_Activity.this,"Login successful",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
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
                toast= Toast.makeText( Login_Activity.this, "connection fail", Toast.LENGTH_LONG );
                toast.setGravity( Gravity.CENTER,0,0 );
                toast.show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>parms=new HashMap<>();
                parms.put("u_email",email);
                parms.put("u_password",password);
                return parms;
            }
        };

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
    }

    public void goforfortepassword(View view) {
        Intent forgot=new Intent(getApplicationContext(),Forgot_password_Activity.class);
        startActivity(forgot);
    }


    public void adminloginpage(View view) {
        startActivity(new Intent(getApplicationContext(),Admin_Login_Activity.class));

    }
}
