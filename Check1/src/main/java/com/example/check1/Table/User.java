package com.example.check1.Table;


import java.sql.Date;

public class User {
    String user_id;
    String fname;
    String lname;
    Date dob;
    int gender;
    String course_id;
    String city;
    String state;
    String country;
    String email;
    String religion;
    int user_type;
    int user_status;
    String password;

    public User(){
        // Empty constructor
    }

    public User(String ID, String firstName, String lastName, Date userDOB, int userGender,
                String courseID, String userCity, String userState, String userCountry,
                String userEmail, String userReligion, int userType, int userStatus,
                String userPW){
        this.user_id = ID;
        this.fname = firstName;
        this.lname = lastName;
        this.dob = userDOB;
        this.gender = userGender;
        this.course_id = courseID;
        this.city = userCity;
        this.state = userState;
        this.country = userCountry;
        this.email = userEmail;
        this.religion = userReligion;
        this.user_type = userType;
        this.user_status = userStatus;
        this.password = userPW;
    }

    public String getUserID(){
        return this.user_id;
    }

    public String getFirstName(){
        return this.fname;
    }

    public String getLastName(){
        return this.lname;
    }

    public Date getUserDOB(){
        return this.dob;
    }

    public int getGender(){
        return this.gender;
    }

    public String getCourseID(){
        return this.course_id;
    }

    public String getCity(){
        return this.city;
    }

    public String getState(){
        return this.state;
    }

    public String getCountry(){
        return this.country;
    }

    public String getEmail(){
        return this.email;
    }

    public String getReligion(){
        return this.religion;
    }

    public int getUserType(){
        return this.user_type;
    }

    public int getUserStatus(){
        return this.user_status;
    }

    public String getPassword(){
        return this.password;
    }




}
