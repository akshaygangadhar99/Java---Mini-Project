package com.example.check1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AdminsInsertion {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/roommate_portal", "root", "0123456789");
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO admins (name, email, password) VALUES " +
                    "('Karan', 'karan@example.com', 'password1'), " +
                    "('Akshay', 'akshay@example.com', 'password2')";

            stmt.executeUpdate(sql);
            System.out.println("Admins inserted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
