package com.example.check1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PieChartStreetWiseReligion implements Initializable {
    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbportal", "root", "root");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_gender,COUNT(*) as count FROM tbluser GROUP BY user_gender");

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while (rs.next()) {
                int intGender = rs.getInt("user_gender");
                String gender = "";
                if(intGender==1){
                    gender = "Male";
                } else if (intGender==2) {
                    gender = "Female";
                } else{
                    gender = "Others";
                }
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(gender, count));
            }
            pieChart.setData(pieChartData);
            pieChart.setLabelLineLength(10);
            pieChart.setLegendVisible(false);
            pieChart.setLabelsVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
