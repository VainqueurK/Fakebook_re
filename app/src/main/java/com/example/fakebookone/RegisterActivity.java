package com.example.fakebookone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password, rpassword, username;
    Button register;
    TextView login;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        email = findViewById(R.id.emailreg);
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

                if(TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strPassword) || TextUtils.isEmpty(strEmail))
                {
                    Toast.makeText(RegisterActivity.this , "Please fill all fields before moving on", Toast.LENGTH_SHORT).show();
                }
                else if(strPassword.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this , "Your password must contain at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(strUsername, strPassword, strEmail);
                }
            }
        });


    }
    private void register(final String username, String password, String email){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", username);
                    hashMap.put("bio", "");
                    hashMap.put("imageurl", "gs://fakebookone-11dcd.appspot.com/profilepic.png");//james you need to modify this hashmap to includ extra information for later

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
        });


    }
}
