package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import navin.laundry.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_to_cart_Activity extends AppCompatActivity {
    ImageView image;
    Button btnremove,btnadd,btnaddtocart,btnplaceorder;
    TextView tvpric,tvpname,tvpdetail,tvqulity;

    String[]count={"1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30",
            "31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
    int position = -1;
    String n,d,p ,img;
    int q;
    String total;
    int pt,qt;
    Toast toast;
    Bitmap bitmap;
    Uri imageuri;
    byte[]bytArray;
    private static final int PICK_IMAGE=1;

    String ADD_TO_CART_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/addtocart.php";
    String CART_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/mycart.php";
//    String ADD_TO_CART_URL="http://192.168.43.65/laundry_service/api/addtocart.php";
//    String CART_URL="http://192.168.43.65/laundry_service/api/mycart.php";
    ProgressDialog progressDialog;
    Mycart_Adapter adapter;
    ArrayList<Mycard_modal> prodct;
    String items,itemqty,itemprice,sum,status;
    JSONArray jsonArray;
    String rupi="₹";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart_);

        image=findViewById(R.id.Image_detail);
        tvpric=findViewById(R.id.tvShow_price);
        tvpname=findViewById(R.id.tvDproduct_name);
        tvpdetail=findViewById(R.id.tvDproduct_detail);
        tvqulity=findViewById(R.id.Add_numb_item);
        btnadd=findViewById(R.id.BtnAdditem);
        btnremove=findViewById(R.id.BtnRemoveitem);
        btnaddtocart=findViewById(R.id.BtnAdd_to_card_id);
        btnplaceorder=findViewById(R.id.Btnplace_order_id);
      //  image.setImageBitmap(imageView2Bitmap(image));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add to cart");



        final Intent intent=getIntent();
        n=intent.getStringExtra("name");
        tvpname.setText(n);
        d=intent.getStringExtra("description");
        tvpdetail.setText(d);
        p= intent.getStringExtra("price");
        tvpric.setText(rupi.concat(p));

        Glide.with(this).load( intent.getStringExtra("image")).into(image);

        User_login_modal userLogin=new User_login_modal( Add_to_cart_Activity.this );
        final String email=userLogin.sharedPreferencesLogin.getString( "LEmail" ,null);





        btnadd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                btnaddtocart.setVisibility(View.VISIBLE);
                btnremove.setVisibility(View.VISIBLE);
                tvqulity.setVisibility(View.VISIBLE);
                btnremove.getVisibility();
             //   btnadd.setLeft(310);

                if (position<count.length-1){
                    position=position +1;
                    tvqulity.setText(count[position]);
                    q= Integer.parseInt(count[position]);

                }


            }
        });

        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position>0){
                    position=position -1;
                    tvqulity.setText(count[position]);
                }
            }
        });

        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pt= Integer.parseInt(p);
                qt= q;
                p= String.valueOf(pt*qt);

                progressDialog=new ProgressDialog(Add_to_cart_Activity.this);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setContentView(R.layout.progrees_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_TO_CART_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("add",response);

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if (status.equals("sucess")){
                                toast= Toast.makeText(Add_to_cart_Activity.this, "Add to cart successfull", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                                Intent addcart=new Intent(getApplicationContext(),Navigation_Activity.class);
                                startActivity(addcart);
                                finish();
                            }
                            else {
                                toast= Toast.makeText(Add_to_cart_Activity.this, "connectin fail", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
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
                        toast= Toast.makeText(Add_to_cart_Activity.this, error.getMessage()+"try again", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parms=new HashMap<>();
                      //  imagedata= imagetostring(bitmap);
                        parms.put("u_email",email);
                        parms.put("u_p_name",n);
                        parms.put("u_p_quntity", String.valueOf(qt));
                        parms.put("u_p_price", String.valueOf(pt));
                        parms.put("total_ptice", String.valueOf(p));
                        parms.put("u_p_image", String.valueOf(image));
                        return parms;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(Add_to_cart_Activity.this);
                requestQueue.add(stringRequest);

            }
        });





        btnplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest=new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ress",response);
                       // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("cart_product");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject mycart = jsonArray.getJSONObject(i);
//                                String name = mycart.getString("u_p_name");
//                                String quntity = mycart.getString("u_p_quntity");
//                                String price = mycart.getString("u_p_price");


//                                Mycard_modal mycardModal = new Mycard_modal();
//                                mycardModal.setName(name);
//                                mycardModal.setQulity(quntity);
//                                mycardModal.setPrice(price);
//                                prodct.add(mycardModal);
//                                adapter = new Mycart_Adapter(Add_to_cart_Activity.this, prodct);
////

                                sum = jsonObject.getString("sum");
                                items = jsonObject.getString("items");
                                itemqty = jsonObject.getString("item qty");
                                itemprice = jsonObject.getString("item price");


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (jsonArray==null){
                            toast= Toast.makeText(Add_to_cart_Activity.this, "cart is empty", Toast.LENGTH_SHORT);
                            toast.show();
                            toast.setGravity(Gravity.CENTER,0,0);
                        }
                        else {
                            User_Order_Modal order = new User_Order_Modal(Add_to_cart_Activity.this);
                            order.setItems(items);
                            order.setItemqty(itemqty);
                            order.setItemprice(itemprice);
                            order.setTotalprice(sum);
                            Intent placeorder = new Intent(getApplicationContext(), Place_order_Activity.class);
                            startActivity(placeorder);
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast toast = Toast.makeText(Add_to_cart_Activity.this, "connection fail", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String>parms=new HashMap<>();
                        parms.put("u_email",email);
                        return parms;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(Add_to_cart_Activity.this);
                requestQueue.add(stringRequest);

            }
        });
    }

    //@Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//          //  imageuri = data.getData();
//            //          CropImage.activity()
////                    .setGuidelines(CropImageView.Guidelines.ON)
////                    .setAspectRatio(1, 1)
////                    .start(this);
////
//        }
////        try {
////            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//    private String imagetostring(Bitmap bitmap){
//        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//        byte[] imagebyte=outputStream.toByteArray();
//        String encodedimage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
//        return encodedimage;
//
//    }
//    private Bitmap imageView2Bitmap(ImageView view){
//        Bitmap bitmap=((BitmapDrawable)view.getDrawable()).getBitmap();
//        return bitmap;
//
//
//    }

}
