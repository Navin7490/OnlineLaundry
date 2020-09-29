package navin.laundry.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import navin.laundry.myproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Admin_ViewOrder_Activity extends AppCompatActivity {
    TextView orderid,name,mobile,address,orderdate,items,itemprice,itemQTY,totalPrice,pickupdate,pickuptime,payment;
    EditText cstatuss;
    Button btnprint,btnupdatestatus;
    String corderid,cstatus,chaorderid,chastatus;
    String status="";
    String CHANGE_STATUS="https://navindeveloperinfo.000webhostapp.com/laundry_service/admin_change_order_status.php";
   // String CHANGE_STATUS="http://192.168.43.65/laundry_service/admin_change_order_status.php";
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view_order_);

        getSupportActionBar().setTitle("Admin");

        orderid=findViewById(R.id.Tv_OrderIdCustomer);
        name=findViewById(R.id.Tv_CustomerName);
        mobile=findViewById(R.id.Tv_CustomerMobileNo);
        address=findViewById(R.id.Tv_CustomerAddress);
        orderdate=findViewById(R.id.Tv_Orderdate);
        items=findViewById(R.id.TV_Items);
        itemprice=findViewById(R.id.TV_ItemPrice);
        itemQTY=findViewById(R.id.TV_ItemQty);
        totalPrice=findViewById(R.id.TV_TotalPriceitem);
        pickupdate=findViewById(R.id.TV_Pickupdate);
        pickuptime=findViewById(R.id.TV_Pickuptime);
        payment=findViewById(R.id.Tv_CustomerPayment);
        cstatuss=findViewById(R.id.TV_Status);
        btnprint=findViewById(R.id.Btn_A_Print);
        btnupdatestatus=findViewById(R.id.Btn_A_update_s);

        Intent intent=getIntent();
         corderid= intent.getStringExtra("orderid");
        String cname= intent.getStringExtra("username");
        String cmobile= intent.getStringExtra("mobile");
        String caddress= intent.getStringExtra("address");
        String corderdate= intent.getStringExtra("orderdate");
        String citems= intent.getStringExtra("items");
        String citemqty= intent.getStringExtra("itemqty");
        String citemprice= intent.getStringExtra("itemprice");
        String ctotalprice= intent.getStringExtra("totalprice");
        String cpickupdate= intent.getStringExtra("pickupdate");
        String cpickuptime= intent.getStringExtra("pickuptime");
         cstatus= intent.getStringExtra("status");

        orderid.setText(corderid);
        name.setText(cname);;
        mobile.setText(cmobile);;
        address.setText(caddress);;
        orderdate.setText(corderdate);;
        items.setText(citems);;
        itemQTY.setText(citemqty);;
        itemprice.setText(citemprice);;
        totalPrice.setText(ctotalprice);;
        pickupdate.setText(cpickupdate);;
        pickuptime.setText(cpickuptime);;
        cstatuss.setText(cstatus);

        btnupdatestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chastatus=cstatuss.getText().toString();
                chaorderid=orderid.getText().toString();
                if (chastatus.isEmpty()) {
                    toast = Toast.makeText(Admin_ViewOrder_Activity.this, "Please enter status", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    StringRequest updaterequest = new StringRequest(Request.Method.POST, CHANGE_STATUS, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("update", response);

                            try {
                                JSONObject job = new JSONObject(response);
                                String status = job.getString("statuss");
                                if (status.equals("success")) {
                                    toast = Toast.makeText(Admin_ViewOrder_Activity.this, "Changed status", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent updatestatus = new Intent(Admin_ViewOrder_Activity.this, Admin_Home_Activity.class);
                                    startActivity(updatestatus);
                                    finish();

                                } else {
                                    toast = Toast.makeText(Admin_ViewOrder_Activity.this, "fail", Toast.LENGTH_SHORT);
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

                            toast = Toast.makeText(Admin_ViewOrder_Activity.this, "try  again", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parmsdu = new HashMap<>();
                            // parmsd.put("id",id);
                            parmsdu.put("order_id", chaorderid);
                            parmsdu.put("status", chastatus);

                            return parmsdu;
                        }
                    };
                    RequestQueue changstatus = Volley.newRequestQueue(Admin_ViewOrder_Activity.this);
                    changstatus.add(updaterequest);
                }
            }
        });

        btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}