package com.example.myproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class KidsDress_Fragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Product_Fragment_modal> product;


    public KidsDress_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_kids_dress_, container, false);
        recyclerView=v.findViewById(R.id.Rvkidsdress);
        ProdctFragmentRecy_Adapter adapter=new ProdctFragmentRecy_Adapter(getContext(),product);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product=new ArrayList<>();
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"shirt pent","dry clean","200"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"shirt pent","dry clean and iron","200"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"jeacket","dry clean and iron","250"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"shirt pent","dry clean","300"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"shirt pent","dry clean","150"));
    }
}
