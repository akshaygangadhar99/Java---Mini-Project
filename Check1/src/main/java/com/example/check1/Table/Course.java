package com.example.check1.Table;

public class Course {

    String course_id;
    String course_name;

    public Course(){
        // Empty constructor
    }

    public Course(String courseID, String courseName){
        this.course_id = courseID;
        this.course_name = courseName;
    }

    public String getCourseID(){
        return this.course_id;
    }

    public String getCourseName(){
        return this.course_name;
    }
}
