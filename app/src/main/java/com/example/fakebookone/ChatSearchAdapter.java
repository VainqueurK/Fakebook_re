package com.example.fakebookone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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
        holder.chatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                Intent activityMessageIntent = new Intent(V.getContext(),
                MessageUser.class);
                V.getContext().startActivity(activityMessageIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, bioText;
        Button chatButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            bioText = itemView.findViewById(R.id.bio_text);
            chatButton = itemView.findViewById(R.id.chat_with_user);
        }
    }
}
