package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Mycart_Adapter extends RecyclerView.Adapter<Mycart_Adapter.Viewholder> {
    private Context context;
    private ArrayList<Mycard_modal> product;


    public Mycart_Adapter(Context context, ArrayList<Mycard_modal> product) {
        this.context = context;
        this.product = product;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View viewlist=layoutInflater.inflate(R.layout.my_cart_list_item,parent,false);
        return new Viewholder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.name.setText(product.get(position).getName());
        holder.quntity.setText(product.get(position).getQulity());
        holder.price.setText(product.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name,quntity,price;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Tvsname);
            quntity=itemView.findViewById(R.id.Tvsqulity);
            price=itemView.findViewById(R.id.Tvsprice);
        }
    }
}
