package navin.laundry.myproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_ViewOrderAdapter extends RecyclerView.Adapter<Admin_ViewOrderAdapter.viewhoder> {
    private Context context;
    private ArrayList<Admin_ViewOrderModal> product;

    public Admin_ViewOrderAdapter(Context context, ArrayList<Admin_ViewOrderModal> product) {
        this.context=context;
        this.product=product;
    }
    @NonNull
    @Override
    public Admin_ViewOrderAdapter.viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View viewlist=layoutInflater.inflate(R.layout.admin_list_order_item,parent,false);
        return new viewhoder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_ViewOrderAdapter.viewhoder holder, int position) {
        holder.odate.setText(product.get(position).getOrderdate());
        holder.oId.setText(product.get(position).getOrderId());
        holder.cname.setText(product.get(position).getCname());
        holder.tvstatus.setText(product.get(position).getCstatus());
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class viewhoder extends RecyclerView.ViewHolder {
        TextView odate,oId,cname,tvstatus;
        Button btnviewdetail;
        String VIEWORDER="https://navindeveloperinfo.000webhostapp.com/laundry_service/view_orderDetail.php";
       // String VIEWORDER="http://192.168.43.65/laundry_service/view_orderDetail.php";
        public viewhoder(@NonNull View itemView) {
            super(itemView);
            odate=itemView.findViewById(R.id.Tv_OrderdateView);
            oId=itemView.findViewById(R.id.Tv_OrderIdView);
            cname=itemView.findViewById(R.id.Tv_CustomerNameView);
            tvstatus=itemView.findViewById(R.id.Tv_A_status);

            btnviewdetail=itemView.findViewById(R.id.Btn_ViewOrder);
            btnviewdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final String orderId=oId.getText().toString();
                    StringRequest stringRequest =new StringRequest(Request.Method.POST, VIEWORDER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String statu=jsonObject.getString("statuss");
                                if (statu.equals("sucesss")){
                                    String orderid=jsonObject.getString("order_id");
                                    String name=jsonObject.getString("username");
                                    String mobile=jsonObject.getString("mobile");
                                    String address=jsonObject.getString("address");
                                    String orderdate=jsonObject.getString("order_date");

                                    String items=jsonObject.getString("items");
                                    String itemqty=jsonObject.getString("itemqty");
                                    String itemprice=jsonObject.getString("itemprice");
                                    String totalprice=jsonObject.getString("totalprice");
                                    String pickupdate=jsonObject.getString("pickupdate");
                                    String pickuptime=jsonObject.getString("pickuptime");
                                    String cstatus=jsonObject.getString("status");

                                    //Toast.makeText(context, "order :"+mobile+":"+name, Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(context,Admin_ViewOrder_Activity.class);
                                    intent.putExtra("orderid",orderid);
                                    intent.putExtra("username",name);
                                    intent.putExtra("mobile",mobile);
                                    intent.putExtra("address",address);
                                    intent.putExtra("orderdate",orderdate);
                                    intent.putExtra("items",items);
                                    intent.putExtra("itemqty",itemqty);
                                    intent.putExtra("itemprice",itemprice);
                                    intent.putExtra("totalprice",totalprice);
                                    intent.putExtra("pickupdate",pickupdate);
                                    intent.putExtra("pickuptime",pickuptime);
                                    intent.putExtra("status",cstatus);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>parms=new HashMap<>();
                            parms.put("order_id",orderId);
                            return parms;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
                }
            });
        }
    }
}
