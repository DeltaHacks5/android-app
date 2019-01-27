package me.susieson.sportscanner;

import java.util.Date;

class Message {

    String message;
    User sender;
    Date createdAt;

    public Message(String message, User sender, Date createdAt) {
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }
}
