package com.example.fakebookone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fakebookone.R;

public class PostActivity extends AppCompatActivity
{
    ImageButton backButton, postButton;
    EditText postInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        backButton = findViewById(R.id.cancelPost);
        postButton = findViewById(R.id.postPost);
        postInput = findViewById(R.id.postInputBox);

        postInput.setText(null);

        String onYourMind = postInput.getText().toString();

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
            }
        });

        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println(postInput.getText());

                if (onYourMind != "") //somehow not working
                {

                }
            }
        });
    }
}
