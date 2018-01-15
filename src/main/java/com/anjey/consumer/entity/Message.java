package com.anjey.consumer.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Message.COLLECTION_NAME)
public class Message {
    public static final String COLLECTION_NAME = "message";

    @Id
    private String id;

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
