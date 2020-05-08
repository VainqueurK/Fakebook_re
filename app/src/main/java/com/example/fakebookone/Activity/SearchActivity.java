package com.example.fakebookone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fakebookone.Adapter.SearchAdapter;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.SearchResults;
import com.example.fakebookone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
{

    ArrayList<SearchResults> list;
    ArrayList<Profile> profiles = new ArrayList<>();
    private RecyclerView resultList;
    private ImageButton back;
    private EditText searchField;

    @Override
    protected void onStart()
    {
        super.onStart();
        if(searchField != null)
        {
            searchField.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    search(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchuser);

        resultList = findViewById(R.id.searchList);
        searchField = findViewById(R.id.searchBar);

        loadUsers();//get all user in data base
        back = findViewById(R.id.exitSearch);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });

        if(searchField != null)
        {
            searchField.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    search(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }

    }
    //async
    private void loadUsers()
    {

        profiles.clear();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    //get all users;
                    profiles.add(ds.getValue(Profile.class));
                    System.out.println(profiles.get(i));
                    i++;
                }

                SearchAdapter sa = new SearchAdapter(profiles, SearchActivity.this);
                resultList.setAdapter((RecyclerView.Adapter) sa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(String str)
    {
        ArrayList<Profile> myList = new ArrayList<Profile>();
        for(Profile p : profiles)
        {
            if(p.getUsername() != null)
            {
                if (p.getUsername().toLowerCase().contains(str.toLowerCase()))
                {
                    myList.add(p);
                }
            }
        }
        SearchAdapter adapterClass = new SearchAdapter(myList, SearchActivity.this);
        resultList.setAdapter(adapterClass);
    }

}
