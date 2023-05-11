package com.example.check1;

import java.sql.*;

public class Villa {
    private String villaID;
    private String streetID;
    private String villaNo;
    private int room1;
    private int room2;
    private int room3;
    private float distance;
    private int availability;
    private String streetName;

    public Villa(String villaID,String streetID,String streetName,String villaNo,int room1, int room2, int room3, float distance, int availability){
        this.villaID=villaID;
        this.streetID=streetID;
        this.streetName=streetName;
        this.villaNo=villaNo;
        this.room1=room1;
        this.room2=room2;
        this.room3=room3;
        this.distance=distance;
        this.availability=availability;
    }
    public Villa(String streetName, String villaNo , float distance){
        this.streetName=streetName;
        this.villaNo=villaNo;
        this.distance=distance;
    }

    public String getVillaID(){
        return villaID;
    }
    public String getStreetID(){
        return streetID;
    }
    public String getVillaNo(){
        return villaNo;
    }
    public int getRoom1(){
        return room1;
    }
    public int getRoom2(){
        return room2;
    }
    public int getRoom3(){
        return room3;
    }
    public String getStreetName(){
        return streetName;
    }
    public float getDistance(){return distance;}
    public int getAvailability(){
        return availability;
    }

}
