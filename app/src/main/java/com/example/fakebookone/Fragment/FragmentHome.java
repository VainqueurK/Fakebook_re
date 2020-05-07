package com.example.fakebookone.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.EditProfileActivity;
import com.example.fakebookone.Activity.SearchActivity;
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

    View view;
    private ImageButton searchBtn;
    private RecyclerView resultList;

    public FragmentHome() {

    }
    public void onCreate() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        searchBtn = view.findViewById(R.id.SearchButton);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent nextScreen = new Intent(getActivity(), SearchActivity.class);
                startActivity(nextScreen);
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();

    }
}



