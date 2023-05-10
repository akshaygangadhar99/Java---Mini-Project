package com.example.check1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginController {

    public PasswordField password;
    public RadioButton radioAdmin;
    public ToggleGroup UserType;
    public RadioButton radioStudent;
    public Button btnLogin;
    public Hyperlink newUser;
    public Hyperlink forgotPassword;
    public ImageView christLogoImage;
    public TextField txtRegID;

    public void verifyUser(ActionEvent event) throws IOException {
        String regID = txtRegID.getText();
        String password1 = password.getText();
        RadioButton choice = (RadioButton) UserType.getSelectedToggle();
        if(regID.isEmpty() || password1.isEmpty() || choice==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("MISSING");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the details!");
            alert.show();
        }
        else{
            int userType=0;
            if(choice.getText().equalsIgnoreCase("admin")){
                userType=2;
            } else if (choice.getText().equalsIgnoreCase("student")) {
                userType = 1;
            }
            UserAuthentication verify=new UserAuthentication();
            if(userType==1 && verify.authenticateUser(regID,password1,userType)){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Login Success");
//                alert.setHeaderText(null);
//                alert.setContentText("Welcome "+ userName);
//                alert.show();
                verify.writeToAHiddenFile(regID);

                if(!verify.checkPreference(regID)){
                    goToHomePage();
                } else {
                    goToBookedHomePage();
                }
            } else if (userType==2 && verify.authenticateUser(regID,password1,userType)){

//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Login Success");
//                alert.setHeaderText(null);
//                alert.setContentText("Welcome "+verify.getUserName(regID));
//                alert.show();
                verify.writeToAHiddenFile(verify.getUserName(regID));
                goToAdminHomePage();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Credentials");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username/password/usertype");
                alert.show();
            }
        }

    }
    public void handleForgotPassword() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("forgotPassword.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) forgotPassword.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void handleNewUser() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registrationPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) newUser.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Registration Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }

    public void goToHomePage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void goToAdminHomePage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminHomePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void goToBookedHomePage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("bookedHomePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
