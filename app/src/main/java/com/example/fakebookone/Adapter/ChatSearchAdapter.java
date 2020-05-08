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
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.MessageUser;
import com.example.fakebookone.Fragment.FragmentChat;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.Misc.Model.ChatRoom;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;

//import org.ocpsoft.prettytime.PrettyTime;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatSearchAdapter extends RecyclerView.Adapter<ChatSearchAdapter.MyViewHolder>{


    ArrayList<ChatRoom> list;
    Context mContext;
    public ChatSearchAdapter(ArrayList<ChatRoom> list, Context context){
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_user, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(list.get(position).getUserTwo().getUsername());
        switch(list.get(position).getLastMessage().getType()){
            case 1:{
                holder.latest.setText(list.get(position).getLastMessage().getSender().equals(StaticData.MYPROFILE.getId())?"you sent a picture":"Sent a picture");
            }break;
            default:{
                holder.latest.setText(list.get(position).getLastMessage().getContent());
            }break;
        }
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        String ago = prettyTime.format(new Date(list.get(position).getLastMessage().getTimestamp()));
        holder.latest.setText(ago);


        holder.chatTile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MessageUser.class);
                /*intent.putExtra("profile_list", list.get(position));*/
                v.getContext().startActivity(intent);
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
        LinearLayout chatTile;
        CircleImageView chat_profile_image;
        TextView username, latest, timestamp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chatTile = itemView.findViewById(R.id.chat_tile);
            chat_profile_image = itemView.findViewById(R.id.chat_profile_image);
            latest = itemView.findViewById(R.id.chat_latest);
            username = itemView.findViewById(R.id.chat_username);
            timestamp = itemView.findViewById(R.id.chat_timestamp);
        }
    }
}
