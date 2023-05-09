package com.example.check1;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    private String user_id;
    private String fname;
    private String lname;
    private LocalDate dob;
    private int gender;
    private String course_id;
    private String course_name;
    private String city;
    private String state;
    private String country;
    private String email;
    private String religion;
    private int user_type;
    private int user_status;
    private String password;

    public User(String ID, String firstName, String lastName, LocalDate userDOB, int userGender,
                String courseID, String course_name, String userCity, String userState, String userCountry,
                String userEmail, String userReligion, int userType, int userStatus,
                String userPW){
        this.user_id = ID;
        this.fname = firstName;
        this.lname = lastName;
        this.dob = userDOB;
        this.gender = userGender;
        this.course_id = courseID;
        this.course_name = course_name;
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

    public LocalDate getUserDOB(){
        return this.dob;
    }

    public int getGender(){
        return this.gender;
    }

    public String getCourseID(){
        return this.course_id;
    }

    public String getCourse_name(){
        return this.course_name;
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
