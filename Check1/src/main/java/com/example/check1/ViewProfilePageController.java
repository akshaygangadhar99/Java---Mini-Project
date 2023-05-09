package com.example.check1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewProfilePageController implements Initializable {
    @FXML
    public Hyperlink goBack;
    @FXML
    private Label HeadingLabel;
    private UserAuthentication user;
    @FXML
    private GridPane gridPane;
    @FXML
    private String regID;
    @FXML
    private String userID;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = new UserAuthentication();
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.userID = userChoice[1];
        if(userID.equals(regID)){
            String userName = user.getUserName(regID);
            String sql = "SELECT CONCAT(user_fname, ' ', user_lname) AS full_name, FLOOR(DATEDIFF(CURDATE(), user_dob) / 365) AS age,user_gender,user_course_id, CONCAT(user_city, ', ', user_state,', ',user_country) AS address,user_email,user_religion,user_food_preference,user_bio,user_personality,user_cooking_ability,user_smoker,user_alcohol,user_language  FROM tbluser join tblpreferences on tbluser.user_id=tblpreferences.user_id where tbluser.user_id=" + regID + ";";
            getDataFromDatabaseForProfileDetails(sql);
            HeadingLabel.setText(userName + "'s Profile!");
        }else {
            String userName = user.getUserName(userID);
            String sql = "SELECT CONCAT(user_fname, ' ', user_lname) AS full_name, FLOOR(DATEDIFF(CURDATE(), user_dob) / 365) AS age,user_gender,user_course_id, CONCAT(user_city, ', ', user_state,', ',user_country) AS address,user_email,user_religion,user_food_preference,user_bio,user_personality,user_cooking_ability,user_smoker,user_alcohol,user_language  FROM tbluser join tblpreferences on tbluser.user_id=tblpreferences.user_id where tbluser.user_id=" + userID + ";";
            getDataFromDatabaseForProfileDetails(sql);
            HeadingLabel.setText(userName + "'s Profile!");
        }
    }
    public String[] readUserChoice(){
        String[] userChoice = new String[2];
        try {
            File file = new File(".userChoice.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i=0;
            while ((line = bufferedReader.readLine()) != null) {
                userChoice[i]=line;
                i++;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userChoice;
    }

    public void getDataFromDatabaseForProfileDetails(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String name = rs.getString("full_name");
                int age = rs.getInt("age");
                int user_gender = rs.getInt("user_gender");
                String gender="";
                if(user_gender==1){
                    gender="Male";
                } else if (user_gender==2) {
                    gender="Female";
                } else{
                    gender="Others";
                }
                String courseID = rs.getString("user_course_id");
                String courseName = user.getCourseName(courseID);
                String address = rs.getString("address");
                String email = rs.getString("user_email");
                String religion = rs.getString("user_religion");
                int food_preferences = rs.getInt("user_food_preference");
                String food="";
                if(food_preferences==1){
                    food="Pure Vegiterian";
                } else if (food_preferences==2) {
                    food="Pure Non-Vegiterian";
                } else{
                    food="Both Veg and Non-Veg";
                }
                String bio = rs.getString("user_bio");
                int personality = rs.getInt("user_personality");
                int cookingAbility = rs.getInt("user_cooking_ability");
                String cook="";
                if(cookingAbility==1){
                    cook="Cannot Cook";
                } else if (cookingAbility==2) {
                    cook="Can cook partially";
                } else{
                    cook="Can cook well";
                }
                int user_smoker = rs.getInt("user_smoker");
                String smoker="";
                if(user_smoker==1){
                    smoker="Non-Smoker";
                } else if (user_smoker==2) {
                    smoker="Occasional Smoker";
                } else{
                    smoker="Heavy-Smoker";
                }
                int user_alcohol = rs.getInt("user_alcohol");
                String alcohol="";
                if(user_alcohol==1){
                    alcohol="Non-Consumer";
                } else if (user_alcohol==2) {
                    alcohol="Occasional Drinker";
                } else{
                    alcohol="Heavy-Drinker";
                }
                String languages = rs.getString("user_language");
                UserDetails userDetails = new UserDetails(name,age,gender,courseName,address,email,religion,food,bio,personality,cook,smoker,alcohol,languages);
                Label label = new Label(userDetails.getName());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center; -fx-margin: 100;");
                gridPane.add(label,1,0);
                label = new Label(Integer.toString(userDetails.getAge()));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,1);
                label = new Label(userDetails.getGender());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,2);
                label = new Label(userDetails.getCourse_name());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,3);
                label = new Label(userDetails.getAddress());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,4);
                label = new Label(userDetails.getEmail());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,5);
                label = new Label(userDetails.getReligion());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,6);
                label = new Label(userDetails.getFood());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,7);
                label = new Label(userDetails.getBio());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,8);
                label = new Label(Integer.toString(userDetails.getPersonality()));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,9);
                label = new Label(userDetails.getCook());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,10);
                label = new Label(userDetails.getSmoker());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,11);
                label = new Label(userDetails.getAlcohol());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,12);
                label = new Label(userDetails.getLanguages());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label,1,13);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void goToHomePage() throws IOException {
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
