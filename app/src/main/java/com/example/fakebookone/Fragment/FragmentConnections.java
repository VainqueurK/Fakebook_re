package com.example.fakebookone.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Adapter.ChatSearchAdapter;
import com.example.fakebookone.Adapter.ConnectionsAdapter;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.Misc.Model.ChatRoom;
import com.example.fakebookone.Misc.Model.Message;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentConnections extends Fragment {
    static Context context;
    private View view;
    private EditText searchField;
    private RecyclerView resultList;
    private DatabaseReference mfakebookDataBase; //referencing the database
   //private ArrayList<ChatSearchResults> list;
    private ArrayList<Profile> friends=new ArrayList<>();
    private RecyclerView userList;
    private ArrayList<ChatSearchResults> users;
    private FirebaseRecyclerAdapter ChatSearchAdapter;
    private ArrayList<ChatRoom> chatRooms=new ArrayList<>();

    public FragmentConnections() {

    }
    public void onCreate() {
    }

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.connect_lists, container, false);


        return view;
    }

    ConnectionsAdapter connAdapter;
    public void onStart() {
        super.onStart();
        InitializeFields();

        connAdapter = new ConnectionsAdapter(friends, context);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        resultList.setLayoutManager(llm);

        resultList.setAdapter((RecyclerView.Adapter) connAdapter);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&StaticData.MYPROFILE==null)
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        StaticData.MYPROFILE=dataSnapshot.getValue(Profile.class);
                        loadProfiles();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        else loadProfiles();

       /*if(mfakebookDataBase != null){
            mfakebookDataBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        list = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(ChatSearchResults.class));
                        }
                        com.example.fakebookone.Adapter.ChatSearchAdapter adapterClass = new ChatSearchAdapter(list, context);


                        resultList.setAdapter((RecyclerView.Adapter) adapterClass);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }*/


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

    public void loadProfiles(){
        friends.clear();
        for(String uid : StaticData.MYPROFILE.getFriends()){
            System.out.println(uid);
            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 friends.add(dataSnapshot.getValue(Profile.class));
                    //connAdapter = new ConnectionsAdapter(friends, context);
                    //resultList.setAdapter((RecyclerView.Adapter) connAdapter);
                    connAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    public void search(String str) {
        ArrayList<Profile> myList = new ArrayList<>();
        for(Profile f : friends){
            if(f.getUsername() != null) {
                if (f.getUsername().toLowerCase().contains(str.toLowerCase())) {
                    myList.add(f);
                }
            }
        }
        connAdapter.notifyDataSetChanged();
    }

// HELPER FUNCTIONS

    private void InitializeFields() {

        //initialize search view
        if (view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.connection_search);
        }
        //initialize recyclerView
        if (view.findViewById(R.id.connections_list) != null) {
            resultList = view.findViewById(R.id.connections_list);
        }
        //initialize database reference
        mfakebookDataBase = FirebaseDatabase.getInstance().getReference("Users");


    }


    //Dialog box displayed on click

    public void displayDialogBox(){
        // setup the alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose an option");
        //set up a list
        String[] options = {"Chat", "View user profile", "Follow user",};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Chat
                    case 1: // View user profile
                    case 2: // Follow user
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public class dialogBox implements View.OnClickListener{
        public dialogBox(){
            displayDialogBox();
        };
        @Override
        public void onClick(View v) {
            displayDialogBox();
        }
    }
}