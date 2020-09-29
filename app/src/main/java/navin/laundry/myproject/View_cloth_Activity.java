package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class View_cloth_Activity extends AppCompatActivity {
   RecyclerView recyclerView;
   ArrayList<ProductView_modal>product;
   RecyclerView.LayoutManager layoutManager;
    String PRODUCT_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/api/myproduct.php";

//    String PRODUCT_URL="http://192.168.43.65/laundry_service/api/myproduct.php?";

    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cloth_);
        recyclerView=findViewById(R.id.Rv_men_cloth);
        product=new ArrayList<>();
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Intent catintent=getIntent();
        category=catintent.getStringExtra("category");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category+" product");
        final ProgressDialog progressDialog = new ProgressDialog(View_cloth_Activity.this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("product_detail");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject shirt=jsonArray.getJSONObject(i);
                        String name=shirt.getString("Product_name");
                        String description=shirt.getString("Product_description");
                        String price=shirt.getString("Product_price");
                        String image=shirt.getString("Product_image");
                        ProductView_modal modal=new ProductView_modal();

                        modal.setName(name);
                        modal.setDescription(description);
                        modal.setPrice(price);
                        modal.setImage(image);
                        product.add(modal);
                        ProdctView_Adapter adapter=new ProdctView_Adapter(getApplicationContext(),product);
                        recyclerView.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "connection fail", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms=new HashMap<>();
                parms.put("category_name",category);
                return parms;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
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
