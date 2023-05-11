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


public class VillaFourSharingDetailsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn BedNumber;
    @FXML
    private TableColumn Availability;
    @FXML
    private Hyperlink goBack;
    @FXML
    private String regID;
    @FXML
    private String villaID;
    private int housingChoice;
    private int sharingChoice;

    private ObservableList<VillaBed> villaBeds = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql="";
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.villaID = userChoice[1];
        this.housingChoice = Integer.parseInt(userChoice[2]);
        this.sharingChoice = Integer.parseInt(userChoice[3]);
        sql="select bed_id,tblvillabooking.villa_id,user_id,tblvillabooking.availability " +
                "from tblvillabooking join tblvilla on tblvillabooking.villa_id=tblvilla.villa_id " +
                "where tblvillabooking.villa_id="+villaID+" and " +
                "bed_id>"+(Integer.parseInt(villaID)-1)*10+" and " +
                "bed_id<="+(Integer.parseInt(villaID)*10-6)+";";
        getDataFromDatabaseForVillaFourSharing(sql);
        addColumnForBooking();
    }
    public String[] readUserChoice(){
        String[] userChoice = new String[4];
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
    public void getDataFromDatabaseForVillaFourSharing(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String bed_id = rs.getString("bed_id");
                String villa_id = rs.getString("villa_id");
                int intAvailability = rs.getInt("availability");
                String availability="";
                if(intAvailability==0){
                    availability="NO";
                }
                else{
                    availability="YES";
                }
                String user_id = rs.getString("user_id");
                String roomID = "1";
                VillaBed villaBed = new VillaBed(bed_id,roomID,villa_id,user_id,availability);
                villaBeds.add(villaBed);
            }
            tableView.setItems(villaBeds);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(BedNumber, Availability);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForBooking(){
        TableColumn<VillaBed, Hyperlink> hyperlink = new TableColumn<>("BookedByStudent");
        hyperlink.setCellValueFactory(cellData -> {
            VillaBed villaBed = cellData.getValue();
            if(villaBed.getAvailability().equalsIgnoreCase("YES")){
                Hyperlink link = new Hyperlink("Book");
                link.setOnAction(event -> {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Selected Bed");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Bed ID "+villaBed.getBedID()+"Room"+villaBed.getRoomID());
//                    alert.showAndWait();
                    UserAuthentication admin = new UserAuthentication();
                    String content=regID+"\n"+villaBed.getBedID()+"\n"+"villa"+"\n"+housingChoice+"\n"+sharingChoice;
                    admin.writeToAHiddenFile(content);
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
                    stage.setTitle("Confirm Booking");
                    stage.setMaximized(false);
                    stage.setMaximized(true);
                    stage.show();
                });
                return new SimpleObjectProperty<>(link);
            }
            else{
                Hyperlink link = new Hyperlink("View");
                String userId = villaBed.getUserID();
                link.setOnAction(event -> {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Selected Bed");
//                    alert.setHeaderText(null);
//                    alert.setContentText("UserID "+userId);
//                    alert.showAndWait();
                    UserAuthentication admin = new UserAuthentication();
                    if(userId==null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("NOT AVAILABLE");
                        alert.setHeaderText(null);
                        alert.setContentText("This bed has been blocked by the university.");
                        alert.show();
                    }else{
                        admin.writeToAHiddenFile(regID+"\n"+userId+"\n"+villaID+"\n"+housingChoice+"\n"+sharingChoice);
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
                    }
                });
                return new SimpleObjectProperty<>(link);
            }

        });
        tableView.getColumns().add(hyperlink);
    }
    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID+"\n"+housingChoice+"\n"+sharingChoice);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("housingDetails.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
