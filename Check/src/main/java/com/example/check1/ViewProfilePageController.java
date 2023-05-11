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
    private int housingChoice;
    private int sharingChoice;
    private String houseID;
    private String buildingID;
    private int flag=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = new UserAuthentication();
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.userID = userChoice[1];
        try{
            this.houseID = userChoice[2];
            this.housingChoice = Integer.parseInt(userChoice[3]);
            this.sharingChoice = Integer.parseInt(userChoice[4]);
            this.buildingID = userChoice[5];
        } catch (Exception e){

        }

        if (userID.equals(regID)) {
            String userName = user.getUserName(regID);
            String sql = "SELECT CONCAT(user_fname, ' ', user_lname) AS full_name, FLOOR(DATEDIFF(CURDATE(), user_dob) / 365) AS age,user_gender,user_course_id, CONCAT(user_city, ', ', user_state,', ',user_country) AS address,user_email,user_religion from tbluser where user_id=" + userID + ";";
            try {
                getDataFromDatabaseForProfileDetails(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            HeadingLabel.setText(userName + "'s Profile!");
        } else {
            String userName = user.getUserName(userID);
            String sql = "SELECT CONCAT(user_fname, ' ', user_lname) AS full_name, FLOOR(DATEDIFF(CURDATE(), user_dob) / 365) AS age,user_gender,user_course_id, CONCAT(user_city, ', ', user_state,', ',user_country) AS address,user_email,user_religion,user_food_preference,user_bio,user_personality,user_cooking_ability,user_smoker,user_alcohol,user_language  FROM tbluser join tblpreferences on tbluser.user_id=tblpreferences.user_id where tbluser.user_id=" + userID + ";";
            try {
                getDataFromDatabaseForProfileDetails(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            HeadingLabel.setText(userName + "'s Profile!");
        }
    }

    public String[] readUserChoice() {
        String[] userChoice = new String[6];
        try {
            File file = new File(".userChoice.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("view")){
                    fileReader.close();
                    this.flag=1;
                    return userChoice;
                } else if (line.equals("booked")) {
                    fileReader.close();
                    this.flag=2;
                    return userChoice;
                } else if (line.equals("viewbooked")) {
                    fileReader.close();
                    this.flag=3;
                    return userChoice;
                } else{
                    userChoice[i] = line;
                    i++;
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userChoice;
    }

    public void getDataFromDatabaseForProfileDetails(String sql) throws SQLException {
        Connection conn;
        try {
            String name = "";
            int age = 0;
            String gender = "";
            String courseName = "";
            String address = "";
            String email = "";
            String religion = "";
            String food = "";
            String bio = "";
            String cook = "";
            String alcohol = "";
            String smoker = "";
            String languages = "";
            int personality = 0;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.isAfterLast()) {
                    break;
                }
                name = rs.getString("full_name");
                age = rs.getInt("age");
                int user_gender = rs.getInt("user_gender");
                gender = "";
                if (user_gender == 1) {
                    gender = "Male";
                } else if (user_gender == 2) {
                    gender = "Female";
                } else {
                    gender = "Others";
                }
                String courseID = rs.getString("user_course_id");
                courseName = user.getCourseName(courseID);
                address = rs.getString("address");
                email = rs.getString("user_email");
                religion = rs.getString("user_religion");
            }
            sql = "SELECT user_food_preference,user_bio,user_personality,user_cooking_ability,user_smoker,user_alcohol,user_language FROM tblpreferences where tblpreferences.user_id=" + userID + ";";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if (rs.isAfterLast()) {
                    break;
                }
                int food_preferences = rs.getInt("user_food_preference");
                food = "";
                if (food_preferences == 1) {
                    food = "Pure Vegiterian";
                } else if (food_preferences == 2) {
                    food = "Pure Non-Vegiterian";
                } else {
                    food = "Both Veg and Non-Veg";
                }
                bio = rs.getString("user_bio");
                personality = rs.getInt("user_personality");
                int cookingAbility = rs.getInt("user_cooking_ability");
                cook = "";
                if (cookingAbility == 1) {
                    cook = "Cannot Cook";
                } else if (cookingAbility == 2) {
                    cook = "Can cook partially";
                } else {
                    cook = "Can cook well";
                }
                int user_smoker = rs.getInt("user_smoker");
                smoker = "";
                if (user_smoker == 1) {
                    smoker = "Non-Smoker";
                } else if (user_smoker == 2) {
                    smoker = "Occasional Smoker";
                } else {
                    smoker = "Heavy-Smoker";
                }
                int user_alcohol = rs.getInt("user_alcohol");
                alcohol = "";
                if (user_alcohol == 1) {
                    alcohol = "Non-Consumer";
                } else if (user_alcohol == 2) {
                    alcohol = "Occasional Drinker";
                } else {
                    alcohol = "Heavy-Drinker";
                }
                languages = rs.getString("user_language");
            }
            UserDetails userDetails = new UserDetails(name, age, gender, courseName, address, email, religion, food, bio, personality, cook, smoker, alcohol, languages);
            Label label = new Label(userDetails.getName());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center; -fx-margin: 100;");
            gridPane.add(label, 1, 0);
            label = new Label(Integer.toString(userDetails.getAge()));
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 1);
            label = new Label(userDetails.getGender());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 2);
            label = new Label(userDetails.getCourse_name());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 3);
            label = new Label(userDetails.getAddress());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 4);
            label = new Label(userDetails.getEmail());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 5);
            label = new Label(userDetails.getReligion());
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
            gridPane.add(label, 1, 6);
            if(userDetails.getFood()!=null){
                label = new Label(userDetails.getFood());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 7);
                label = new Label(userDetails.getBio());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 8);
                label = new Label(Integer.toString(userDetails.getPersonality()));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 9);
                label = new Label(userDetails.getCook());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 10);
                label = new Label(userDetails.getSmoker());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 11);
                label = new Label(userDetails.getAlcohol());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 12);
                label = new Label(userDetails.getLanguages());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");
                gridPane.add(label, 1, 13);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        conn.close();
    }

    public void goToHomePage() throws IOException {
        if(flag==0){
            Parent root = null;
            if(housingChoice==1 && sharingChoice==2){
                user.writeToAHiddenFile(regID+"\n"+houseID+"\n"+housingChoice+"\n"+sharingChoice);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("villaFourSharingDetails.fxml")));
            } else if(housingChoice==1 && sharingChoice==1){
                user.writeToAHiddenFile(regID+"\n"+houseID+"\n"+housingChoice+"\n"+sharingChoice);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("villaThreeSharingDetails.fxml")));
            } else if (housingChoice==2 && sharingChoice==2) {
                user.writeToAHiddenFile(regID+"\n"+houseID+"\n"+buildingID+"\n"+housingChoice+"\n"+sharingChoice);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("apartmentFourSharingDetails.fxml")));
            } else if (housingChoice==2 && sharingChoice==1) {
                user.writeToAHiddenFile(regID+"\n"+houseID+"\n"+buildingID+"\n"+housingChoice+"\n"+sharingChoice);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("apartmentThreeSharingDetails.fxml")));
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Housing Details");
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
        } else if(flag==1){
            user.writeToAHiddenFile(regID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
        } else if(flag==2) {
            user.writeToAHiddenFile(regID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("roomMateDetails.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("RoomMates");
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
        } else if (flag==3) {
            user.writeToAHiddenFile(regID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("bookedHomePage.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
        }
    }

}
