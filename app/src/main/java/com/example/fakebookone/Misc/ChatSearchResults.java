package com.example.fakebookone.Misc;

import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class ChatSearchResults  {
    public String username, bio;
    public Button chat;
    public ChatSearchResults(){}
    public ChatSearchResults(String username, String bio, Button chat) {
        this.username = username;
        this.bio = bio;
        this.chat = chat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


}
