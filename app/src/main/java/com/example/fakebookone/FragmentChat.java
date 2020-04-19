package com.example.fakebookone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FragmentChat extends Fragment {

    private View view;
    private SearchView searchField;
    private RecyclerView resultList;
    private DatabaseReference mfakebookDataBase; //referencing the database
    ArrayList<ChatSearchResults> list;
    private Adapter ChatSearchAdapter;

    public FragmentChat() {

    }
    public void onCreate() {
    }

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);

        InitializeFields();

        return view;
    }

    public void onStart() {
        super.onStart();
        if(mfakebookDataBase != null){
            mfakebookDataBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                            list = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(ChatSearchResults.class));
                        }
                        ChatSearchAdapter adapterClass = new ChatSearchAdapter(list);
                        resultList.setAdapter((RecyclerView.Adapter) ChatSearchAdapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(searchField != null){
            searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    public void search(String str) {
        ArrayList<ChatSearchResults> myList = new ArrayList<ChatSearchResults>();
        for(ChatSearchResults object : list){
            if(object.getBio().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        ChatSearchAdapter adapterClass = new ChatSearchAdapter(myList);
        resultList.setAdapter(adapterClass);
    }

// HELPER FUNCTIONS

    private void InitializeFields() {

        //initialize search view
        if (view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.search_field);
        }
        //initialize recyclerView
        if (view.findViewById(R.id.chat_list) != null) {
            resultList = view.findViewById(R.id.chat_list);
        }
        //initialize database reference
        mfakebookDataBase = FirebaseDatabase.getInstance().getReference("Users");


    }
}


