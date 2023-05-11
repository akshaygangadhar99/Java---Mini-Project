package com.example.check1;

import com.example.check1.BackendMethods.VisualMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import com.example.check1.BackendMethods.VisualMethods.*;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PieChartStreetWiseReligion implements Initializable {
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart barChart;
    @FXML
    private PieChart pieChart2;
    @FXML
    private Hyperlink goBack;
    @FXML
    private String regID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.regID = readUserChoice();
        loadPieChart();
        loadBarChart();
        loadPieChart2();
    }
    public void loadPieChart(){
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
            pieChart.setStartAngle(90);
            pieChart.setClockwise(true);
            pieChart.setLabelsVisible(true);
            pieChart.setLegendSide(Side.BOTTOM);
            pieChart.setTitle("Gender Demographics");
            pieChartData.get(0).getNode().setStyle("-fx-pie-color: lightblue;");
            pieChartData.get(1).getNode().setStyle("-fx-pie-color: lightpink;");

            // Add labels to the chart
            for (PieChart.Data data : pieChart.getData()) {
                Label label = new Label(String.format("%.0f", data.getPieValue()));
                label.setStyle("-fx-font-size: 12px;");
                data.getNode().setUserData(label);
//                pieChart.getChartChildren().add(label);
            }
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
    public void loadBarChart(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Distance from University");
        yAxis.setLabel("Total Bookings");

        // Create a series for the bar chart and add data to it
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        int[] count = VisualMethods.getDistanceWiseCount();
        series.getData().add(new XYChart.Data<>("< 1 km", count[0]));
        series.getData().add(new XYChart.Data<>("Between 1 & 2 km", count[1]));
        series.getData().add(new XYChart.Data<>("> 2 km", count[2]));

        // Add labels to the data
//        for (XYChart.Data<String, Number> data : series.getData()) {
//            String label = data.getYValue().toString();
//            data.setNode(new Label(label));
//        }

        // Add the series to the bar chart
        barChart.getData().add(series);

        // Set the title of the bar chart
        barChart.setTitle("Distance-wise Bookings");

        // Add the bar chart to your scene
        // scene is the object of Scene class
//        scene.setRoot(barChart);
    }
    public void loadPieChart2(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int[] count = VisualMethods.getStrWiseReligionCount("2");
        pieChartData.add(new PieChart.Data("Hindu", count[0]));
        pieChartData.add(new PieChart.Data("Christian", count[1]));
        pieChartData.add(new PieChart.Data("Islam", count[2]));
        pieChartData.add(new PieChart.Data("Atheist", count[3]));
        pieChartData.add(new PieChart.Data("Buddhism", count[4]));
        pieChartData.add(new PieChart.Data("Sikh", count[5]));
        pieChart2.setData(pieChartData);
        pieChart2.setLabelLineLength(10);
        pieChart2.setLegendVisible(false);
        pieChart2.setStartAngle(90);
        pieChart2.setClockwise(true);
        pieChart2.setLabelsVisible(true);
        pieChart2.setLegendSide(Side.BOTTOM);
        pieChart2.setTitle("Thicket St ~ Religion-wise Distribution");
    }
    public String readUserChoice(){
        String userChoice = "";
        try {
            File file = new File(".userChoice.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                userChoice+=line;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userChoice;
    }

    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
