package com.example.fakebookone.Misc;

import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class SearchResults  {
    public String username, bio, id;

    public SearchResults()
    {

    }
    public SearchResults(String username, String bio, String uid) {
        this.username = username;
        this.bio = bio;
        this.id = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getUid() {
        return id;
    }

    public void setUid(String Uid) {
        this.id = Uid;
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
