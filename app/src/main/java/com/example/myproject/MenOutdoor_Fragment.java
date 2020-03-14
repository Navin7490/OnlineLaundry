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
public class MenOutdoor_Fragment extends Fragment {
    View v;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Product_Fragment_modal> prodct;
    public MenOutdoor_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_men_outdoor_, container, false);

        recyclerView =v.findViewById(R.id.RvMen_outdoor);

        ProdctFragmentRecy_Adapter adapter=new ProdctFragmentRecy_Adapter(getContext(),prodct);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prodct=new ArrayList<>();
        prodct.add(new Product_Fragment_modal(R.drawable.jeans,"shirt","dry clean","100"));
        prodct.add(new Product_Fragment_modal(R.drawable.oblanket,"jeans","dry clean and iron","200"));
        prodct.add(new Product_Fragment_modal(R.drawable.silkisaree,"lehenga","dry and clean","300"));
        prodct.add(new Product_Fragment_modal(R.drawable.obathtowal,"servani","dry","400"));
    }
}
