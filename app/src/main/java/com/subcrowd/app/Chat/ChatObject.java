package com.cureApp.app.Chat;

/**
 * Created by manel on 10/31/2017.
 */

public class ChatObject {
    private String message;
    private Boolean currentUser;
    private Boolean isSeen;

    public ChatObject(String message, Boolean currentUser, Boolean isSeen){
        this.message = message;
        this.currentUser = currentUser;
        this.isSeen = isSeen;
    }

    public Boolean getisSeen(){ return isSeen;}

    public void setIsSeen(Boolean seen){
        this.isSeen = seen;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String userID){
        this.message = message;
    }

    public Boolean getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(Boolean currentUser){
        this.currentUser = currentUser;
    }
}
