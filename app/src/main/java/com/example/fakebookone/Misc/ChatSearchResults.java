package com.example.fakebookone.Misc;

import android.net.Uri;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

public class ChatSearchResults  {
    public String username, bio;
    public String imageurl;
    public Button chat;
    public ChatSearchResults(){}
    public ChatSearchResults(String username, String bio, Button chat, String imageurl) {
        this.username = username;
        this.bio = bio;
        this.chat = chat;
        this.imageurl = imageurl;
        System.out.println("URI = " + imageurl);
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
    public String getImageurl(){
        return imageurl;
    }


}
