package com.example.myproject;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyOrder_Adapter extends RecyclerView.Adapter<MyOrder_Adapter.Viewholder> {
    private Context context=null;
    private ArrayList<MyOrder_Modal> product;
    Dialog dialog;
    public MyOrder_Adapter(Context context, ArrayList<MyOrder_Modal> product) {
        this.context = context;
        this.product = product;
        dialog=new Dialog(context);
    }

    @NonNull
    @Override
    public MyOrder_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog.setContentView(R.layout.alertdialoag_cancelorder);
        dialog.setCanceledOnTouchOutside(false);

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View viewlist=layoutInflater.inflate(R.layout.order_list,parent,false);
        return new Viewholder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrder_Adapter.Viewholder holder, int position) {
        holder.orderId.setText(product.get(position).getOrderID());
        holder.orderdate.setText(product.get(position).getOrderdate());
        holder.items.setText(product.get(position).getItems());
        holder.itemprice.setText(product.get(position).getItemprice());
        holder.itemqty.setText(product.get(position).getItemqty());
        holder.totalprice.setText(product.get(position).getTotalprice());
        holder.pickupdate.setText(product.get(position).getPickupdate());
        holder.pickuptime.setText(product.get(position).getPickuptime());
        holder.status.setText(product.get(position).getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                TextView no=dialog.findViewById(R.id.TV_Noc);
                TextView yes=dialog.findViewById(R.id.TV_Yesc);
             no.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                 }
             });


            }
        });

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView orderId,orderdate,items,itemprice,itemqty,totalprice,pickupdate,pickuptime,status;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.Tv_OrderId);
            orderdate=itemView.findViewById(R.id.Tv_Orderdate);
            items=itemView.findViewById(R.id.TV_Items);
            itemprice=itemView.findViewById(R.id.TV_ItemPrice);
            itemqty=itemView.findViewById(R.id.TV_ItemQty);
            totalprice=itemView.findViewById(R.id.TV_TotalPriceitem);
            pickupdate=itemView.findViewById(R.id.TV_Pickupdate);
            pickuptime=itemView.findViewById(R.id.TV_Pickuptime);
            status=itemView.findViewById(R.id.TV_Status);
        }
    }
}
