package com.example.fakebookone;

import androidx.recyclerview.widget.RecyclerView;

public class ChatSearchResults  {
    public String username, bio;
    public ChatSearchResults(){}
    public ChatSearchResults(String username, String bio) {
        this.username = username;
        this.bio = bio;
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
