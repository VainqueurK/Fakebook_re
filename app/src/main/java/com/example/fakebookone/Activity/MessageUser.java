package com.example.fakebookone.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebookone.Adapter.ChatRoomAdapter;
import com.example.fakebookone.Misc.Model.ChatRoom;
import com.example.fakebookone.Misc.Model.Message;
import com.example.fakebookone.Misc.StaticData;
import com.example.fakebookone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;


public class MessageUser extends AppCompatActivity {

    View view;
    private ImageButton sendMessageButton, SendImageFileButton;
    private EditText messageField;
    private ChatRoom chatRoom;
    private RecyclerView messageView;
    private ChatRoomAdapter arAdapter;

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_user);
        messageField = findViewById(R.id.input_message);
        sendMessageButton =findViewById(R.id.send_message_button);
        messageView=findViewById(R.id.messages_list_users);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        // linearLayoutManager.setStackFromEnd(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        messageView.setLayoutManager(linearLayoutManager);


        chatRoom=(ChatRoom) getIntent().getSerializableExtra("chatRoom");
        arAdapter=new ChatRoomAdapter(chatRoom.getMessages(),MessageUser.this,chatRoom.getUserOne(),chatRoom.getUserTwo());
        messageView.setAdapter(arAdapter);
        setupMessageView();
        sendMessageButton.setOnClickListener(e->{
            sendMessage();
        });


    }

    private void sendMessage() {
        long timeStamp=new Date().getTime();
        Message message= new Message(messageField.getText().toString(),timeStamp, StaticData.MYPROFILE.getId(),0);
        System.out.println(message);
        FirebaseFirestore.getInstance().collection("Messages").document(chatRoom.getKey()).collection(chatRoom.getKey()).document(timeStamp+"").set(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                messageField.getText().clear();
            }
        }).addOnSuccessListener(s->{
            //success
            //TODO:UpdateUI
            arAdapter.notifyDataSetChanged();
        });
    }
    private void setupMessageView(){
        System.out.println("setupp view starting");
        FirebaseFirestore.getInstance().collection("Message").document(chatRoom.getKey()).collection(chatRoom.getKey()).orderBy("timeSent").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots!=null)
                {
                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                        chatRoom.getMessages().add(ds.toObject(Message.class));
                    }
                    // arAdapter.notifyDataSetChanged();
                }

            }
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
