package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mycart_Adapter extends RecyclerView.Adapter<Mycart_Adapter.Viewholder> {
    private Context context=null;
    private ArrayList<Mycard_modal> product;
    String nu;
    int qu;
    int pu;
    String imgu;
    int qutot;
    int putot;
    String  total;
    String id,nd,qd,pd,imgd;
    String DELETE_FROM_CARTURL="http://192.168.43.65/laundry_service/api/delete_from_cart.php";
    Toast toast;
    ProgressDialog progressDialog;
    Dialog dialog;
    String UPDATECART_URL="http://192.168.43.65/laundry_service/api/updatecart.php";
    ArrayAdapter<String>adapter;

    public Mycart_Adapter(Context context, ArrayList<Mycard_modal> product) {
        this.context = context;
        this.product = product;
        progressDialog=new ProgressDialog(context);
        dialog=new Dialog(context);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog.setContentView(R.layout.alert_dailoag_custom);
        dialog.setCanceledOnTouchOutside(false);
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View viewlist=layoutInflater.inflate(R.layout.my_cart_list_item,parent,false);
        return new Viewholder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
       // progressDialog.setContentView(R.layout.my_cart_list_item);
        holder.name.setText(product.get(position).getName());
        holder.quntity.setText(product.get(position).getQulity());
        holder.price.setText(product.get(position).getPrice());
      // Glide.with(context).load(product.get(position).getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        holder.tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // deletefromcart();
                //progressDialog=ProgressDialog.show(context,"","please wait",false);
                Mycard_modal mycard_modal=product.get(position);
                nd=mycard_modal.getName();
                qd=mycard_modal.getQulity();
                pd=mycard_modal.getPrice();
               // id=mycard_modal.getId();
               TextView yes=dialog.findViewById(R.id.TV_YesDe);
               TextView no=dialog.findViewById(R.id.TV_NoDe);
                dialog.show();

//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progrees_dialog);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        StringRequest deleterequest=new StringRequest(Request.Method.POST, DELETE_FROM_CARTURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                 progressDialog.dismiss();
                                Log.d("delete", response);

                                try {
                                    JSONObject job= new JSONObject(response);
                                    String status=job.getString("status");
                                    if (status.equals("sucess")){
                                        toast= Toast.makeText(context.getApplicationContext(), "Deleted item", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.show();
                                Intent mycart=new Intent(context.getApplicationContext(),Navigation_Activity.class);
                                context. startActivity(mycart);

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
                                progressDialog.dismiss();
                                toast= Toast.makeText(context.getApplicationContext(),"try  again", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>parmsd=new HashMap<>();
                                // parmsd.put("id",id);
                                parmsd.put("u_p_name",nd);
                                parmsd.put("u_p_quntity",qd);
                                parmsd.put("u_p_price",pd);

                                return parmsd;
                            }
                        };
                        RequestQueue deletequeue= Volley.newRequestQueue(context.getApplicationContext());
                        deletequeue.add(deleterequest);
                    }
                });


            }
        });
        holder.tvupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mycard_modal mycard_modal = product.get(position);
                nu = mycard_modal.getName();
                pu = Integer.parseInt(mycard_modal.getPrice());
                imgu = mycard_modal.getImage();
                //qu=mycard_modal.getQuntyupdate();

                if (qu ==0) {
                   toast= Toast.makeText(context.getApplicationContext(), "select quntity", Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER,0,0);
                   toast.show();
                } else {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progrees_dialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    total = String.valueOf(qu*pu);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATECART_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             progressDialog.dismiss();
                            Log.d("update", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String status = jsonObject.getString("status");
                                if (status.equals("sucess")) {

                                    toast = Toast.makeText(context.getApplicationContext(), "Cart Upadate Successfully", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent updateintent = new Intent(context.getApplicationContext(), Navigation_Activity.class);
                                    context.startActivity(updateintent);

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
                            progressDialog.dismiss();
                            toast = Toast.makeText(context.getApplicationContext(), "try again", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parms = new HashMap<>();
                            parms.put("u_p_name", nu);
                            parms.put("u_p_quntity", String.valueOf(qu));
                            parms.put("u_p_price", String.valueOf(pu));
                            parms.put("total_ptice", String.valueOf(total));

                            // parms.put("u_p_image", String.valueOf(image));

                            return parms;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                }
            }

        });



    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name,quntity,price, tvdelete,tvupdate;
        Spinner spinner;
        ImageView image;
        @SuppressLint("ResourceType")
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Tv_pnamed);
            quntity=itemView.findViewById(R.id.Tv_pquntityd);
            price=itemView.findViewById(R.id.Tv_ppriced);
            //image=itemView.findViewById(R.id.Im_procart);
            tvdelete=itemView.findViewById(R.id.Tv_pdeletebtn);
            tvupdate=itemView.findViewById(R.id.Tv_pupdatebtn);
            spinner=itemView.findViewById(R.id.Sp_uquntity);


            final ArrayAdapter adapter=ArrayAdapter.createFromResource(context,R.array.spinner_item,R.layout.spinner_color);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        for (int i = 0; i < position; i++) {
                            qu = position;

                        }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    public void deletefromcart(){
//
           AlertDialog.Builder builder=new AlertDialog.Builder(context);
              //  builder.setCancelable(false);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete item");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

               Dialog dialog= builder.create();
               dialog.show();




    }

}
