package com.example.check1;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class UserAuthentication {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbPortal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public boolean authenticateUser(String regNo, String password, int userType) {
        boolean isAuthenticated = false;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM tblUser WHERE user_id = ? AND user_password = ? AND user_type = ? AND user_status = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNo);
            stmt.setString(2, password);
            stmt.setString(3, String.valueOf(userType));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if(rs.getString(1).equals(regNo) && rs.getString(15).equals(password)){
                    isAuthenticated = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public boolean resetPassword(String regID,String password){
        boolean success = false;
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "Update tblUser set user_password='"+password+"' where user_id='"+regID+"'";
            Statement stmt = conn.createStatement();
            int check = stmt.executeUpdate(sql);
            if (check > 0) {
                success = true;
                return success;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean registerUser(User student){
        boolean success = false;
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "insert into tblUser(user_id,user_fname,user_lname,user_dob,user_gender,user_course_id,user_city,user_state,user_country,user_email,user_image,user_religion,user_type,user_status,user_password) VALUES("+student.getUserID()+",'"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getUserDOB()+"',"+student.getGender()+",'"+student.getCourseID()+"','"+student.getCity()+"','"+student.getState()+"','"+student.getCountry()+"','"+student.getEmail()+"',NULL,'"+student.getReligion()+"',1,1,'"+student.getPassword()+"');";
            Statement stmt = conn.createStatement();
            int check = stmt.executeUpdate(sql);
            if (check > 0) {
                success = true;
                return success;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }

    public void writeToAHiddenFile(String content){
        try {
            File userChoiceFile = new File(".userChoice.txt");
            FileWriter writer = new FileWriter(userChoiceFile);
            writer.write(content);
            writer.close();
//            System.out.println("Content written to the hidden file: " + userChoiceFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean confirmBed(String userID, String bedID, String houseType){
        boolean success = false;

        return success;
    }
    public String getBookingDetails(String userID, String bedID, String houseType){
        String content = "";
        return content;
    }
    public boolean insertUserPreference(String userID, int foodChoice, String bio, int personality, int cookAbility, int smoker, int alcohol, String language){
        boolean success = false;
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "insert into tblpreferences(user_id,user_food_preference,user_bio,user_personality,user_cooking_ability,user_smoker,user_alcohol,user_language)" +
                    " values('"+userID+"',"+foodChoice+",'"+bio+"',"+personality+","+cookAbility+","+smoker+","+alcohol+",'"+language+"');";
            Statement stmt = conn.createStatement();
            int check = stmt.executeUpdate(sql);
            if (check > 0) {
                success = true;
                return success;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }

    public String getUserName(String regID){
        String userName="";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "select user_fname from tbluser where user_id="+regID+";";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                userName = rs.getString("user_fname");
            }
            return userName;
        }
        catch (Exception e){

        }
        return userName;
    }
    public String getCourseName(String courseID){
        String courseName="";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "select course_name from tblcourse where course_id="+courseID+";";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                courseName = rs.getString("course_name");
            }
            return courseName;
        }
        catch (Exception e){
            return "-";
        }
    }
    public static void main(String[] args) {

    }
}
