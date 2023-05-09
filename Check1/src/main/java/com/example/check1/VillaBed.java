package com.example.check1;

public class VillaBed {
    private String bedID;
    private String roomID;
    private String villaID;
    private String userID;
    private String availability;

    public VillaBed(String bedID,String roomID,String villaID,String userID,String availability){
        this.bedID=bedID;
        this.roomID=roomID;
        this.villaID=villaID;
        this.userID=userID;
        this.availability=availability;
    }

    public String getBedID(){
        return bedID;
    }
    public String getRoomID(){
        return roomID;
    }
    public String getVillaID(){
        return villaID;
    }
    public String getUserID(){
        return userID;
    }
    public String getAvailability(){
        return availability;
    }
}
