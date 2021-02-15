package com.example.homefit;

import java.util.ArrayList;

public class MemberInformation {
    private String name;
    private String id;
    private String photoUrl;
    private int level;
    private String weight;
    private ArrayList<String> friendList;

    public MemberInformation(String name, String id,String photoUrl,String weight)
    {
        this.name = name;
        this.id = id;
        this.photoUrl = photoUrl;
        this.level = 0;
        this.weight = weight;
        this.friendList = friendList;
    }

    public String getName()
    {
        return this.name;
    }
    public String getId()
    {
        return this.id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getPhotoUrl() { return this.photoUrl; }
    public void setPhotoUrl(String birthday)
    {
        this.photoUrl = photoUrl;
    }
    public int getLevel(){return this.level;}
    public void setLevel(int level){this.level = level;}
    public String getWeight(){return this.weight;}
    public void setWeight(String weight){this.weight = weight;}
    public ArrayList<String> getFriendList()
    {
        return this.friendList;
    }
    public void setFriendList(ArrayList<String> friendList)
    {
        this.friendList = friendList;
    }

}
