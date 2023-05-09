package com.example.check1;

import java.time.LocalDate;

public class UserDetails {
    private String name;
    private int age;
    private String gender;
    private String course_name;
    private String address;
    private String email;
    private String religion;
    private String food;
    private String bio;
    private int personality;
    private String cook;
    private String smoker;
    private String alcohol;
    private String languages;


    public UserDetails(String name, int age, String gender, String course_name, String address,
                       String email, String religion, String food, String bio, int personality,
                       String cook, String smoker, String alcohol, String languages){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.course_name = course_name;
        this.address = address;
        this.email = email;
        this.religion = religion;
        this.food = food;
        this.bio = bio;
        this.personality = personality;
        this.cook = cook;
        this.smoker = smoker;
        this.alcohol = alcohol;
        this.languages = languages;
    }

    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    public String getGender(){
        return this.gender;
    }
    public String getCourse_name(){
        return this.course_name;
    }
    public String getAddress(){
        return this.address;
    }
    public String getEmail(){
        return this.email;
    }
    public String getReligion(){
        return this.religion;
    }
    public String getFood(){
        return this.food;
    }
    public String getBio(){
        return this.bio;
    }
    public int getPersonality(){
        return this.personality;
    }
    public String getCook(){
        return this.cook;
    }
    public String getSmoker(){
        return this.smoker;
    }
    public String getAlcohol(){
        return this.alcohol;
    }
    public String getLanguages(){
        return this.languages;
    }






}
