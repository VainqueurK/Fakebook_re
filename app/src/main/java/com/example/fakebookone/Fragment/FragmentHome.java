package com.example.fakebookone.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Adapter.ChatSearchAdapter;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private View view;
    private EditText searchField;
    private RecyclerView resultList;
    private DatabaseReference mfakebookDataBase; //referencing the database
    ArrayList<ChatSearchResults> list;
    private FirebaseRecyclerAdapter ChatSearchAdapter;

    public FragmentHome() {

    }
    public void onCreate() {
    }

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

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
                        com.example.fakebookone.Adapter.ChatSearchAdapter adapterClass = new ChatSearchAdapter(list, getContext());
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
            searchField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    search(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void search(String str) {
        ArrayList<ChatSearchResults> myList = new ArrayList<ChatSearchResults>();
        for(ChatSearchResults object : list){
            if(object.getUsername() != null) {
                if (object.getUsername().toLowerCase().contains(str.toLowerCase())) {
                    myList.add(object);
                }
            }
        }
        ChatSearchAdapter adapterClass = new ChatSearchAdapter(myList, getContext());
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



