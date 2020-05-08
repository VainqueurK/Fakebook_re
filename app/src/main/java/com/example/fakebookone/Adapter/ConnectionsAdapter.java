package com.example.fakebookone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.MessageUser;
import com.example.fakebookone.Activity.UserpageActivity;
import com.example.fakebookone.Misc.Model.ChatRoom;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionsAdapter.MyViewHolder>{


    ArrayList<Profile> list;
    Context mContext;
    public ConnectionsAdapter(ArrayList<Profile> list, Context context){
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.connections_user, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(list.get(position).getUsername());
        holder.bio.setText(list.get(position).getBio());
        holder.chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MessageUser.class);
                /*intent.putExtra("profile_list", list.get(position));*/
                v.getContext().startActivity(intent);
            }
        });
        holder.profileTile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UserpageActivity.class);
                i.putExtra("profileId", list.get(position).getId());
                v.getContext().startActivity(i);
            }
        });
        //TODO add image link

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        LinearLayout profileTile;
        CircleImageView conn_profile_image;
        TextView username, bio;
        ImageButton chatButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileTile = itemView.findViewById(R.id.conn_tile);
            conn_profile_image = itemView.findViewById(R.id.conn_profile_image);
            bio = itemView.findViewById(R.id.conn_bio);
            username = itemView.findViewById(R.id.conn_username);
            chatButton = itemView.findViewById(R.id.chat_button_conn);

        }
    }
}
