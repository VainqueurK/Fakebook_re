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

    View view;
    private EditText searchField;
    private ImageButton searchBtn;
    private RecyclerView resultList;



    public FragmentChat()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);
        if((EditText) view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.search_field);
            searchBtn = view.findViewById(R.id.search_image);
            resultList = view.findViewById(R.id.chat_list);
        }
        //InitializeFields();
        return view;
    }

    private void InitializeFields() {

        if((EditText) view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.search_field);
            searchBtn = view.findViewById(R.id.search_image);
            resultList = view.findViewById(R.id.chat_list);
        }

    }


}