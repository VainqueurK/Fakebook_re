package com.example.fakebookone.Misc.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    private String username = "";
    private String bio = "";
    private String fullName = "";
    private String dateOfBirth = "";
    private String hometown = "";
    private String id = "";
    private String education = "";
    private String imageurl = "";
    private String work = "";
    private ArrayList<String> friends=new ArrayList<String>();//UID of users
    private ArrayList<String> messages=new ArrayList<String>();//ID of messages
    private ArrayList<String> message_keys=new ArrayList<String>();//ID of messages

    public Profile(String username, String bio, String fullName, String dateOfBirth, String hometown, String id, String education, String imageurl, String work, ArrayList<String> friends, ArrayList<String> messages, ArrayList<String> message_keys) {
        this.username = username;
        this.bio = bio;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.hometown = hometown;
        this.id = id;
        this.education = education;
        this.imageurl = imageurl;
        this.work = work;
        this.friends = friends;
        this.messages = messages;
        this.message_keys = message_keys;
    }



    public Profile(String username, String bio, String fullName, String dateOfBirth, String hometown, String id, String education, String imageurl, String work, ArrayList<String> friends, ArrayList<String> messages) {
        this.username = username;
        this.bio = bio;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.hometown = hometown;
        this.id = id;
        this.education = education;
        this.imageurl = imageurl;
        this.work = work;
        this.friends = friends;
        this.messages = messages;
    }

    public Profile(String username, String bio, String fullName, String dateOfBirth, String hometown, String id, String education, String imageurl, String work) {
        this.username = username;
        this.bio = bio;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.hometown = hometown;
        this.id =id;
        this.education = education;
        this.imageurl = imageurl;
        this.work = work;
    }

    public Profile(String username, String id, String imageurl) {
        this.username = username;
        this.id =id;
        this.imageurl = imageurl;
    }

    public Profile(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public Profile(String id) {
        this.id = id;
    }

    public Profile() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public ArrayList<String> getMessage_keys() {
        return message_keys;
    }

    public void setMessage_keys(ArrayList<String> message_keys) {
        this.message_keys = message_keys;
    }
    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", hometown='" + hometown + '\'' +
                ", id='" + id + '\'' +
                ", education='" + education + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", work='" + work + '\'' +
                '}';
    }
}
