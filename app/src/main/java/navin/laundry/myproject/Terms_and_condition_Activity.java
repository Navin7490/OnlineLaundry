package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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


public class Terms_and_condition_Activity extends AppCompatActivity {
    TextView tvterm;
    String TERMS_CONDITION_URL="http://192.168.43.65/laundry_service/api/terms_condition.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition_);
        tvterm=findViewById(R.id.Tvs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Terms Condition");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, TERMS_CONDITION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("laundry_condition");
                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject object=jsonArray.getJSONObject(i);
                        String condition=object.getString("t_condition");
                        tvterm.setText(condition);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
       progressDialog.dismiss();
                Toast.makeText(Terms_and_condition_Activity.this, "connection fail", Toast.LENGTH_SHORT).show();
            }
        });
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
