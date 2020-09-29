package navin.laundry.myproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import navin.laundry.myproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_ViewProductAdapter extends RecyclerView.Adapter<Admin_ViewProductAdapter.viewholder> {
    private Context context=null;
    private ArrayList<Admin_ViewProductModal> product;
    String procategory,id;
    Dialog dialog;
    String uid,ucat,uname,udecsrip,uprice;
    String DELETE_PRODUCT_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/admin_deleteproduct.php";
   // String DELETE_PRODUCT_URL="http://192.168.43.65/laundry_service/admin_deleteproduct.php";
    String UPDATE_PRODUCT_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/admin_update_product.php";
  //  String UPDATE_PRODUCT_URL="http://192.168.43.65/laundry_service/admin_update_product.php";
    Toast toast;
    ProgressDialog progressDialog;
    public Admin_ViewProductAdapter(Context context, ArrayList<Admin_ViewProductModal> product) {
        this.context = context;
        this.product = product;
        dialog=new Dialog(context);
        progressDialog=new ProgressDialog(context);

    }
    @NonNull
    @Override
    public Admin_ViewProductAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog.setContentView(R.layout.admin_updateproduct_dialog);
        dialog.setCanceledOnTouchOutside(false);

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View viewlist=layoutInflater.inflate(R.layout.admin_view_product_itemlist,parent,false);
        return new viewholder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull final Admin_ViewProductAdapter.viewholder holder, int position) {

        holder.tvid.setText(product.get(position).getId());
        holder.tvcate.setText(product.get(position).getProcategory());
        holder.tvname.setText(product.get(position).getProname());
        holder.tvdescription.setText(product.get(position).getProdescription());
        holder.tvprice.setText(product.get(position).getProprice());
        Glide.with(context).load(product.get(position).getProimage()).into(holder.primage);
        holder.tvedit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view=LayoutInflater.from(context).inflate(R.layout.admin_updateproduct_dialog,null);
                builder.setView(view);
                final AlertDialog alertDialog=builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                Button btnupdate,btncancel;
                final TextView tvuid,tvuptitle;

                final EditText etupname,etudec,etuprice;

                tvuid=view.findViewById(R.id.Tv_UpdateId);
                tvuptitle=view.findViewById(R.id.Tv_ProductUTitle);
                etupname=view.findViewById(R.id.Et_UProduName);
                etudec=view.findViewById(R.id.Et_UDescription);
                etuprice=view.findViewById(R.id.Et_UPrice);

                btncancel=view.findViewById(R.id.Btn_CancelUProduct);
                btnupdate=view.findViewById(R.id.Btn_AddUProduct);
                ucat=holder.tvcate.getText().toString();
                tvuptitle.setText("Update Product in "+ucat+" Category");
                uid=  holder.tvid.getText().toString();
                tvuid.setText(uid);
                uname=holder.tvname.getText().toString();
                etupname.setText(uname);
                udecsrip=holder.tvdescription.getText().toString();
                etudec.setText(udecsrip);
                uprice=holder.tvprice.getText().toString();
                etuprice.setText(uprice);

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uid = tvuid.getText().toString();
                        uname = etupname.getText().toString();
                        udecsrip = etudec.getText().toString();
                        uprice = etuprice.getText().toString();
                        if (uname.isEmpty() || udecsrip.isEmpty() || uprice.isEmpty()) {
                            toast = Toast.makeText(context, "All Filed Required", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {


                            StringRequest updaterequest = new StringRequest(Request.Method.POST, UPDATE_PRODUCT_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.d("update", response);

                                    try {
                                        JSONObject job = new JSONObject(response);
                                        String status = job.getString("status");
                                        if (status.equals("success")) {
                                            toast = Toast.makeText(context.getApplicationContext(), "updated item", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            Intent productUpdate = new Intent(context.getApplicationContext(), Admin_Home_Activity.class);
                                            context.startActivity(productUpdate);

                                        } else {
                                            toast = Toast.makeText(context.getApplicationContext(), "fail", Toast.LENGTH_SHORT);
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

                                    toast = Toast.makeText(context.getApplicationContext(), "try  again", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parmsdu = new HashMap<>();
                                    // parmsd.put("id",id);
                                    parmsdu.put("id", uid);
                                    parmsdu.put("name", uname);
                                    parmsdu.put("description", udecsrip);
                                    parmsdu.put("price", uprice);


                                    return parmsdu;
                                }
                            };
                            RequestQueue upque = Volley.newRequestQueue(context.getApplicationContext());
                            upque.add(updaterequest);
                        }
                    }
                });



                 btncancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                      alertDialog.dismiss();
                     }
                 });



            }
        });


        holder.tvdelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builderdelet=new AlertDialog.Builder(view.getContext());
                View viewdelete=LayoutInflater.from(context).inflate(R.layout.admin_delete_dialog,null);
                builderdelet.setView(viewdelete);
                final AlertDialog alertDialogdelete=builderdelet.create();
                alertDialogdelete.show();
                alertDialogdelete.setCanceledOnTouchOutside(false);

                TextView tvcancel,tvdelete;
                tvcancel=viewdelete.findViewById(R.id.Tv_No);
                tvdelete=viewdelete.findViewById(R.id.Tv_Yes);

                tvdelete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View vdelete) {
                        alertDialogdelete.dismiss();


//                        progressDialog.show();
//                        progressDialog.setContentView(R.layout.admin_progress_dialog);
//                        progressDialog.setCanceledOnTouchOutside(false);
//                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        uid=holder.tvid.getText().toString();

                        StringRequest deleterequest=new StringRequest(Request.Method.POST, DELETE_PRODUCT_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("delete", response);

                                try {
                                    JSONObject job= new JSONObject(response);
                                    String status=job.getString("status");
                                    if (status.equals("success")){
                                        toast= Toast.makeText(context.getApplicationContext(), "Deleted item", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.show();
                                        Intent product=new Intent(context.getApplicationContext(),Admin_Home_Activity.class);
                                        context. startActivity(product);

                                    }
                                    else {
                                        toast= Toast.makeText(context.getApplicationContext(), "fail", Toast.LENGTH_SHORT);
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

                                toast= Toast.makeText(context.getApplicationContext(),"try  again", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>parmsd=new HashMap<>();
                                // parmsd.put("id",id);
                                parmsd.put("id",uid);

                                return parmsd;
                            }
                        };
                        RequestQueue deletequeue= Volley.newRequestQueue(context.getApplicationContext());
                        deletequeue.add(deleterequest);
                    }
                });
                tvcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogdelete.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
         TextView tvid,tvcate, tvname,tvdescription,tvprice,tvedit,tvdelet;
        ImageView primage;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvid=itemView.findViewById(R.id.Tv_productId);
            tvcate=itemView.findViewById(R.id.Tv_produViewcategory);
            primage=itemView.findViewById(R.id.Image_Product);
            tvname=itemView.findViewById(R.id.Tv_productName);
            tvdescription=itemView.findViewById(R.id.Tv_productDescription);
            tvprice=itemView.findViewById(R.id.Tv_productPrice);
            tvedit=itemView.findViewById(R.id.Tv_productUpdate);
            tvdelet=itemView.findViewById(R.id.Tv_productDelete);


        }
    }
}
