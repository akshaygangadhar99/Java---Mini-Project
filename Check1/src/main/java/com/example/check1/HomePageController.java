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

public class HomePageController implements Initializable {
    @FXML
    private Label HeadingLabel;
    @FXML
    public ToggleGroup houseType;
    @FXML
    public ToggleGroup sharingType;
    @FXML
    public Button btnFindRooms;

    @FXML
    private Hyperlink logout;
    @FXML
    private Hyperlink viewProfile;
    private String regID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserAuthentication user = new UserAuthentication();
        this.regID = readUserChoice();
        String userName = user.getUserName(regID);
        HeadingLabel.setText("Welcome "+userName+"!");
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
    public void findRooms() throws IOException {
        RadioButton radioHouseChoice = (RadioButton) houseType.getSelectedToggle();
        RadioButton radioSharingChoice = (RadioButton) sharingType.getSelectedToggle();
        if(radioHouseChoice==null || radioSharingChoice==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("MISSING");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the details!");
            alert.show();
        } else {
            int houseChoice=0;
            int sharingChoice=0;
            if(radioHouseChoice.getText().equalsIgnoreCase("villa")){
                houseChoice=1;
            } else if (radioHouseChoice.getText().equalsIgnoreCase("apartment")) {
                houseChoice=2;
            }
            if(radioSharingChoice.getText().equalsIgnoreCase("3-sharing")){
                sharingChoice=1;
            }
            else if (radioSharingChoice.getText().equalsIgnoreCase("4-sharing")){
                sharingChoice=2;
            }
            try {
                File userChoiceFile = new File(".userChoice.txt");
                FileWriter writer = new FileWriter(userChoiceFile);
                String content=regID+"\n"+houseChoice+"\n"+sharingChoice;
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("housingDetails.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnFindRooms.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("House Listings");
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.show();
        }
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
    public void viewProfile() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+regID+"\n"+"view");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewProfilePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) viewProfile.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Student Details");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
