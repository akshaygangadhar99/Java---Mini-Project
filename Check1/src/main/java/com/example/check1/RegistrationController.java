package com.example.check1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class RegistrationController {
    @FXML
    public TextField txtRegNo;
    @FXML
    public TextField txtFirstName;
    @FXML
    public TextField txtLastName;
    @FXML
    public DatePicker txtDOB;
    @FXML
    public RadioButton radioMale;
    @FXML
    public ToggleGroup gender;
    @FXML
    public RadioButton radioFemale;
    @FXML
    public TextField txtEmail;
    @FXML
    public RadioButton radioOthers;
    @FXML
    public ComboBox cmbCourse;
    @FXML
    public ComboBox cmbCity;
    @FXML
    public ComboBox cmbState;
    @FXML
    public ComboBox cmbCountry;
    @FXML
    public TextField txtReligion;
    @FXML
    public Button btnUploadImage;
    @FXML
    public Button btnRegister;
    @FXML
    public PasswordField txtPwd;
    @FXML
    public PasswordField txtCnfPwd;
    @FXML
    public Hyperlink goBack;

    public void registerUser() throws IOException {
        String regNo = txtRegNo.getText();
        String fName = txtFirstName.getText();
        String lName = txtLastName.getText();
        LocalDate dob = txtDOB.getValue();
        RadioButton choice = (RadioButton) gender.getSelectedToggle();
        String email = txtEmail.getText();
        String course = (String) cmbCourse.getValue();
        String city = (String) cmbCity.getValue();
        String state = (String) cmbState.getValue();
        String country = (String) cmbCountry.getValue();
        String religion = txtReligion.getText();
        String password = txtPwd.getText();
        String cnfPassword = txtCnfPwd.getText();
//        System.out.println("Hello"+regNo+fName+lName+dob+choice+email+course+city+state+country+religion+password+cnfPassword+gender);
        if(regNo.isEmpty() || fName.isEmpty() || lName.isEmpty() || dob==null || choice==null || email.isEmpty() || course==null || city.isEmpty() || state.isEmpty() || country.isEmpty() || religion.isEmpty() || password.isEmpty() || cnfPassword.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("MISSING");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the details!");
            alert.show();
        } else {
            if (!(regNo.matches("[0-9]+") && regNo.length() <= 10)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("CHECK");
                alert.setHeaderText(null);
                alert.setContentText("Registration Number can only be in numbers and less than 10 digits");
                alert.show();
            } else{
                UserAuthentication user = new UserAuthentication();
                if(!user.getUserName(regNo).isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("REMEMBER");
                    alert.setHeaderText(null);
                    alert.setContentText("You are already registered!");
                    alert.show();
                } else {
                    if(password.equals(cnfPassword)) {
                        password = txtCnfPwd.getText();
                        String gender = choice.getText();
                        int genderValue;
                        if(gender.equalsIgnoreCase("male")){
                            genderValue=1;
                        } else if (gender.equalsIgnoreCase("female")) {
                            genderValue=2;
                        } else if (gender.equalsIgnoreCase("others")) {
                            genderValue=3;
                        } else{
                            genderValue=0;
                        }
                        String courseID = "0";
                        switch (course){
                            case "Bsc":
                                courseID="1";
                                break;
                            case "BBA":
                                courseID="2";
                                break;
                            case "BA LLB":
                                courseID="3";
                                break;
                            case "BBA LLB":
                                courseID="4";
                                break;
                            case "BCom":
                                courseID="5";
                                break;
                            case "MSc":
                                courseID="6";
                                break;
                            case "MBA":
                                courseID="7";
                                break;
                            case "LLM":
                                courseID="8";
                                break;
                        }
                        User student = new User(regNo,fName,lName,dob,genderValue,courseID,course,city,state,country,email,religion,1,1,password);
                        if(user.registerUser(student)){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("REGISTRATION SUCCESSFULL!");
                            alert.setHeaderText(null);
                            alert.setContentText("Registration Number: "+regNo+"\nEmail: "+email);
                            alert.showAndWait();
                            goBackToLogin();
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("FAILED");
                            alert.setHeaderText(null);
                            alert.setContentText("Registration Failed! Try Again!");
                            alert.showAndWait();
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("INCORRECT DETAILS");
                        alert.setHeaderText(null);
                        alert.setContentText("Passwords did not match!");
                        alert.show();
                    }
                }
            }
        }
    }
    public void goBackToLogin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginPage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public void uploadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Handle the selected file
            String fileName = selectedFile.getName();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Details");
            alert.setHeaderText(null);
            alert.setContentText("Your image name is "+fileName);
            alert.showAndWait();
            btnUploadImage.setText(fileName);
        } else {

        }
    }

}
