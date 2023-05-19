package com.example.check1;
import java.sql.*;

public class CreateTable {

    static final String DB_URL = "jdbc:mysql://localhost/roommate_portal";
    static final String USER = "root";
    static final String PASS = "0123456789";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Create the Students table
//            String sql = "CREATE TABLE Students " +
//                    "(id INTEGER not NULL AUTO_INCREMENT, " +
//                    " name VARCHAR(255), " +
//                    " email VARCHAR(255), " +
//                    " password VARCHAR(255), " +
//                    " bio_description VARCHAR(255), " +
//                    " introvert BOOLEAN, " +
//                    " veg BOOLEAN, " +
//                    " PRIMARY KEY ( id ), " +
//                    " UNIQUE (email))";
//            String sql="";

            String sql = "CREATE TABLE IF NOT EXISTS tblCourse (course_id varchar(10)," +
                    "course_name varchar(100)," +
                    "CONSTRAINT PRIMARY KEY (course_id))";
            stmt.executeUpdate(sql);

            sql="CREATE TABLE IF NOT EXISTS tblUser (user_id varchar(10)," +
                    "user_fname varchar(100), user_lname varchar(100), user_dob date," +
                    "user_gender int, user_course_id varchar(10), user_city varchar(100)," +
                    "user_state varchar(100), user_country varchar(100), user_email varchar(150), " +
                    "user_image varchar(250), user_religion varchar(50), user_type int, user_status int," +
                    "user_password varchar(100)," +
                    "CONSTRAINT PRIMARY KEY (user_id), " +
                    "CONSTRAINT FOREIGN KEY (user_course_id) REFERENCES tblCourse(course_id))";
            stmt.executeUpdate(sql);

            // Create the Stays table
            sql = "CREATE TABLE IF NOT EXISTS Stays " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " stay_type VARCHAR(255), " +
                    " distance_from_college INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);

            // Create the Rooms table
            sql = "CREATE TABLE IF NOT EXISTS Rooms " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " stay_id INTEGER, " +
                    " room_number INTEGER, " +
                    " capacity INTEGER, " +
                    " PRIMARY KEY ( id ), " +
                    " FOREIGN KEY (stay_id) REFERENCES Stays(id))";
            stmt.executeUpdate(sql);

            // Create the Beds table
            sql = "CREATE TABLE IF NOT EXISTS Beds " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " room_id INTEGER, " +
                    " student_id INTEGER, " +
                    " booking_date DATE, " +
                    " PRIMARY KEY ( id ), " +
                    " FOREIGN KEY (room_id) REFERENCES Rooms(id), " +
                    " FOREIGN KEY (student_id) REFERENCES Students(id))";
            stmt.executeUpdate(sql);

            // Create the Admins table
            sql = "CREATE TABLE IF NOT EXISTS Admins " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " email VARCHAR(255), " +
                    " password VARCHAR(255), " +
                    " PRIMARY KEY ( id ), " +
                    " UNIQUE (email))";
            stmt.executeUpdate(sql);

            System.out.println("Tables created successfully!");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.getMessage();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
