package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class My_cart_Activity extends AppCompatActivity {
    TextView tvtotalprice,tvplaceorder;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Mycard_modal>prodct;
    Toast toast;
    Mycart_Adapter adapter;
    String items,itemqty,itemprice,sum;
    String CART_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/mycart.php";

//    String CART_URL="http://192.168.43.65/laundry_service/api/mycart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_);
        recyclerView=findViewById(R.id.Rvcart);
        tvtotalprice=findViewById(R.id.Tv_totalprice);
        tvplaceorder=findViewById(R.id.Tv_totalplaceorser);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Cart");

         prodct=new ArrayList<>();
//        prodct.add(new Mycard_modal(R.drawable.jeans,"shirt","dry clean","100"));
//        prodct.add(new Mycard_modal(R.drawable.oblanket,"jeans","dry clean and iron","200"));
//        prodct.add(new Mycard_modal(R.drawable.silkisaree,"lehenga","dry and clean","300"));
////        prodct.add(new Mycard_modal(R.drawable.obathtowal,"servani","dry","400"));
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final User_login_modal userLogin=new User_login_modal( My_cart_Activity.this );
        final String email=userLogin.sharedPreferencesLogin.getString( "LEmail" ,null);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ress",response);
              progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("cart_product");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject mycart = jsonArray.getJSONObject(i);
                                 String name = mycart.getString("u_p_name");
                                 String quntity = mycart.getString("u_p_quntity");
                                 String price = mycart.getString("u_p_price");
                                 //String image=mycart.getString("u_p_image");


                                    Mycard_modal mycardModal = new Mycard_modal();
                                    mycardModal.setName(name);
                                    mycardModal.setQulity(quntity);
                                    mycardModal.setPrice(price);
                                   // mycardModal.setImage(image);
                                    prodct.add(mycardModal);
                                    adapter = new Mycart_Adapter(My_cart_Activity.this, prodct);
                                    recyclerView.setAdapter(adapter);
//
                                    sum = jsonObject.getString("sum");
                                    tvtotalprice.setText(sum);
                                    items = jsonObject.getString("items");
                                    itemqty = jsonObject.getString("item qty");
                                    itemprice = jsonObject.getString("item price");

                             }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(My_cart_Activity.this, "connection fail", Toast.LENGTH_SHORT);
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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        tvplaceorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter==null){
                   toast= Toast.makeText(My_cart_Activity.this, "cart is empty", Toast.LENGTH_SHORT);
                   toast.show();
                   toast.setGravity(Gravity.CENTER,0,0);
                }
                else {
                    User_Order_Modal order = new User_Order_Modal(My_cart_Activity.this);
                    order.setItems(items);
                    order.setItemqty(itemqty);
                    order.setItemprice(itemprice);
                    order.setTotalprice(sum);

                    Intent orderintent = new Intent(getApplicationContext(), Place_order_Activity.class);
                    startActivity(orderintent);
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
