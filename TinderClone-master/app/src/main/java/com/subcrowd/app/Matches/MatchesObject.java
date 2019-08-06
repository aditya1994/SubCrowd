package com.subcrowd.app.Matches;

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



    public MatchesObject (String userId, String name, String profileImageUrl, String need, String give, String budget, String lastMessage, String lastTimeStamp){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.need = need;
        this.budget = budget;
        this.give = give;
        this.lastMessage = lastMessage;
        this.lastTimeStamp = lastTimeStamp;
    }

    public String getUserId(){
        return userId;
    }
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
