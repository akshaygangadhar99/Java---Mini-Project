package com.example.check1;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class RoomMateDetailsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn BedNumber;
    @FXML
    private TableColumn RoomNumber;
    @FXML
    private TableColumn UserName;
    @FXML
    private Hyperlink goBack;
    @FXML
    private String regID;
    @FXML
    private String aptID;
    private String streetName;
    private String buildingName;
    @FXML
    private String villaID;
    @FXML
    private Label HeadingLabel;

    private ObservableList<ApartmentBed> apartmentBeds = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql="";
        this.regID = readUserChoice();
        sql="select bed_id,user_id,tblaptbooking.apt_id,apt_no,tblbuilding.bld_name,tblstreet.street_name from tblaptbooking join tblapartment on tblaptbooking.apt_id=tblapartment.apt_id join tblbuilding on tblapartment.bld_id=tblbuilding.bld_id join tblstreet on tblbuilding.street_id=tblstreet.street_id where tblaptbooking.apt_id=(select apt_id from tblaptbooking where bed_id=(select apt_bed_id from tblbookingdetails where user_id="+regID+"));";
        UserAuthentication user = new UserAuthentication();
        if(user.hasRecords(sql)){
            getDataFromDatabase(sql);
            addColumnForBooking();
        } else {
            sql="select bed_id,user_id,tblvillabooking.villa_id,villa_no,street_name from tblvillabooking join tblvilla on tblvillabooking.villa_id=tblvilla.villa_id join tblstreet on tblvilla.street_id=tblstreet.street_id where tblvillabooking.villa_id=(select villa_id from tblvillabooking where bed_id=(select villa_bed_id from tblbookingdetails where user_id="+regID+"));";
            getDataFromDatabase(sql);
            addColumnForBooking();
        }
        if(aptID!=null){
            HeadingLabel.setText("Apartment Number "+aptID+", "+buildingName+", "+streetName);
        } else {
            HeadingLabel.setText("Villa Number "+villaID+", "+streetName);
        }
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
    public void getDataFromDatabase(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String bed_id = rs.getString("bed_id");
                String user_id = rs.getString("user_id");
                try{
                    this.aptID = rs.getString("apt_no");
                    this.buildingName = rs.getString("bld_name");
                    this.streetName = rs.getString("street_name");
                } catch (Exception e){

                }
                try{
                    this.villaID = rs.getString("villa_no");
                    this.streetName = rs.getString("street_name");
                } catch (Exception e){

                }
                int roomNo = Integer.parseInt(bed_id)%10;
                if(roomNo==0){
                    roomNo=10;
                }
                String roomID="";
                if(roomNo>=1 && roomNo<=4){
                    roomID = "1";
                } else if (roomNo>=5 && roomNo<=7) {
                    roomID = "2";
                }else {
                    roomID = "3";
                }
                if(user_id!=null){
                    UserAuthentication user = new UserAuthentication();
                    String userName = user.getUserName(user_id);
                    ApartmentBed apartmentBed = new ApartmentBed(bed_id,roomID,user_id,userName);
                    apartmentBeds.add(apartmentBed);
                }
            }
            tableView.setItems(apartmentBeds);
            tableView.getColumns().clear();;
            tableView.getColumns().addAll(BedNumber,RoomNumber,UserName);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForBooking(){
        TableColumn<ApartmentBed, Hyperlink> hyperlink = new TableColumn<>("BookedByStudent");
        hyperlink.setCellValueFactory(cellData -> {
            ApartmentBed apartmentBed = cellData.getValue();
            Hyperlink link = new Hyperlink("View Profile");
            link.setOnAction(event -> {
                UserAuthentication admin = new UserAuthentication();
                String content=regID+"\n"+apartmentBed.getUserID()+"\nbooked";
                admin.writeToAHiddenFile(content);
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
                stage.setTitle("User Details");
                stage.setMaximized(false);
                stage.setMaximized(true);
                stage.show();
            });
            return new SimpleObjectProperty<>(link);
        });
        tableView.getColumns().add(hyperlink);
    }
    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
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
