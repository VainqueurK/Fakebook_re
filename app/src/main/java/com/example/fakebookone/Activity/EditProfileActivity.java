package com.example.fakebookone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    Map<String,String> userValues = new HashMap<>();
    
    Button doneBtn, editImgBtn;

    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    //Storage
    private StorageReference profileImgReference;

    private CircleImageView profileImage;

    final static int Gallery_Pick = 1;

    private TextView tvFullName, tvUsername, tvDob;
    private EditText editTxtBio, editTxtWork, editTxtEducation, editTxtHometown;

    private static final int MY_PERMISSIONS_REQUEST = 100;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ProgressBar progressBar;

    private Uri imgUri;
    private String url;

    private Context context = EditProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // DB
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        profileImgReference = FirebaseStorage.getInstance().getReference().child("profileImages");

        // Text Fields
        tvFullName = findViewById(R.id.my_profile_name);
        tvUsername = findViewById(R.id.my_user_name);
        tvDob = findViewById(R.id.my_dob_edit);

        // Profile Image
        profileImage = findViewById(R.id.postImage);

        //Edit Text Fields
        editTxtBio = findViewById(R.id.my_bio_edit);
        editTxtWork = findViewById(R.id.my_work_edit);
        editTxtEducation = findViewById(R.id.my_education_edit);
        editTxtHometown = findViewById(R.id.my_hometown_edit);

        //Button

        requestStoragePermission();

        loadImageByUri();


        System.out.println("retrieving data----");
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    String dBUsername =  dataSnapshot.child("username").getValue().toString();
                      String dBFullName =  dataSnapshot.child("fullName").getValue().toString();
                      String dBImageUrl =  dataSnapshot.child("imageurl").getValue().toString();

                      System.out.println(dataSnapshot.child("dateOfBirth").getValue());

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
                   /* userValues.put("username",dBUsername);
                    userValues.put("fullName",dBFullName);
                    userValues.put("imageurl",dBImageUrl);
                    userValues.put("dateOfBirth",dBDob);
                    userValues.put("bio",dBBio);
                    userValues.put("work",dBWork);
                    userValues.put("education",dBEducation);
                    userValues.put("hometown",dBHometown);
                    
                    */
                    ArrayList<String>friends=new ArrayList<>();
                    ArrayList<String>messages=new ArrayList<>();

                   if(dataSnapshot.child("friends").getValue()!=null){
                       if(dataSnapshot.child("friends").getValue() instanceof ArrayList){
                           friends= (ArrayList<String>) dataSnapshot.child("friends").getValue();
                       }
                   }
                    if(dataSnapshot.child("messages").getValue()!=null){
                        if(dataSnapshot.child("messages").getValue() instanceof ArrayList){
                            messages= (ArrayList<String>) dataSnapshot.child("messages").getValue();
                        }
                    }

                    Profile editedProfile=new Profile(dBUsername,dBBio,dBFullName,dBDob,dBHometown,currentUserId,dBEducation,dBImageUrl,dBWork,friends,messages);
                    System.out.println(editedProfile);
                    StaticData.MYPROFILE=editedProfile;
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
                Profile editedProfile=new Profile(
                        !tvUsername.getText().toString().isEmpty()?tvUsername.getText().toString():StaticData.MYPROFILE.getUsername() ,
                        !editTxtBio.getText().toString().isEmpty()? editTxtBio.getText().toString():StaticData.MYPROFILE.getBio(),
                        !tvFullName.getText().toString().isEmpty()?tvFullName.getText().toString():StaticData.MYPROFILE.getFullName(),
                        !tvDob.getText().toString().isEmpty()?tvDob.getText().toString():StaticData.MYPROFILE.getDateOfBirth(),
                        !editTxtHometown.getText().toString().isEmpty()?editTxtHometown.getText().toString():StaticData.MYPROFILE.getHometown(),
                        currentUserId,
                        !editTxtEducation.getText().toString().isEmpty()?editTxtEducation.getText().toString():StaticData.MYPROFILE.getEducation(),
                        !url.isEmpty()?url:StaticData.MYPROFILE.getFullName(),
                        !editTxtWork.getText().toString().isEmpty()?editTxtWork.getText().toString():StaticData.MYPROFILE.getWork(),
                        StaticData.MYPROFILE!=null?StaticData.MYPROFILE.getFriends():new ArrayList<>(),
                        StaticData.MYPROFILE!=null?StaticData.MYPROFILE.getMessages():new ArrayList<>(),
                        StaticData.MYPROFILE!=null?StaticData.MYPROFILE.getMessage_keys():new ArrayList<>()
                        );

               /*
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
                */


                profileRef.setValue(editedProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                            StaticData.MYPROFILE=editedProfile;
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }

        });
        editImgBtn = findViewById(R.id.edit_image_button);

       editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Pick );
            }
        });

       // loadImageByInternetUrl();



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            /*
            Glide
                    .with(context)
                    .load(imageUri)
                    .into(profileImage);
*/


            StorageReference filePath = profileImgReference.child(currentUserId + ".jpg");

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                    if(downloadUri.isSuccessful()) {
                        url = downloadUri.getResult().toString();

                    }
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(EditProfileActivity.this, "Error occured " + e.getMessage(), Toast.LENGTH_SHORT).show();

            });
        }

    }

    private void requestStoragePermission() {
        if(ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfileActivity.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void loadImageByUri(){
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){


                    String dBImageUrl =  dataSnapshot.child("imageurl").getValue().toString();
                    System.out.println( "this is the url : " + dBImageUrl);
                    Glide
                            .with(context)
                            .load(dBImageUrl)
                            .into(profileImage);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
