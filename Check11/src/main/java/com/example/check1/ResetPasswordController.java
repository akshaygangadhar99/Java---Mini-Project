package com.example.check1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ResetPasswordController {

    public Hyperlink goBack;
    public ImageView christLogoImage;
    public TextField txtResetRegID;
    public PasswordField txtResetPassword;
    public Button btnReset;
    public PasswordField txtResetCnfPassword;

    public void resetPassword() throws IOException {
        UserAuthentication reset = new UserAuthentication();
        String regID = txtResetRegID.getText();
        String password = txtResetPassword.getText();
        String cnfPassword = txtResetCnfPassword.getText();
        if(regID.isEmpty() || password.isEmpty() || cnfPassword.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("MISSING");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter the details!");
            alert.showAndWait();
        } else {
            if(reset.getUserName(regID).isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INVALID");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid Registration Number!");
                alert.showAndWait();
            } else{
                if(password.equals(cnfPassword)){
                    if(reset.resetPassword(regID,password)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("PASSWORD RESET SUCCESSFULL!");
                        alert.showAndWait();
                        goBackToLogin();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("INVALID");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter valid Registration Number!");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("INVALID");
                    alert.setHeaderText(null);
                    alert.setContentText("Passwords did not match!");
                    alert.showAndWait();
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
}
