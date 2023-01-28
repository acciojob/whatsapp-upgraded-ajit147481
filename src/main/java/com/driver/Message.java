package com.driver;

import jdk.jfr.Timestamp;

import java.util.Date;


public class Message {
    private int id;
    private String content;
    @Timestamp
    private Date timestamp;
    public Message(){

    }
    public Message(int id,String content){
        this.id=id;
        this.content=content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
