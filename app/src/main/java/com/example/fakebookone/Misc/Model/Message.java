package com.example.fakebookone.Misc.Model;

import java.io.Serializable;

public class Message implements Serializable {
    private String content;
    private long timeSent;
    private String sender;
    private int type=0;

    public Message(String content, long timeSent, String sender, int type) {
        this.content = content;
        this.timeSent = timeSent;
        this.sender = sender;
        this.type = type;
    }
    public Message(){

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeSent() {
        return timeSent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimeSent(long timeSent) {
        this.timeSent = timeSent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", timeSent=" + timeSent +
                ", sender='" + sender + '\'' +
                ", type=" + type +
                '}';
    }
}
