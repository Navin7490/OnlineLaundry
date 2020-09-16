package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Select_Payment_Method_Activity extends AppCompatActivity {
    RadioButton rbtngooglePay,rbtndebitcard,rbtncreditcard,rbtninterbank;
    TextView tvamount,tvconfirm,tvnext;
    Toast toast;
    String Amountpay;
    ProgressDialog progressDialog;
    String oredrdate, username,email,mobile,address,items,itemqty,itemprice,totalprice,pickupdate,pickuptime,status;
    String ORDER_URL="http://192.168.43.65/laundry_service/api/insert_order.php";
    Intent intent;

    /// google pay payment//
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    Uri uri;
    String upiid = "tinu1316@oksbi";
    String name = "Navin";
    String note = "Laundry service";
    String staus;
    // end google pay//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__payment__method_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Payment Method");

        rbtngooglePay=findViewById(R.id.Rbtn_GooglePay);
        rbtndebitcard=findViewById(R.id.Rbtn_Debit);
        rbtncreditcard=findViewById(R.id.Rbtn_Credit);
        rbtninterbank=findViewById(R.id.Rbtn_Internetbank);
        tvamount=findViewById(R.id.Tv_amoutapayable);
        tvconfirm=findViewById(R.id.Tv_Confirm);
        tvnext=findViewById(R.id.Tv_Next);
        User_Order_Modal order=new User_Order_Modal(Select_Payment_Method_Activity.this);
        Amountpay=  order.shareprefmyorder.getString("total_price",null);
        tvamount.setText(Amountpay);
        User_login_modal login_modal=new User_login_modal(Select_Payment_Method_Activity.this);
        email= login_modal.sharedPreferencesLogin.getString("LEmail",null);
        oredrdate=order.shareprefmyorder.getString("order_date",null);
        username=  order.shareprefmyorder.getString("user_name",null);
        mobile=  order.shareprefmyorder.getString("mobile",null);
        address=  order.shareprefmyorder.getString("address",null);
        items=  order.shareprefmyorder.getString("items",null);
        itemqty=  order.shareprefmyorder.getString("item_qty",null);
        itemprice=  order.shareprefmyorder.getString("item_price",null);
        totalprice=  order.shareprefmyorder.getString("total_price",null);
        pickupdate=  order.shareprefmyorder.getString("pickup_date",null);
        pickuptime=  order.shareprefmyorder.getString("pickup_time",null);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rbtngooglePay.isChecked()&&!rbtndebitcard.isChecked()&&!rbtncreditcard.isChecked()
                        &&!rbtninterbank.isChecked()){
                    toast=Toast.makeText(Select_Payment_Method_Activity.this, "Select payment Method", Toast.LENGTH_SHORT);
                    toast.show();
                    toast.setGravity(Gravity.CENTER,0,0);

                }




            }
        });
        rbtngooglePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtngooglePay.isChecked();
                tvconfirm.setVisibility(View.VISIBLE);
                tvconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!totalprice.equals("")) {

                            uri = getUPIPaymentUri(name, upiid, note, totalprice);
                            payWithGPay();
                        }

                    }
                });

            }
        });
//


    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TEZ_REQUEST_CODE) {
//            // Process based on the data in response.
//            Log.d("result", data.getStringExtra("Status"));
//        }
        if (data!=null){
            staus=data.getStringExtra("Status").toLowerCase();

        }
        if ((RESULT_OK==resultCode )&& staus.equals("success")){
            toast= Toast.makeText(getApplicationContext(), "Trasaction successfull", Toast.LENGTH_SHORT);
            toast.show();
            toast.setGravity(Gravity.CENTER,0,0);
            GooglePayOrder();
        }

//       if (RESULT_OK!=resultCode && !staus.equals("success")){
//            Toast.makeText(getApplicationContext(), "Trasaction fail"+data.getStringExtra("approvel"), Toast.LENGTH_SHORT).show();
//
//        }
        else {
             toast= Toast.makeText(getApplicationContext(), "Trasaction cancel by user", Toast.LENGTH_SHORT);
             toast.show();
             toast.setGravity(Gravity.CENTER,0,0);
        }

    }

    private void payWithGPay() {
        if (isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            toast= Toast.makeText(Select_Payment_Method_Activity.this, "Please Install GPay", Toast.LENGTH_SHORT);
            toast.show();
            toast.setGravity(Gravity.CENTER,0,0);
        }
    }


    private static Uri getUPIPaymentUri(String name, String UPIId, String note, String amount) {
          return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", UPIId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

    }

    private static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /////  End google pay payment event /////////


    private  void GooglePayOrder(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                Log.d("order",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("sucess")){
                        toast=Toast.makeText(Select_Payment_Method_Activity.this, "Order Confirm", Toast.LENGTH_SHORT);
                        toast.show();
                        toast.setGravity(Gravity.CENTER,0,0);
                        Intent intent=new Intent(getApplicationContext(),Navigation_Activity.class);
                        finish();

                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(Select_Payment_Method_Activity.this, "fail", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Select_Payment_Method_Activity.this, "try again"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms=new HashMap<>();
                parms.put("order_id","");
                parms.put("order_date",oredrdate);
                parms.put("username",username);
                parms.put("email",email);
                parms.put("mobile",mobile);
                parms.put("address",address);
                parms.put("items",items);
                parms.put("itemqty",itemqty);
                parms.put("itemprice",itemprice);
                parms.put("totalprice",totalprice);
                parms.put("pickupdate",pickupdate);
                parms.put("pickuptime",pickuptime);

                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Select_Payment_Method_Activity.this);
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



 // rbtndebitcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rbtndebitcard.isChecked();
//                tvconfirm.setVisibility(View.GONE);
//            }
//        });
//        rbtncreditcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rbtncreditcard.isChecked();
//                tvconfirm.setVisibility(View.GONE);
//
//            }
//        });
//        rbtninterbank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rbtninterbank.isChecked();
//                tvconfirm.setVisibility(View.GONE);
//
//            }
//        });
