package com.example.fakebookone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fakebookone.Fragment.DatePickerFragment;
import com.example.fakebookone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText email, password, rpassword, username, fullName;
    Button register, datePickerBtn;
    TextView login;

    FirebaseAuth auth;
    DatabaseReference reference;
    String dateOfBirth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        email = findViewById(R.id.emailreg);
        fullName =findViewById(R.id.fullNamereg);
        username = findViewById(R.id.usernamereg);
        password = findViewById(R.id.passwordreg);
        rpassword = findViewById(R.id.rpassword);
        register = findViewById(R.id.register);
        login = findViewById(R.id.createAccount);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                String strEmail = email.getText().toString();
                String strFullName = fullName.getText().toString();

                if(TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strPassword) || TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strFullName))
                {
                    Toast.makeText(RegisterActivity.this , "Please fill all fields before moving on", Toast.LENGTH_SHORT).show();
                }
                else if(strPassword.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this , "Your password must contain at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(strUsername, strPassword, strEmail,strFullName);
                }
            }
        });

        datePickerBtn = findViewById(R.id.datePickerBtn);
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        dateOfBirth  = DateFormat.getDateInstance().format(c.getTime());


    }

    private void register(final String username, String password, String email, String fullName){
        System.out.println("yo it works");

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                System.out.println("completed");
                if(task.isSuccessful())
                {
                    System.out.println("task works");
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", username);
                    hashMap.put("imageurl", "gs://fakebookone-11dcd.appspot.com/profilepic.png");//james you need to modify this hashmap to includ extra information for later
                    hashMap.put("fullName", fullName);
                    hashMap.put("dateOfBirth", dateOfBirth);
                    // The user can add to these later in Edit Profile
                    hashMap.put("bio", "");
                    hashMap.put("work","");
                    hashMap.put("education","");
                    hashMap.put("hometown","");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        }).addOnFailureListener((e)->{

            Toast.makeText(RegisterActivity.this , e.getMessage(), Toast.LENGTH_SHORT).show();

        });



    }


}
