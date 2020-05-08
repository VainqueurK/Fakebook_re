package com.example.fakebookone.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Misc.Model.Message;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder>{


    ArrayList<Message> list;
    Profile me,profile;
    Context mContext;
    public ChatRoomAdapter(ArrayList<Message> list, Context context){
        this.list = list;
        this.mContext = context;
    }
    public ChatRoomAdapter(ArrayList<Message> list, Context context,Profile me,Profile profile){
        this.list = list;
        this.mContext = context;
        this.profile=profile;
        this.me=me;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messagebubble, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        System.out.println("print "+list.get(position).getSender());
        System.out.println("print "+profile.getId());
        System.out.println(list.get(position).getSender().equals(me.getId()));

        if( holder.chat_content!=null) {
            holder.chat_content.setText(list.get(position).getContent());


            if (list.get(position).getSender().equals(me.getId())) {
                holder.cardView.setCardBackgroundColor(0xFFFFAF39);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.RIGHT;
                holder.cardView.setLayoutParams(lp);
            }
            else if((list.get(position).getSender().equals(profile.getId())) ) {
                holder.cardView.setCardBackgroundColor(0xF87D5FF);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.LEFT;
                holder.cardView.setLayoutParams(lp);
            }


            PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
            String ago = prettyTime.format(new Date(list.get(position).getTimeSent()));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        TextView chat_content;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chat_content = itemView.findViewById(R.id.chat_content);
            cardView = itemView.findViewById(R.id.chat_bubble);

        }
    }
}
