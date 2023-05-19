package com.example.check1;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class ApartmentThreeSharingDetailsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn BedNumber;
    @FXML
    private TableColumn Availability;
    @FXML
    private TableColumn RoomNumber;
    @FXML
    private Hyperlink goBack;
    private String regID;
    private String apartmentID;
    private int sharingChoice;
    private int housingChoice;
    private String buildingID;

    private ObservableList<ApartmentBed> apartmentBeds = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql="";
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.apartmentID = userChoice[1];
        this.buildingID = userChoice[2];
        this.housingChoice = Integer.parseInt(userChoice[3]);
        this.sharingChoice = Integer.parseInt(userChoice[4]);
        sql="select bed_id,tblaptbooking.apt_id,user_id,tblaptbooking.availability,apt_no,tblaptbooking.availability" +
                " from tblaptbooking join tblapartment on tblaptbooking.apt_id=tblapartment.apt_id " +
                "where tblapartment.apt_id="+apartmentID+" and bed_id>"+(Integer.parseInt(apartmentID)*10-6)+
                " and bed_id<="+(Integer.parseInt(apartmentID))*10+";";
        getDataFromDatabaseForVillaThreeSharing(sql);
        addColumnForBooking();
    }
    public String[] readUserChoice(){
        String[] userChoice = new String[5];
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
    public void getDataFromDatabaseForVillaThreeSharing(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String bed_id = rs.getString("bed_id");
                String apartmentID = rs.getString("apt_id");
                int intAvailability = rs.getInt("availability");
                String availability="";
                if(intAvailability==0){
                    availability="NO";
                }
                else{
                    availability="YES";
                }
                String user_id = rs.getString("user_id");
                String roomID = "";
                if(Integer.parseInt(bed_id)<=(Integer.parseInt(apartmentID)*10-3)){
                    roomID = "2";
                }
                else{
                    roomID = "3";
                }
                ApartmentBed apartmentBed = new ApartmentBed(bed_id,roomID,apartmentID,user_id,availability);
                apartmentBeds.add(apartmentBed);
            }
            tableView.setItems(apartmentBeds);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(BedNumber,RoomNumber,Availability);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForBooking(){
        TableColumn<ApartmentBed, Hyperlink> hyperlink = new TableColumn<>("Booking");
        hyperlink.setCellValueFactory(cellData -> {
            ApartmentBed apartmentBed = cellData.getValue();
            if(apartmentBed.getAvailability().equalsIgnoreCase("YES")){
                Hyperlink link = new Hyperlink("Book");
                link.setOnAction(event -> {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Booking Success");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Bed ID "+apartmentBed.getBedID()+"Room"+apartmentBed.getRoomID());
//                    alert.showAndWait();
                    UserAuthentication admin = new UserAuthentication();
                    admin.writeToAHiddenFile(regID+"\n"+apartmentBed.getBedID()+"\n"+"apartment"+"\n"+housingChoice+"\n"+sharingChoice+"\n"+buildingID);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userPreferencePage.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("User Details");
                    stage.setMaximized(false);
                    stage.setMaximized(true);
                    stage.show();
                });
                return new SimpleObjectProperty<>(link);
            }
            else{
                Hyperlink link = new Hyperlink("View");
                String userId = apartmentBed.getUserID();
                link.setOnAction(event -> {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Selected Bed");
//                    alert.setHeaderText(null);
//                    alert.setContentText("User ID "+userId);
//                    alert.showAndWait();
                    if(userId==null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("NOT AVAILABLE");
                        alert.setHeaderText(null);
                        alert.setContentText("This bed has been blocked by the university.");
                        alert.show();
                    }else{
                        UserAuthentication admin = new UserAuthentication();
                        admin.writeToAHiddenFile(regID+"\n"+userId+"\n"+apartmentID+"\n"+housingChoice+"\n"+sharingChoice+"\n"+buildingID);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("viewProfilePage.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) tableView.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("User Profile");
                        stage.setMaximized(false);
                        stage.setMaximized(true);
                        stage.show();
                    }
                });
                return new SimpleObjectProperty<>(link);
            }

        });
        tableView.getColumns().add(hyperlink);
    }
    public void goBackToHome() throws IOException {
        UserAuthentication admin = new UserAuthentication();
        admin.writeToAHiddenFile(regID+"\n"+buildingID+"\n"+housingChoice+"\n"+sharingChoice);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("buildingDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Apartment Listings");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
