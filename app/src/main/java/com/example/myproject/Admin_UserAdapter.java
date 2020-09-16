package com.example.myproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Admin_UserAdapter extends RecyclerView.Adapter<Admin_UserAdapter.viewholder> {
    private ArrayList<Admin_UserModal>useres;
    private Context context;

    public Admin_UserAdapter(ArrayList<Admin_UserModal> useres, Context context) {
        this.useres = useres;
        this.context = context;
    }

    @NonNull
    @Override
    public Admin_UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View viewlist=layoutInflater.inflate(R.layout.admin_users_list,parent,false);

        return new viewholder(viewlist);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_UserAdapter.viewholder holder, int position) {
        holder.tvname.setText(useres.get(position).getName());
        holder.tvemail.setText(useres.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return useres.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tvname,tvemail;
        Button btnviewdetail;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.Tv_A_U_N);
            tvemail=itemView.findViewById(R.id.Tv_A_U_E);
            btnviewdetail=itemView.findViewById(R.id.Btn_A_View_user);
        }
    }
}
