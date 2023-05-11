package com.example.check1;

import java.sql.Time;
import java.time.LocalDate;

public class Booking {
    private String bookingID;
    private String userID;
    private LocalDate date;
    private Time time;
    private String villaBedID;
    private String apartmentBedID;

    public Booking(String bookingID,String userID,LocalDate date,Time time,String villaBedID,String apartmentBedID){
        this.bookingID = bookingID;
        this.userID = userID;
        this.date = date;
        this.time = time;
        this.villaBedID = villaBedID;
        this.apartmentBedID = apartmentBedID;
    }
    public String getBookingID(){
        return bookingID;
    }
    public String getUserID(){
        return userID;
    }
    public LocalDate getDate(){
        return date;
    }
    public Time getTime(){
        return time;
    }
    public String getVillaBedID(){
        return villaBedID;
    }
    public String getApartmentBedID(){
        return apartmentBedID;
    }
}
