package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_ViewProduct_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Admin_ViewProductModal> product;
    String category;
    String PRODUCT_URL="http://192.168.43.65/laundry_service/viewProductbyCate.php";
    String ADD_PRODUCT_URL="http://192.168.43.65/laundry_service/Add_product.php";
    Dialog dialog;
    FloatingActionButton floatingActionButton;
    EditText etname,etdesciption,etprice;
    ImageView image;
    Spinner spinnercate;
    Button cancel,Add;
    private static final int PICK_IMAGE=1;
    Uri imageUri;
    Bitmap bitmap;
    String Imagedata;
    String name,description,price;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view_product_);
        getSupportActionBar().setTitle("Admin");

        Intent intent=getIntent();
        category= intent.getStringExtra("category");
        getSupportActionBar().setTitle(category+" Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        floatingActionButton=findViewById(R.id.Floati_ProductAdd);
        recyclerView=findViewById(R.id.Rv_ViewProduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        product=new ArrayList<>();

        dialog=new Dialog(this);
        progressDialog=new ProgressDialog(this);
        dialog.setContentView(R.layout.admin_add_product_dialog);
        dialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    // String status=jsonObject.getString("status");
                    JSONArray jsonArray=jsonObject.getJSONArray("product_detail");
                    // if (status.equals("success")){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject productv=jsonArray.getJSONObject(i);
                        String proId=productv.getString("id");
                        String procategory=productv.getString("Product_category");

                        String proimage=productv.getString("Product_image");
                        String proname=productv.getString("Product_name");
                        String prodescription=productv.getString("Product_description");
                        String proprice=productv.getString("Product_price");

                        Admin_ViewProductModal viewProductModal=new Admin_ViewProductModal();
                        viewProductModal.setId(proId);
                        viewProductModal.setProcategory(procategory);
                        viewProductModal.setProimage(proimage);
                        viewProductModal.setProname(proname);
                        viewProductModal.setProdescription(prodescription);
                        viewProductModal.setProprice(proprice);
                        product.add(viewProductModal);
                        Admin_ViewProductAdapter adapter=new Admin_ViewProductAdapter(getApplicationContext(),product);
                        recyclerView.setAdapter(adapter);

                    }

                    // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Admin_ViewProduct_Activity.this, "connectin Fail", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parm=new HashMap<>();
                parm.put("category_name",category);
                return parm;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                etname=dialog.findViewById(R.id.Et_ProduName);
                etdesciption=dialog.findViewById(R.id.Et_Description);
                etprice=dialog.findViewById(R.id.Et_Price);
                image=dialog.findViewById(R.id.Im_Product);
                cancel=dialog.findViewById(R.id.Btn_CancelProduct);
                Add=dialog.findViewById(R.id.Btn_AddProduct);
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        name=etname.getText().toString();
                        description=etdesciption.getText().toString();
                        price=etprice.getText().toString();


//                        if (cat==null || cat.matches(matchescat.toString())){
//                            Toast.makeText(getContext(), "select Category", Toast.LENGTH_SHORT).show();
//
//                        }
                        if (name.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();

                        }
                        else if (description.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Enter description", Toast.LENGTH_SHORT).show();

                        } else if (price.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Enter price", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            progressDialog.show();
                            StringRequest addproductreuest=new StringRequest(Request.Method.POST, ADD_PRODUCT_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        String status=jsonObject.getString("status");
                                        if (status.equals("success")){
                                            Toast.makeText(getApplicationContext(), "Product Add Success", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent1=new Intent(getApplicationContext(),Admin_Home_Activity.class);
                                            startActivity(intent1);
                                            finish();
                                        }
                                        else if (status.equals("failur")){
                                            Toast.makeText(getApplicationContext(), "Faill", Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();


                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String>parms=new HashMap<>();
                                    Imagedata=imageToString(bitmap);
                                    parms.put("category_name",category);
                                    parms.put("name",name);
                                    parms.put("description",description);
                                    parms.put("price",price);
                                    parms.put("image",Imagedata);
                                    return parms;
                                }
                            };


                            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(addproductreuest) ;

                        }


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gallary=new Intent();
                        gallary.setType("image/*");
                        gallary.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(gallary,"select image"),PICK_IMAGE);
                    }
                });
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            imageUri=data.getData();
        }
        try {
            bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),imageUri);
            image.setImageBitmap(bitmap);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private  String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[]imagebyte=outputStream.toByteArray();
        String encodedimage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedimage;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}