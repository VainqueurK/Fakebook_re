package com.example.fakebookone.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Adapter.ChatSearchAdapter;
import com.example.fakebookone.Misc.ChatSearchResults;
import com.example.fakebookone.Misc.Model.ChatRoom;
import com.example.fakebookone.Misc.Model.Message;
import com.example.fakebookone.Misc.Model.Profile;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
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

public class FragmentChat extends Fragment {
    static Context context;
    private View view;
    private EditText searchField;
    private RecyclerView resultList;
    private DatabaseReference mfakebookDataBase; //referencing the database
   //private ArrayList<ChatSearchResults> list;
    private ArrayList<Profile> friends;
    private RecyclerView userList;
    private ArrayList<ChatSearchResults> users;
    private FirebaseRecyclerAdapter ChatSearchAdapter;
    private ArrayList<ChatRoom> chatRooms=new ArrayList<>();

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
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&StaticData.MYPROFILE==null)
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            StaticData.MYPROFILE=dataSnapshot.getValue(Profile.class);
                            loadPrevConversation();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }
            );
        else
        loadPrevConversation();

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

                        System.out.println(list);


                        resultList.setAdapter((RecyclerView.Adapter) adapterClass);

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
        }*/
    }

    public void loadPrevConversation(){
        for(String key : StaticData.MYPROFILE.getMessage_keys()){
            String[]users=key.split("-");
            String otherUid=users[0].contains(StaticData.MYPROFILE.getId())?users[1]:users[0];
            Profile peer=null;
            FirebaseDatabase.getInstance().getReference().child("Users").child(otherUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    loadMessage(dataSnapshot.getValue(Profile.class),key);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    private void loadMessage(Profile peer, String key) {

        ChatRoom room=new ChatRoom(key, StaticData.MYPROFILE,peer);
        FirebaseFirestore.getInstance().collection("Messages").document(key).collection(key).orderBy("timestamp").limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                Message message=null;
                assert queryDocumentSnapshots != null;
                for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                   message=ds.toObject(Message.class);
               }
                if(message!=null){
                    room.setTimestamp(message.getTimeSent());
                    room.setLastMessage(message);
                    chatRooms.add(room);
                    //TODO: update ui
                    com.example.fakebookone.Adapter.ChatSearchAdapter adapterClass = new ChatSearchAdapter(chatRooms, context);


                    resultList.setAdapter((RecyclerView.Adapter) adapterClass);

                }
            }

        });
        FirebaseFirestore.getInstance().collection("Messages").document(key).collection(key).orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                ArrayList<Message> messages=new ArrayList<>();
                assert queryDocumentSnapshots != null;
                for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    messages.add(ds.toObject(Message.class));
                }
                room.setMessages(messages);
                //TODO: go to chat page

            }
        });
    }
    public void search(String str) {
        ArrayList<ChatRoom> myList = new ArrayList<ChatRoom>();
        for(ChatRoom object : chatRooms){
            if(object.getUserTwo().getUsername() != null) {
                if (object.getUserTwo().getUsername().toLowerCase().contains(str.toLowerCase())) {
                    myList.add(object);
                }
            }
        }
        ChatSearchAdapter adapterClass = new ChatSearchAdapter(myList, context);
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