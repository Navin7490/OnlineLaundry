package navin.laundry.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

public class Admin_Login_Activity extends AppCompatActivity {
     EditText etemail,etpassword;
     String email,password;
     String ADMIN_LOGIN="https://navindeveloperinfo.000webhostapp.com/laundry_service/Admin_login.php";
    // String ADMIN_LOGIN="http://192.168.43.65/laundry_service/Admin_login.php";
     Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login_);
        getSupportActionBar().setTitle("Admin");
        etemail=findViewById(R.id.Et_A_LEmail);
        etpassword=findViewById(R.id.Et_A_LPassword);
    }

    public void login(View view) {
        email=etemail.getText().toString();
        password=etpassword.getText().toString();
        if (email.isEmpty()){
            etemail.requestFocus();
            etemail.setError("Enater Email");
        }
        else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            etemail.requestFocus();
            etemail.setError("Invalid Email");
        }
        else if (password.isEmpty()){
            etpassword.requestFocus();
            etpassword.setError("Enter Password");
        }
        else if (password.length()<6){
            etpassword.requestFocus();
            etpassword.setError("Enter strong password");

        }
        else {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setContentView(R.layout.progrees_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, ADMIN_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        String status=jsonObject.getString("status");
                        if (status.equals("failed")){

                            toast= Toast.makeText(Admin_Login_Activity.this,"incorrect email or password",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                        }
                        else if (status.equals("Success")){
                          //  Admin_UserModal userModal=new Admin_UserModal(getApplicationContext());

                            String name=jsonObject.getString("a_name");
                            String email=jsonObject.getString("a_email");

                           // userLoginModal.setLName(name);
                           // userLoginModal.setLEmail(email);
                            Intent intent=new Intent(getApplicationContext(),Admin_Home_Activity.class);
                            toast=Toast.makeText(Admin_Login_Activity.this,"Login  successful",Toast.LENGTH_LONG);
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
                    toast= Toast.makeText( Admin_Login_Activity.this, "connection fail", Toast.LENGTH_LONG );
                    toast.setGravity( Gravity.CENTER,0,0 );
                    toast.show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String>parms=new HashMap<>();
                    parms.put("a_email",email);
                    parms.put("a_password",password);
                    return parms;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}