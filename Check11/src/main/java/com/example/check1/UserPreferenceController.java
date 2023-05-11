package com.example.check1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserPreferenceController implements Initializable {

    public Hyperlink goBack;
    public ToggleGroup FoodPreference;
    public Slider sliPersonality;
    public ToggleGroup CookngAbility;
    public ToggleGroup Smoker;
    public ToggleGroup Alcohol;
    public TextField txtLanguages;
    public TextArea txtBio;
    public Button btnConfirmBooking;
    private String userID;
    private String bedID;
    private String houseType;
    private String houseID;
    private int housingChoice;
    private int sharingChoice;
    private String buildingID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        String[] userChoice = readUserChoice();
        this.userID = userChoice[0];
        this.bedID = userChoice[1];
        this.houseType = userChoice[2];
        this.housingChoice = Integer.parseInt(userChoice[3]);
        this.sharingChoice = Integer.parseInt(userChoice[4]);
        try{
            this.buildingID = userChoice[5];
        } catch (Exception e){

        }
        UserAuthentication user = new UserAuthentication();
        this.houseID = user.getHouseID(bedID);
    }
    public String[] readUserChoice(){
        String[] userChoice = new String[6];
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
    public void confirmBed(){
        try{
            RadioButton radioFoodChoice = (RadioButton) FoodPreference.getSelectedToggle();
            RadioButton radioCookChoice = (RadioButton) CookngAbility.getSelectedToggle();
            RadioButton radioSmokerChoice = (RadioButton) Smoker.getSelectedToggle();
            RadioButton radioAlcoholChoice = (RadioButton) Alcohol.getSelectedToggle();
            String languages = txtLanguages.getText();
            String bio = txtBio.getText();
            if(radioFoodChoice==null || radioCookChoice==null || radioSmokerChoice==null || radioAlcoholChoice==null || languages.isEmpty() || bio.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("MISSING");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter the details!");
                alert.show();
            }else{
                String strFoodChoice = radioFoodChoice.getText();
                int foodChoice = 0;
                if(strFoodChoice.equalsIgnoreCase("Vegetarian")){
                    foodChoice = 1;
                } else if (strFoodChoice.equalsIgnoreCase("Non-Vegetarian")) {
                    foodChoice = 2;
                } else if (strFoodChoice.equalsIgnoreCase("Both")) {
                    foodChoice = 3;
                }
                int personality = 0;
                personality = (int) sliPersonality.getValue();
                String strCookAbility = radioCookChoice.getText();
                int cookAbility = 0;
                if(strCookAbility.equalsIgnoreCase("Cannot cook")){
                    cookAbility = 1;
                } else if (strCookAbility.equalsIgnoreCase("Can cook a little")) {
                    cookAbility = 2;
                } else if (strCookAbility.equalsIgnoreCase("Can cook well")) {
                    cookAbility = 3;
                }
                String strSmoker = radioSmokerChoice.getText();
                int smoker = 0;
                if(strSmoker.equalsIgnoreCase("Non-Smoker")){
                    smoker = 1;
                } else if (strSmoker.equalsIgnoreCase("Occasional Smoker")) {
                    smoker = 2;
                } else if (strSmoker.equalsIgnoreCase("Heavy Smoker")) {
                    smoker = 3;
                }
                String strAlcohol = radioAlcoholChoice.getText();
                int alcohol = 0;
                if(strAlcohol.equalsIgnoreCase("Non-Consumer")){
                    alcohol = 1;
                } else if (strAlcohol.equalsIgnoreCase("Occasional")) {
                    alcohol = 2;
                } else if (strAlcohol.equalsIgnoreCase("Frequent")) {
                    alcohol = 3;
                }
                UserAuthentication admin = new UserAuthentication();
                if(UpdateTables.updateTables(userID,bedID,houseType) && admin.insertUserPreference(userID,foodChoice,bio,personality,cookAbility,smoker,alcohol,languages)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("CONGRATULATIONS!");
                    alert.setHeaderText(null);
                    String content = "Your booking is SUCCESSFUL";
                    alert.setContentText(content);
                    alert.showAndWait();
                    goBackToBookedHome();
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SORRY!");
                    alert.setHeaderText(null);
                    String content = "Your booking FAILED";
                    alert.setContentText(content);
                    alert.showAndWait();
                }
            }

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("REQUIRED");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the details");
            alert.show();
        }

    }
    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        Parent root = null;
        if(housingChoice==1 && sharingChoice==2){
            user.writeToAHiddenFile(userID+"\n"+houseID+"\n"+housingChoice+"\n"+sharingChoice);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("villaFourSharingDetails.fxml")));
        } else if(housingChoice==1 && sharingChoice==1){
            user.writeToAHiddenFile(userID+"\n"+houseID+"\n"+housingChoice+"\n"+sharingChoice);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("villaThreeSharingDetails.fxml")));
        } else if (housingChoice==2 && sharingChoice==2) {
            user.writeToAHiddenFile(userID+"\n"+houseID+"\n"+buildingID+"\n"+housingChoice+"\n"+sharingChoice);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("apartmentFourSharingDetails.fxml")));
        } else if (housingChoice==2 && sharingChoice==1) {
            user.writeToAHiddenFile(userID+"\n"+houseID+"\n"+buildingID+"\n"+housingChoice+"\n"+sharingChoice);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("apartmentThreeSharingDetails.fxml")));
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Bed Listings");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void goBackToBookedHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(userID);
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
