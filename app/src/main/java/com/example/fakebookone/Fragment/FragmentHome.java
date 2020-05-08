package com.example.fakebookone.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Activity.LoginActivity;
import com.example.fakebookone.Activity.MainActivity;
import com.example.fakebookone.Activity.PostActivity;
import com.example.fakebookone.Activity.RegisterActivity;
import com.example.fakebookone.Activity.SearchActivity;
import com.example.fakebookone.Adapter.ChatSearchAdapter;
import com.example.fakebookone.Adapter.PostAdapter;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.Misc.Model.Post;
import com.example.fakebookone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private View view;
    private EditText searchField;
    private RecyclerView resultList;
    private ImageButton SearchButton, postButton;
    private List<Post> postLists;
    private PostAdapter ;

    public FragmentHome() {

    }
    public void onCreate() {
    }

    public void readPosts()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for(DataSnapshot ds: dataSnapshot.child("Users").getChildren())
                {
                    Post post = dataSnapshot.getValue(Post.class);
                    for(String id : )
                    {
                        if(post.getPublisher().equals(id))
                        postLists.add(post);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })


    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home, container, false);

        SearchButton = view.findViewById(R.id.searchButton);
        postButton = view.findViewById(R.id.postButton);
        resultList = view.findViewById(R.id.postsList);
        resultList.setHasFixedSize(true);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        resultList.setLayoutManager(lm);

        postLists = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postLists);
        resultList.setAdapter(postAdapter);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostActivity.class));
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();

    }

}



