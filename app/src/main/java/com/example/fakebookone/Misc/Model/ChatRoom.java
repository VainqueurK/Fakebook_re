package com.example.fakebookone.Misc.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatRoom implements Serializable {
    private String key="";
    private Profile userOne;
    private Profile userTwo;
    private long timestamp;
    private Message lastMessage;
    private ArrayList<Message> messages=new ArrayList<>();

    public ChatRoom(String key, Profile userOne, Profile userTwo, long timestamp, Message lastMessage) {
        this.key = key;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.timestamp = timestamp;
        this.lastMessage = lastMessage;
    }

    public ChatRoom(String key, Profile userOne, Profile userTwo) {
        this.key = key;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Profile getUserOne() {
        return userOne;
    }

    public void setUserOne(Profile userOne) {
        this.userOne = userOne;
    }

    public Profile getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(Profile userTwo) {
        this.userTwo = userTwo;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
