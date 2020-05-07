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

import com.example.fakebookone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

public class UserpageActivity extends AppCompatActivity
{
    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    ImageView imageProfile;
    TextView posts, connects, username, bio;
    Button connect, message;
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
        profileId = intent.getStringExtra("uid");

        posts = findViewById(R.id.postCount);
        connects = findViewById(R.id.connectCount);
        username = findViewById(R.id.usernameText);
        bio = findViewById(R.id.bioText);

        connect = findViewById(R.id.connectButton);
        message = findViewById(R.id.messageButton);

        imageProfile = findViewById(R.id.profilePic);
        backButton = findViewById(R.id.backButton);

        currentUserId = mAuth.getCurrentUser().getUid();

        connect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(connect.equals("Connect"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileId).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else if(connect.equals("Connected"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileId).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileId)
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }


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


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
