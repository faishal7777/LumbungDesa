package com.runupstdio.lumbungdesa.Model;

public class User {


    private int conversationId;
    private String username;
    private String imageUrl;
    private String destination_id;

    public User (int conversationId, String username, String imageUrl, String destinationId){
        this.conversationId = conversationId;
        this.username = username;
        this.imageUrl = imageUrl;
        this.destination_id = destinationId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String  getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destinationId) {
        this.destination_id = destinationId;
    }
}
