package com.example.check1;

import com.example.check1.BackendMethods.AdminMethods;
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
import java.util.Optional;
import java.util.ResourceBundle;
import com.example.check1.BackendMethods.AdminMethods.*;

public class AdminHomePageController implements Initializable {
    public Button btnViewBookings;
    public Button btnBlockBuilding;
    public Button btnBlockApartment;
    public Button btnBlockVilla;
    public Button btnUnblockBuilding;
    public Button btnUnblockApartment;
    public Button btnUnblockVilla;
    public Button btnDeleteBookings;
    @FXML
    private Label HeadingLabel;
    @FXML
    private Hyperlink logout;
    private String regID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.regID = readUserChoice();
        HeadingLabel.setText("Welcome "+regID+", You are Admin!");
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
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID);
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
    public void blockBuilding() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+1);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminBuildingDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Building Details");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void blockApartment() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+2);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminBuildingDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Building Details");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void blockVilla() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+3);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminBuildingDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Building Details");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
