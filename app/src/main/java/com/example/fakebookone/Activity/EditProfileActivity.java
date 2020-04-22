package com.example.fakebookone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fakebookone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    Map<String,String> userValues = new HashMap<>();
    Button doneBtn;

    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    private TextView tvFullName, tvUsername, tvDob;
    private EditText editTxtBio, editTxtWork, editTxtEducation, editTxtHometown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // DB
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        // Text Fields
        tvFullName = findViewById(R.id.my_profile_name);
        tvUsername = findViewById(R.id.my_user_name);
        tvDob = findViewById(R.id.my_dob_edit);

        //Edit Text Fields
        editTxtBio = findViewById(R.id.my_bio_edit);
        editTxtWork = findViewById(R.id.my_work_edit);
        editTxtEducation = findViewById(R.id.my_education_edit);
        editTxtHometown = findViewById(R.id.my_hometown_edit);



        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String dBUsername =  dataSnapshot.child("username").getValue().toString();
                    String dBFullName =  dataSnapshot.child("fullName").getValue().toString();
                    String dBImageUrl =  dataSnapshot.child("imageurl").getValue().toString();
                    String dBDob = dataSnapshot.child("dateOfBirth").getValue().toString();

                    // Setting Text Views to DB Values
                    tvUsername.setText("@" + dBUsername);
                    tvFullName.setText(dBFullName);
                    tvDob.setText( dBDob);

                    String dBBio = dataSnapshot.child("bio").getValue().toString();
                    String dBWork = dataSnapshot.child("work").getValue().toString();
                    String dBEducation = dataSnapshot.child("education").getValue().toString();
                    String dBHometown = dataSnapshot.child("hometown").getValue().toString();

                    // Setting Edit Texts to DB Values
                    editTxtBio.setText(dBBio);
                    editTxtWork.setText(dBWork);
                    editTxtEducation.setText(dBEducation);
                    editTxtHometown.setText(dBHometown);

                    // Adding Values to HashMap
                    userValues.put("username",dBUsername);
                    userValues.put("fullName",dBFullName);
                    userValues.put("imageurl",dBImageUrl);
                    userValues.put("dateOfBirth",dBDob);
                    userValues.put("bio",dBBio);
                    userValues.put("work",dBWork);
                    userValues.put("education",dBEducation);
                    userValues.put("hometown",dBHometown);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 'Save Changes' button
        doneBtn = findViewById(R.id.done_button);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HashMap<String, Object> hashMap = new HashMap<>();

                // These values always remain unchanged, (adding them back again)
                hashMap.put("id", currentUserId);
                hashMap.put("username", userValues.get("username") );
                hashMap.put("fullName", userValues.get("fullName") );
                hashMap.put("imageurl",userValues.get("imageurl"));
                hashMap.put("dateOfBirth", userValues.get("dateOfBirth") );

                // Those values which user can edit
                hashMap.put("bio", editTxtBio.getText().toString());
                hashMap.put("work", editTxtWork.getText().toString());
                hashMap.put("education", editTxtEducation.getText().toString());
                hashMap.put("hometown", editTxtHometown.getText().toString());



                profileRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }

        });


    }





}
