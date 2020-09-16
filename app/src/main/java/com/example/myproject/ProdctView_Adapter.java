package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProdctView_Adapter extends RecyclerView.Adapter<ProdctView_Adapter.Viewholder> {
    private Context context;
    private ArrayList<ProductView_modal> product;
    String n,d,p,img;
    Uri imageuri;
    Bitmap bitmap;
    String imagedata;
    Bitmap bmp;
    byte[]bytArray;
    public ProdctView_Adapter(Context context, ArrayList<ProductView_modal> product) {
        this.context = context;
        this.product = product;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.product_list_item, parent, false);
        return new Viewholder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        final ProductView_modal productFragmentModal=product.get(position);
        holder.name.setText( product.get(position).getName());
        holder.description.setText( product.get(position).getDescription());
        holder.price.setText( product.get(position).getPrice());
        Glide.with(context).load(product.get(position).getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                n=productFragmentModal.getName();
                d=productFragmentModal.getDescription();
                p=productFragmentModal.getPrice();
                img= productFragmentModal.getImage();

//             ByteArrayOutputStream stream=new ByteArrayOutputStream();
//             bmp.compress(Bitmap.CompressFormat.JPEG,100,stream);
//            img= String.valueOf(bytArray=stream.toByteArray());
                Intent intent=new Intent(v.getContext(), Add_to_cart_Activity.class);
                // img=imagetostring(bitmap);
                intent.putExtra("name",n);
                intent.putExtra("description",d);
                intent.putExtra("price",p);
                intent.putExtra("image",img);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TableLayout tabLayout;
        TextView name,description,price,rupi;
        ImageView image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tabLayout=itemView.findViewById(R.id.Tablelayout);
            name=itemView.findViewById( R.id.TvproductName );
            description=itemView.findViewById( R.id.TvproDescrip );
            price=itemView.findViewById( R.id.TvPrice );
            rupi=itemView.findViewById(R.id.Tvrupi);
            image=itemView.findViewById( R.id.Improduct );
            //image.setImageBitmap(imageView2Bitmap(image));
        }
    }

    private String imagetostring(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebyte=outputStream.toByteArray();
        String encodedimage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedimage;

    }
}
