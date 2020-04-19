package com.example.fakebookone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class FragmentChat extends Fragment {

    private View view;
    private EditText searchField;
    private ImageButton searchBtn;
    private RecyclerView resultList;

    private DatabaseReference mfakebookDataBase; //referencing the database



    public FragmentChat() {

    }



    public void onCreate(){};
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);

        InitializeFields();

        return view;
    }



// HELPER FUNCTIONS
    private void fireBaseUserSearch() {

        final View mView;
        FirebaseRecyclerOptions<ChatSearchResults> options =
                new FirebaseRecyclerOptions.Builder<ChatSearchResults>()
                        .setQuery(mfakebookDataBase, ChatSearchResults.class)
                        .build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<ChatSearchResults, UsersViewHolder>(options) {

        @Override
        public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_layout, parent, false);

            return new UsersViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(UsersViewHolder holder, int position, ChatSearchResults model) {
            View mView = holder.mView;
            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_bio = (TextView) mView.findViewById(R.id.bio_text);
            ImageView user_pfp = (ImageView) mView.findViewById(R.id.profile_image);
        }
    };


    }

    private void InitializeFields() {

        //initialize search text
        if(view.findViewById(R.id.search_field) != null) {
            searchField = view.findViewById(R.id.search_field);
        }
        //initialize search button
        if(view.findViewById(R.id.search_image) != null) {
            searchBtn = view.findViewById(R.id.search_image);
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fireBaseUserSearch();
                }
            });
        }
        //initialize recyclerView
        if(view.findViewById(R.id.chat_list) != null) {
            resultList =  view.findViewById(R.id.chat_list);
        }
        //initialize database reference
        mfakebookDataBase = FirebaseDatabase.getInstance().getReference("Users");


    }


//Viewholder
    public class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }



    }
}