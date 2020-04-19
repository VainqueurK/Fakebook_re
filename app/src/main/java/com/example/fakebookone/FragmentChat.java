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

public class FragmentChat extends Fragment {

    private View view;
    private EditText searchField;
    private ImageButton searchBtn;
    private RecyclerView resultList;



    public FragmentChat() {

    }



    public void onCreate(){};
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);
        InitializeFields();
        return view;
    }

    private void InitializeFields() {
        if(view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.search_field);
        }

        if(view.findViewById(R.id.search_image) != null) {
            searchBtn = view.findViewById(R.id.search_image);
        }
        if(view.findViewById(R.id.chat_list) != null) {
            resultList =  view.findViewById(R.id.chat_list);
        }


    }


}