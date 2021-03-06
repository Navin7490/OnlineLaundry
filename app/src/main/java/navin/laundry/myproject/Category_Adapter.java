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

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.Viewholder> {
    private Context context;
    private ArrayList<Category_modal> prodct;

    public Category_Adapter(Context context, ArrayList<Category_modal> prodct) {
        this.context = context;
        this.prodct = prodct;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem=layoutInflater.inflate(R.layout.category_list_item,parent,false);
        return new Viewholder(listitem);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.name.setText(prodct.get(position).getName());
       Glide.with(context).load(prodct.get(position).getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return prodct.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Imageview_category);
            name=itemView.findViewById(R.id.Tv_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category=name.getText().toString();
                     Category_Sharpre_Modal categorySharpreModal=new Category_Sharpre_Modal(context.getApplicationContext());
                    Intent intent=new Intent(v.getContext(), View_cloth_Activity.class);
                    intent.putExtra("category",category);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);

                }
            });

        }
    }
}
