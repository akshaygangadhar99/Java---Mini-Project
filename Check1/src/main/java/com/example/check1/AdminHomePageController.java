package com.example.check1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminHomePageController implements Initializable {
    public Button btnViewBookings;
    @FXML
    private Label HeadingLabel;
    @FXML
    private Hyperlink logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HeadingLabel.setText("Welcome "+readUserChoice()+", You are Admin!");
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
    public void viewBookings() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingDetails.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnViewBookings.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Booking Details");
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
