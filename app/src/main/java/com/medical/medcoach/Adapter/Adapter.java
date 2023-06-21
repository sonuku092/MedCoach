package com.medical.medcoach.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.medical.medcoach.BlogDetail;
import com.medical.medcoach.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Model> list;

    public Adapter(ArrayList<Model> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void filter_list(ArrayList<Model> filter_list){
        list=filter_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model=list.get(position);
        holder.title.setText(model.getTitle());
        holder.author.setText(model.getAuthor());
        holder.date.setText(model.getDate());
        Glide.with(holder.author.getContext()).load(model.getImgUrl()).into(holder.img);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.author.getContext(), BlogDetail.class);
            intent.putExtra("id", model.getId());
            holder.author.getContext().startActivity(intent);
        });


        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.author.getContext());
            builder.setTitle("What you want to do  ?");
            builder.setMessage("Select Option!");
            builder.setPositiveButton("UPDATE", (dialogInterface, i) -> {
                final Dialog u_dialog= new Dialog(holder.author.getContext());
                u_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                u_dialog.setCancelable(false);
                u_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                u_dialog.setContentView(R.layout.addblogs);
                u_dialog.show();


                AlertDialog dialog1= builder.create();
                dialog1.show();
            });
            builder.setNegativeButton("DELETE", (dialogInterface, i) -> FirebaseFirestore.getInstance().collection("Blogs").document(model.getId()).delete());
            AlertDialog dialog= builder.create();
            dialog.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, author, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.blogimg);
            title = itemView.findViewById(R.id.title1);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date);

        }
    }
}
