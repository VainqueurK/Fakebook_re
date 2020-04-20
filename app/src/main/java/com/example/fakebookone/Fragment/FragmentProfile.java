package com.example.fakebookone.Fragment;

import android.app.Activity;
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

import com.example.fakebookone.Activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.fakebookone.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;


    private TextView userProfileName, username, userInfo, userDob, userWork, userEducation, userHometown;
    private ImageView userProfileImage;
    private Button logoutBtn, editBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        //                                                                               specific unique id of user
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        logoutBtn = view.findViewById(R.id.logout_button);
        editBtn = view.findViewById(R.id.edit_button);

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



    }

    public void logout(View view)
    {
        mAuth.signOut();
        Intent sendToLogin = new Intent(view.getContext(), MainActivity.class);
        sendToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sendToLogin);




    }


/*
    private void editProfileDetails(final String username, String password, String email){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", username);
                    hashMap.put("bio", "");
                    hashMap.put("imageurl", "gs://fakebookone-11dcd.appspot.com/profilepic.png");//james you need to modify this hashmap to includ extra information for later

                    profileRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });


                }
                else {
                    Toast.makeText(RegisterActivity.this , "You can't register with those credentials", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
 */

public void sendMessage(View view){



}


}
