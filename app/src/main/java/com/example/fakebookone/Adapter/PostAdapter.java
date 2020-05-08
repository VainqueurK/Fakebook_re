package com.example.fakebookone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebookone.Misc.Model.Post;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(mContext).inflate(R.layout.post, parent, false);

        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);

        if(post.getDescription().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }
        else
        {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }

        publisherInfo(holder.profilePic, holder.publisher, holder.postDate);
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profilePic;
        public TextView description, publisher, postDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           profilePic = itemView.findViewById(R.id.postImage);
           description = itemView.findViewById(R.id.postContent);
           publisher = itemView.findViewById(R.id.postUsername);
           postDate = itemView.findViewById(R.id.postDate);
        }
    }

    private void publisherInfo(ImageView profilePic, TextView username, TextView date){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(username));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Profile user = dataSnapshot.getValue(Profile.class);
                Glide.with(mContext).load(user.getImageurl()).into(profilePic);
                username.setText(user.getUsername());



                date = LocalDateTime.now();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
