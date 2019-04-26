package com.runupstdio.lumbungdesa.Model;

public class Chat {

    private String DestinationAva;
    private String Message;
    private String Sender;
    private String MyID;

    public Chat(String destinationAva, String msg, String sender, String myID) {
        this.DestinationAva = destinationAva;
        this.Message = msg;
        this.Sender = sender;
        this.MyID = myID;
    }

    public String getDestinationAva() {
        return DestinationAva;
    }

    public void setDestinationAva(String destinationAva) {
        DestinationAva = destinationAva;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getMyID() {
        return MyID;
    }

    public void setMyID(String myID) {
        MyID = myID;
    }
}
