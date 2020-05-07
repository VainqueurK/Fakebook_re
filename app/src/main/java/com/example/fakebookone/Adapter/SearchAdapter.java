package com.example.fakebookone.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.SearchActivity;
import com.example.fakebookone.Activity.UserpageActivity;
import com.example.fakebookone.Misc.SearchResults;
import com.example.fakebookone.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    ArrayList<SearchResults> list;
    private Context context;
    private String uid;


    public SearchAdapter(ArrayList<SearchResults> list, Context context1 )
    {
        this.list = list;
        this.context = context1;
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
        uid = list.get(position).getUsername();

        holder.profileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserpageActivity.class);
                intent.putExtra("uid", uid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameText, bioText;
        LinearLayoutCompat profileButton;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameText = itemView.findViewById(R.id.resultUsername);
            bioText = itemView.findViewById(R.id.resultSearchName);
            profileButton = itemView.findViewById(R.id.profileButton);

        }
    }
}

