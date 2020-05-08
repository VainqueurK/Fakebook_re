package com.example.fakebookone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.LoginActivity;
import com.example.fakebookone.Activity.MessageUser;
import com.example.fakebookone.Activity.UserpageActivity;
import com.example.fakebookone.Fragment.FragmentChat;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{


    ArrayList<Profile> list;
    Context context;

    public SearchAdapter(ArrayList<Profile> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchedprofile, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.nameText.setText(list.get(position).getUsername());
        holder.bioText.setText(list.get(position).getBio());

        holder.button.setOnClickListener( e ->
        {
            Intent i = new Intent(context, UserpageActivity.class);
            i.putExtra("profileId", list.get(position).getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        LinearLayout parentLayout;
        TextView nameText, bioText;
        LinearLayoutCompat button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            nameText = itemView.findViewById(R.id.resultUsername);
            bioText = itemView.findViewById(R.id.resultContent);
            button = itemView.findViewById(R.id.profileButton);
        }
    }
}
