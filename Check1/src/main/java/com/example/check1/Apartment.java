package com.example.check1;

public class Apartment {
    private String apartmentID;
    private String buildingID;
    private String apartmentNo;
    private int room1;
    private int room2;
    private int room3;
    private int availability;
    private String buildingName;
    private String floor;

    public Apartment(String apartmentID, String buildingID, String apartmentNo,String floor, int room1, int room2, int room3, int availability){
        // Constructor
        this.apartmentID = apartmentID;
        this.buildingID = buildingID;
        this.apartmentNo = apartmentNo;
        this.room1 = room1;
        this.room2 = room2;
        this.room3 = room3;
        this.availability = availability;
        this.floor = floor;
    }
    public Apartment(String apartmentID, String buildingName, String apartmentNo,String floor, int availability){
        // Constructor
        this.apartmentID = apartmentID;
        this.buildingName = buildingName;
        this.apartmentNo = apartmentNo;
        this.availability = availability;
        this.floor = floor;
    }

    public String getApartmentID(){
        return this.apartmentID;
    }
    public String getBuildingName(){
        return this.buildingName;
    }

    public String getBuildingID(){
        return this.buildingID;
    }

    public String getApartmentNo(){
        return this.apartmentNo;
    }

    public String getFloor(){
        return this.floor;
    }

    public int getRoom1(){
        return this.room1;
    }

    public int getRoom2(){
        return this.room2;
    }

    public int getRoom3(){
        return this.room3;
    }

    public int getAvailability(){
        return this.availability;
    }
}
