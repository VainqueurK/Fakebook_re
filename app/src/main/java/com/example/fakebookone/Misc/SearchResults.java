package com.example.fakebookone.Misc;

import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class SearchResults  {
    public String username;
    public String bio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public SearchResults(){}
    public SearchResults(String username, String bio, String uid) {
        this.username = username;
        this.bio = bio;
        this.id = uid;
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
