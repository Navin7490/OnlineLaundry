package navin.laundry.myproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Signup_Activity extends Activity {
    EditText etname, etemail, etpassword, etphon;
    TextView tvlogin;
    Button btnrst;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    String gender = "";
    Toast toast;

    CircleImageView profileImage;
    private static final int PICK_IMAGE=1;
    Uri imageUri;
    Bitmap bitmap;
    String imagedata;
    String REGISTRATION_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/user_register.php";

    //String REGISTRATION_URL="http://192.168.43.65/laundry_service/api/user_register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        etname = findViewById( R.id.EtName );
        etname.setEnabled( true );
        etname.setFilters( new InputFilter[]{new InputFilter.AllCaps()} );
        etemail = findViewById( R.id.EtEame );
        etpassword = findViewById( R.id.EtPassword );
        etphon = findViewById( R.id.EtMobileNumber );
        profileImage=findViewById(R.id.ImProfile);

        radioGroup = findViewById( R.id.RGroup );
        radioButton1 = findViewById( R.id.RBTnMale );
        radioButton2 = findViewById( R.id.EtRBTNFemal );

        tvlogin=findViewById( R.id.TvLoginbtn );


        btnrst = findViewById( R.id.BTNREgister );
        btnrst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallary=new Intent();
                gallary.setType("image/*");
                gallary.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallary,"select pictre"),PICK_IMAGE);
            }
        });

    }


    public void BTNLogin(View view) {
        Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
        startActivity(intent);
    }
    public void validation() {
        final String name = etname.getText().toString();
        final String email = etemail.getText().toString().trim();
        final String password = etpassword.getText().toString();
        final String mobile = etphon.getText().toString();


        String r1 = radioButton1.getText().toString();
        String r2 = radioButton2.getText().toString();


         if (name.isEmpty()) {
            etname.requestFocus();
            etname.setError("Enter Name");
        } else if (etname.length() <= 4) {
            etname.requestFocus();
            etname.setError("Name Must be long");
        } else if (!etname.getText().toString().matches("[a-z A-Z]*")) {
            etname.requestFocus();
            etname.setError("Enter Only Character");

        } else if (email.isEmpty()) {
            etemail.requestFocus();
            etemail.setError("Enter Email");
        } else if (!etemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            etemail.requestFocus();
            etemail.setError("Invalid Email");

        } else if (password.isEmpty()) {
            etpassword.requestFocus();
            etpassword.setError("Enter Password");


        } else if (password.length() < 6) {
            etpassword.requestFocus();
            etpassword.setError("Password Must be Long");
        }
         else if (mobile.isEmpty()) {
            etphon.requestFocus();
            etphon.setError("Enter Number");


        } else if (mobile.length() < 10) {
            etphon.requestFocus();
            etphon.setError("Enter Full Mobile Number");


        } else if (!etphon.getText().toString().matches("[0-9]{10}")) {
            etphon.requestFocus();
            etphon.setError("Invalid Mobie Number");


        } else if (!radioButton1.isChecked() && !radioButton2.isChecked()) {
            Toast toast = Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        else {
            if (radioButton1.isChecked()) {

                gender = r1;
            } else if (radioButton2.isChecked()) {

                gender = r2;
            }

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

             progressDialog.setCanceledOnTouchOutside(false);
             progressDialog.setContentView(R.layout.progrees_dialog);
             progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.d("resss", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                        if (status.equals("user already created")) {

                            toast = Toast.makeText(Signup_Activity.this, "Email already existing...", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                        else  {
                            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                            toast = Toast.makeText(Signup_Activity.this, "Registration successful..", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
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
                    toast = Toast.makeText(Signup_Activity.this, "try again", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                     imagedata=imagetostring(bitmap);
                    params.put("u_name", name);
                    params.put("u_email", email);
                    params.put("u_password", password);
                    params.put("u_phone", mobile);
                    params.put("u_gender", gender);
                    params.put("u_profileimage",imagedata);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
  //          CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(this);
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (requestCode == RESULT_OK){
//                Uri resltUri = result.getUri();

        try {
             bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
             profileImage.setImageBitmap(bitmap);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    private String imagetostring(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebyte=outputStream.toByteArray();
        String encodedimage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedimage;

    }
}




