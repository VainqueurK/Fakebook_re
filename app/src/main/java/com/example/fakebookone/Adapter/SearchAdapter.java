package com.example.fakebookone.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    ArrayList<ChatSearchResults> list;

    public SearchAdapter(ArrayList<ChatSearchResults> list){
        this.list = list;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchedprofile, viewGroup, false);
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

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
            nameText = itemView.findViewById(R.id.resultUsername);
            bioText = itemView.findViewById(R.id.resultSearchName);
        }
    }
}

