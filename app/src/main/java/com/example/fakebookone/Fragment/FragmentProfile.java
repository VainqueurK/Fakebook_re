package com.example.fakebookone.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fakebookone.Activity.EditProfileActivity;
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


    private TextView txtFieldProfileName, txtFieldUsername, txtFieldBio, txtFieldDob, txtFieldWork, txtFieldEducation, txtFieldHometown;
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
        txtFieldProfileName = view.findViewById(R.id.my_profile_name);
        txtFieldUsername = view.findViewById(R.id.my_user_name);
        txtFieldBio = view.findViewById(R.id.my_bio_info);
        txtFieldDob = view.findViewById(R.id.my_dob_info);
        txtFieldWork = view.findViewById(R.id.my_work_info);
        txtFieldEducation = view.findViewById(R.id.my_education_info);
        txtFieldHometown = view.findViewById(R.id.my_hometown_info);


        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String dBUsername =  dataSnapshot.child("username").getValue().toString();
                    String dBFullName =  dataSnapshot.child("fullName").getValue().toString();
                    String dBImageUrl =  dataSnapshot.child("imageurl").getValue().toString();
                    String dBDob = dataSnapshot.child("dateOfBirth").getValue().toString();



                    String dBBio = dataSnapshot.child("bio").getValue().toString();
                    String dBWork = dataSnapshot.child("work").getValue().toString();
                    String dBEducation = dataSnapshot.child("education").getValue().toString();
                    String dBHometown = dataSnapshot.child("hometown").getValue().toString();

                    // Setting Text Views to DB Values
                    txtFieldUsername.setText("@" + dBUsername);
                    txtFieldProfileName.setText(dBFullName);
                    txtFieldDob.setText( dBDob);

                    txtFieldBio.setText(dBBio);
                    txtFieldWork.setText(dBWork);
                    txtFieldEducation.setText(dBEducation);
                    txtFieldHometown.setText(dBHometown);

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
              Intent nextScreen = new Intent(getActivity(), EditProfileActivity.class);
              startActivity(nextScreen);

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
