package com.cureApp.app.Matches;

import com.cureApp.app.User.UserObject;

import java.util.ArrayList;

/**
 * Created by manel on 10/31/2017.
 */

public class MatchesObject {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String need;
    private String give;
    private String budget;

    private String lastMessage;
    private String lastTimeStamp;
    private String lastSeen;

    private String chatId;
    private ArrayList<UserObject> userObjectArrayList = new ArrayList<>();
    public MatchesObject (String userId, String name, String profileImageUrl, String need, String give, String budget, String lastMessage, String lastTimeStamp, String chatId, String lastSeen){

        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.need = need;
        this.budget = budget;
        this.give = give;
        this.lastMessage = lastMessage;
        this.lastTimeStamp = lastTimeStamp;
        this.chatId = chatId;
        this.lastSeen = lastSeen;
    }

    public ArrayList<UserObject> getUserObjectArrayList() {
        return userObjectArrayList;
    }




    public void addUserToArrayList(UserObject mUser){
        userObjectArrayList.add(mUser);
    }

    public String getLastSeen(){
        return lastSeen;
    }
    public void setLastSeen(String lastSeen){
        this.lastSeen = lastSeen;
    }


    public String getUserId(){
        return userId;
    }
    public String getChatId(){
        return chatId;
    }
    public void setChatId(String chatId){ this.chatId = chatId;}
    public String getNeed(){
        return need;
    }
    public String getGive(){
        return give;
    }
    public String getBudget(){
        return budget;
    }
    public String getLastMessage() {return this.lastMessage;}
    public String getLastTimestamp() {return this.lastTimeStamp;}


    public void setUserID(String userID){
        this.userId = userId;
    }
    public void setNeed(String need){
        this.need = need;
    }

    public void setGive(String give){
        this.give = give;
    }
    public void setBudget(String budget){
        this.budget = budget;
    }
    public void setLastMessage(String lastMessage) {this.lastMessage = lastMessage;}
    public void setLastTimeStamp(String lastTimeStamp) {this.lastTimeStamp = lastTimeStamp;}


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getProfileImageUrl(){
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}
