package com.example.fakebookone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserpageActivity extends AppCompatActivity
{
    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private Profile profile;
    boolean connected = false;

    CircleImageView imageProfile;
    TextView posts, connects, username, bio, postCount, connectCount, fullName, location, occupation;
    ImageButton connect, message, back;
    ImageButton backButton;

    FirebaseUser firebaseUser;
    String profileId;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        //the user you clicked
        Intent intent = getIntent();
        profileId = intent.getStringExtra("profileId");
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(profileId);
        posts = findViewById(R.id.postCount);
        connects = findViewById(R.id.connectCount);
        username = findViewById(R.id.usernameText);
        bio = findViewById(R.id.bioText);
        location = findViewById(R.id.location);
        occupation = findViewById(R.id.occupation);


        postCount = findViewById(R.id.postCount);
        connectCount = findViewById(R.id.connectCount);
        fullName = findViewById(R.id.fullName);

        connect = findViewById(R.id.connectButton);
        message = findViewById(R.id.messageButton);

        imageProfile = findViewById(R.id.profilePicImg);

        userInfo();

        back = findViewById(R.id.exitButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(UserpageActivity.this, SearchActivity.class));
            }
        });

        connect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArrayList<String> friends = profile.getFriends();
                ArrayList<String> keys = profile.getMessage_keys();

                connected = false;

                if(!friends.contains(StaticData.MYPROFILE.getId()) && !profile.getId().equals(StaticData.MYPROFILE.getId()))
                {
                    friends.add(StaticData.MYPROFILE.getId());
                    keys.add(StaticData.MYPROFILE.getId()+"-"+profile.getId());
                    connected = true;
                }
                else if (friends.contains(StaticData.MYPROFILE.getId()))
                    friends.remove(StaticData.MYPROFILE.getId());

                if(!StaticData.MYPROFILE.getFriends().contains(profile.getId()))
                {
                    StaticData.MYPROFILE.getFriends().add(profile.getId());
                    StaticData.MYPROFILE.getMessage_keys().add(profile.getId()+"-"+profile.getId());
                    connected = true;
                }
                else if (StaticData.MYPROFILE.getFriends().contains(profile.getId()))
                    StaticData.MYPROFILE.getFriends().remove(profile.getId());


                Map<String, Object> temp = new HashMap<>();
                temp.put("friends", friends);
                Map<String, Object> temp2 = new HashMap<>();
                temp.put("friends", StaticData.MYPROFILE.getFriends());

                FirebaseDatabase.getInstance().getReference().child("Users").child(StaticData.MYPROFILE.getId()).updateChildren(temp).addOnSuccessListener(s -> {});

                FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).updateChildren(temp).addOnSuccessListener(s -> {
                    Toast.makeText(UserpageActivity.this, connected?"You are now connected to " + profile.getUsername():"You have disconnected from " + profile.getUsername(), Toast.LENGTH_SHORT).show();
                });


            }
        });
    }

    private void userInfo()
    {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    profile = dataSnapshot.getValue(Profile.class);
                    fillData(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadImageByUri(String url)
    {
        profileRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Glide.with(imageProfile).load(url).placeholder(R.drawable.profile).into(imageProfile);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

    private void fillData(Profile p)
    {
        username.setText(p.getUsername());
        bio.setText(p.getBio());
        fullName.setText(p.getFullName());
        connectCount.setText(p.getFriends().size()+"");
        postCount.setText(p.getMessages().size()+"");
        loadImageByUri(p.getImageurl());
        location.setText(p.getHometown());
        occupation.setText(p.getHometown());
    }



}
