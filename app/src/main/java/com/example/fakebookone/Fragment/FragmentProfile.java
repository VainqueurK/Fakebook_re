package com.example.fakebookone.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fakebookone.Activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.fakebookone.R;



public class FragmentProfile extends Fragment {

    View view;
    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;


    private TextView userProfileName, username, userInfo, userDob, userWork, userEducation, userHometown;
    private ImageView userProfileImage;
    private Button logoutBtn, editBtn;


    public FragmentProfile() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        //                                                                               specific unique id of user
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        // Buttons
        logoutBtn = view.findViewById(R.id.logout_button);
        editBtn = view.findViewById(R.id.edit_button);

        // Text Fields
        userProfileName = view.findViewById(R.id.my_profile_name);
        username = view.findViewById(R.id.my_user_name);
        userInfo = view.findViewById(R.id.my_info);
        userDob = view.findViewById(R.id.my_dob);
        userWork = view.findViewById(R.id.my_work);
        userEducation = view.findViewById(R.id.my_education);
        userHometown = view.findViewById(R.id.my_education);


        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String usernameDB =  dataSnapshot.child("username").getValue().toString();
                    String fullNameDB =  dataSnapshot.child("fullName").getValue().toString();
                    String dobDB = dataSnapshot.child("dateOfBirth").getValue().toString();

                    username.setText("@" + usernameDB);
                    userProfileName.setText(fullNameDB);
                    userDob.setText("Date of Birth:\t" + dobDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // On Logout
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                logout(v);
            }
        });

        // On click Edit Profile
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
              // Navigation.findNavController(v).navigate(R.id.action_fragmentProfile_to_fragmentEditProfile);

            }
        });

    }

    public void logout(View view)
    {
        mAuth.signOut();
        Intent sendToLogin = new Intent(view.getContext(), MainActivity.class);
        sendToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sendToLogin);



    }


}
