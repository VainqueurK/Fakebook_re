package com.example.fakebookone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatSearchAdapter extends RecyclerView.Adapter<ChatSearchAdapter.MyViewHolder>{

    ArrayList<ChatSearchResults> list;

    public ChatSearchAdapter(ArrayList<ChatSearchResults> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(list.get(position).getUsername());
        holder.bioText.setText(list.get(position).getBio());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, bioText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            bioText = itemView.findViewById(R.id.bio_text);
        }
    }
}
