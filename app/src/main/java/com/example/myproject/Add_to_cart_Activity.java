package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Add_to_cart_Activity extends AppCompatActivity {
    ImageView image;
    Button btnremove,btnadd,btnaddtocart,btnplaceorder;
    TextView tvpric,tvpname,tvpdetail,tvqulity;

    String[]count={"1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20"};
    int position = -1;
    String n,p,d,q,i;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart_);

        image=findViewById(R.id.Image_detail);
        tvpric=findViewById(R.id.tvShow_price);
        tvpname=findViewById(R.id.tvDproduct_name);
        tvpdetail=findViewById(R.id.tvDproduct_detail);
        tvqulity=findViewById(R.id.Add_numb_item);
        btnadd=findViewById(R.id.BtnAdditem);
        btnremove=findViewById(R.id.BtnRemoveitem);
        btnaddtocart=findViewById(R.id.BtnAdd_to_card_id);
        btnplaceorder=findViewById(R.id.Btnplace_order_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add to cart");


        final Intent intent=getIntent();
        n =intent.getStringExtra("name");
        tvpname.setText(n);
        d=intent.getStringExtra("description");
        tvpdetail.setText(d);
        p=intent.getStringExtra("price");
        tvpric.setText(p);
        Glide.with(this).load(intent.getStringExtra("image")).into(image);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<count.length-1){
                    position=position +1;
                    tvqulity.setText(count[position]);
                    q=count[position];

                }
            }
        });

        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position>0){
                    position=position -1;
                    tvqulity.setText(count[position]);
                }
            }
        });
        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
