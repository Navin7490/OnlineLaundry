package navin.laundry.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Purchases_Activity extends AppCompatActivity implements PaymentResultListener {
    Button btnpay;
    TextView tvamount;

    String ORDER_URL = "https://navindeveloperinfo.000webhostapp.com/laundry_service/api/insert_order.php";
    ProgressDialog progressDialog;
    Toast toast;
    String Amountpay;
    String oredrdate, username, email, mobile,mobile2, address, items, itemqty, itemprice, pickupdate, pickuptime, status;
    String amoutpayble = "Amount Payable ₹:";
  String totalprice;
    int totalamount;
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases_);
        btnpay = findViewById(R.id.BtnPurchaese);
        tvamount=findViewById(R.id.Tv_amoutapayable);



        User_Order_Modal order = new User_Order_Modal(Purchases_Activity.this);
        Amountpay = order.shareprefmyorder.getString("total_price", null);
        tvamount.setText(amoutpayble.concat(Amountpay));
        User_login_modal login_modal = new User_login_modal(Purchases_Activity.this);
        email = login_modal.sharedPreferencesLogin.getString("LEmail", null);
        oredrdate = order.shareprefmyorder.getString("order_date", null);
        username = order.shareprefmyorder.getString("user_name", null);
        mobile = order.shareprefmyorder.getString("mobile", null);
        mobile2 = order.shareprefmyorder.getString("mobile2", null);
        address = order.shareprefmyorder.getString("address", null);
        items = order.shareprefmyorder.getString("items", null);
        itemqty = order.shareprefmyorder.getString("item_qty", null);
        itemprice = order.shareprefmyorder.getString("item_price", null);
        totalprice = order.shareprefmyorder.getString("total_price", null);
        pickupdate = order.shareprefmyorder.getString("pickup_date", null);
        pickuptime = order.shareprefmyorder.getString("pickup_time", null);

        t= Integer.parseInt(totalprice);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUpPayment();
            }
        });
    }

    private void SetUpPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_v1lezGFouPayNY");
        Checkout.preload(getApplicationContext());
         totalamount = 5* 100;
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", username);
            options.put("description", "Laundry Service");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#2196f3");
            options.put("currency", "INR");
            options.put("amount",totalamount );//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", mobile);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment SuccessFull" , Toast.LENGTH_SHORT).show();
        PayOrder();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed" , Toast.LENGTH_SHORT).show();

    }

    // order

    private void PayOrder() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                Log.d("order", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("sucess")) {
                        toast = Toast.makeText(Purchases_Activity.this, "Order Confirm", Toast.LENGTH_SHORT);
                        toast.show();
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        Intent intent = new Intent(getApplicationContext(), Navigation_Activity.class);
                        finish();

                        startActivity(intent);

                    } else {
                        Toast.makeText(Purchases_Activity.this, "fail", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                Toast.makeText(Purchases_Activity.this, "try again" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("order_id", "");
                parms.put("order_date", oredrdate);
                parms.put("username", username);
                parms.put("email", email);
                parms.put("mobile", mobile2);
                parms.put("address", address);
                parms.put("items", items);
                parms.put("itemqty", itemqty);
                parms.put("itemprice", itemprice);
                parms.put("totalprice", "5");
                parms.put("pickupdate", pickupdate);
                parms.put("pickuptime", pickuptime);


                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Purchases_Activity.this);
        requestQueue.add(stringRequest);
    }

}