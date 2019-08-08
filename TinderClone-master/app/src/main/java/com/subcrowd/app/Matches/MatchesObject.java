package com.subcrowd.app.Matches;

import com.subcrowd.app.User.UserObject;

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
    private String chatId;
    private ArrayList<UserObject> userObjectArrayList = new ArrayList<>();
    public MatchesObject (String userId, String name, String profileImageUrl, String need, String give, String budget, String chatId){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.need = need;
        this.budget = budget;
        this.give = give;
        this.chatId = chatId;
    }

    public ArrayList<UserObject> getUserObjectArrayList() {
        return userObjectArrayList;
    }




    public void addUserToArrayList(UserObject mUser){
        userObjectArrayList.add(mUser);
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
