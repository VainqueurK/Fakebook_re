package com.example.fakebookone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MessageUser extends Fragment {

    View view;
    private ImageButton SendMessageButton, SendImageFileButton;
    private EditText SendUserMessage;
    private RecyclerView UserMessagesList;



    public MessageUser()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_user, container, false);
        return view;
       // InitializeFields();
    }

    private void InitializeFields() {
        SendMessageButton = (ImageButton) getView().findViewById(R.id.send_message_button);
        SendImageFileButton = (ImageButton) getView().findViewById(R.id.send_image_file_button);
        SendUserMessage = (EditText) getView().findViewById(R.id.input_message);
    }


}
