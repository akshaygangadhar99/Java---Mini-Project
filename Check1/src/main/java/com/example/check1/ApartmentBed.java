package com.example.check1;

public class ApartmentBed {
    private String bedID;
    private String roomID;
    private String apartmentID;
    private String userID;
    private String availability;

    public ApartmentBed(String bedID, String roomID, String apartmentID, String userID, String availability){
        this.bedID=bedID;
        this.roomID=roomID;
        this.apartmentID=apartmentID;
        this.userID=userID;
        this.availability=availability;
    }

    public String getBedID(){
        return bedID;
    }
    public String getRoomID(){
        return roomID;
    }
    public String getApartmentID(){
        return apartmentID;
    }
    public String getUserID(){
        return userID;
    }
    public String getAvailability(){
        return availability;
    }
}
