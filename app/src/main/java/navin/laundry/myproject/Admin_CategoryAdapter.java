package navin.laundry.myproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import navin.laundry.myproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_CategoryAdapter extends RecyclerView.Adapter<Admin_CategoryAdapter.viewholder> {

    private Context context;
    private ArrayList<Admin_CategoryModal> product;
    public Admin_CategoryAdapter(Context context, ArrayList<Admin_CategoryModal> product) {
        this.context = context;
        this.product = product;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.admin_category_list,parent,false);
        return new viewholder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.name.setText(product.get(position).getCatename());
        Glide.with(context).load(product.get(position).getCateimage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.ImViCategory);
            name=itemView.findViewById(R.id.Tv_cateName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String catename=name.getText().toString();

                        Intent intent=new Intent(context,Admin_ViewProduct_Activity.class);
                        intent.putExtra("category",catename);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);



                }
            });
        }
    }
}
