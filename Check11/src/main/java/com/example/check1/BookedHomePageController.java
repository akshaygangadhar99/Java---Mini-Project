package com.example.check1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookedHomePageController implements Initializable {
    public Button btnViewBookings;
    @FXML
    private Label HeadingLabel;
    @FXML
    private Hyperlink logout;
    private String regID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserAuthentication user = new UserAuthentication();
        this.regID = readUserChoice();
        String userName = user.getUserName(regID);
        HeadingLabel.setText("Welcome "+userName+"!!");
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
    public void viewProfile() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+regID+"\nviewbooked");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewProfilePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void viewRoomMates() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("roomMateDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("RoomMates");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }

    public void goBackToLogin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }


}
