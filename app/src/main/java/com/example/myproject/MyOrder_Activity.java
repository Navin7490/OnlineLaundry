package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrder_Activity extends AppCompatActivity {
     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
     ArrayList<MyOrder_Modal>product;
     String MYORDER_URL="http://192.168.43.65/laundry_service/api/myorder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Order");
        recyclerView=findViewById(R.id.Rv_Order);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
         product=new ArrayList<>();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final User_login_modal userLogin=new User_login_modal( MyOrder_Activity.this );
        final String email=userLogin.sharedPreferencesLogin.getString( "LEmail" ,null);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, MYORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("orderrss",response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("order_detail");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject order = jsonArray.getJSONObject(i);
                        String orderId=order.getString("order_id");
                        String ordedate=order.getString("order_date");
                        String  items = order.getString("items");
                        String itemprice = order.getString("itemprice");
                        String   itemqty = order.getString("itemqty");
                        String   totalprice = order.getString("totalprice");
                        String   pickupdate = order.getString("pickupdate");
                        String   pickuptime = order.getString("pickuptime");
                        String   status = order.getString("status");



                        MyOrder_Modal orderModal=new MyOrder_Modal();
                        orderModal.setOrderID(orderId);
                        orderModal.setOrderdate(ordedate);
                        orderModal.setItems(items);
                        orderModal.setItemprice(itemprice);
                        orderModal.setItemqty(itemqty);
                        orderModal.setTotalprice(totalprice);
                        orderModal.setPickupdate(pickupdate);
                        orderModal.setPickuptime(pickuptime);
                        orderModal.setStatus(status);
                        product.add(orderModal);
                        MyOrder_Adapter adapter=new MyOrder_Adapter(MyOrder_Activity.this,product);
                        recyclerView.setAdapter(adapter);
//


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(MyOrder_Activity.this, "connection fail", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>parms=new HashMap<>();
                parms.put("email",email);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
