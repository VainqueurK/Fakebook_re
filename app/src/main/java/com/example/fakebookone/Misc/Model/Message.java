package com.example.fakebookone.Misc.Model;

public class Message {
    private String content;
    private long timestamp;
    private String sender;
    private int type=0;

    public Message(String content, long timestamp, String sender, int type) {
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
