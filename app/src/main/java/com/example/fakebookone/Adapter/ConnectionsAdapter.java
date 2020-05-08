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
import com.example.fakebookone.Misc.Model.Message;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionsAdapter.MyViewHolder>{


    ArrayList<Profile> list;
    Context mContext;
    String key;

    public ConnectionsAdapter(ArrayList<Profile> list, Context context,String key){
        this.list = list;
        this.mContext = context;
        this.key=key;
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
                System.out.println("intent---");
                FirebaseFirestore.getInstance().collection("Messages").document(key).collection(key).orderBy("timeSent").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ChatRoom c=new ChatRoom();
                        c.setKey(key);
                        c.setUserOne(StaticData.MYPROFILE);
                        c.setUserTwo(list.get(position));
                        c.setTimestamp(0);

                        if(queryDocumentSnapshots!=null) {
                            for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                                if(ds.exists())
                                c.getMessages().add(ds.toObject(Message.class));
                            }
                            if (c.getMessages().size() > 0) {
                                c.setTimestamp(c.getMessages().get(0).getTimeSent());
                                c.setLastMessage(c.getMessages().get(0));
                            }

                        }
                        intent.putExtra("chatRoom", c);
                        v.getContext().startActivity(intent);
                    }
                });

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
