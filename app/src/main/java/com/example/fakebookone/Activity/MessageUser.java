package com.example.fakebookone.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.R;


public class MessageUser extends AppCompatActivity {

        View view;
        private ImageButton sendMessageButton, SendImageFileButton;
        private EditText messageField;

        public void onCreate(@Nullable Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.message_user);
          messageField = findViewById(R.id.input_message);
          sendMessageButton =findViewById(R.id.send_message_button);

          sendMessageButton.setOnClickListener(e->{
              
          });

        }
        private void getIncomingIntent(){
            if(getIntent().hasExtra("profile_list")){
                String list = getIntent().getStringExtra("profile_list");
            }
        }


   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/


    }
