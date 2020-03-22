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
public class KidsShirt_Fragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Product_Fragment_modal>product;

    public KidsShirt_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_kids_shirt_, container, false);
        recyclerView=v.findViewById(R.id.Rvkidsshirt);

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
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"kidsshirtpent","dry clean","200"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"kidsshirtpent","dry clean","300"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"kidsshirtpent","dry clean","200"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"kidsshirtpent","dry clean","100"));
        product.add(new Product_Fragment_modal(R.drawable.kisdsirtpent,"kidsshirtpent","dry clean","200"));

    }
}
