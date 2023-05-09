package com.example.check1;

public class Building {
    private String buildingID;
    private String streetID;
    private String streetName;
    private String buildingName;
    private float distance;
    private int availability;


    public Building(String buildingID, String streetID, String streetName, String buildingName, float distance, int availability){
        // Constructor
        this.buildingID = buildingID;
        this.streetID = streetID;
        this.streetName = streetName;
        this.buildingName = buildingName;
        this.distance = distance;
        this.availability = availability;
    }
    public String getBuildingID(){
        return this.buildingID;
    }
    public String getStreetID(){
        return streetID;
    }
    public String getStreetName(){
        return streetName;
    }
    public String getBuildingName(){
        return buildingName;
    }
    public float getDistance(){
        return distance;
    }
    public int getAvailability(){
        return availability;
    }
}
