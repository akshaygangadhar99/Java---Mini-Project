package com.example.check1;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class HousingDetailsController implements Initializable {
    @FXML
    public TableColumn buildingName;
    @FXML
    public TableColumn streetName;
    @FXML
    public TableColumn distance;
    @FXML
    private TableView tableView;
    private int housingChoice;
    private int sharingChoice;
    private ObservableList<Villa> villas = FXCollections.observableArrayList();
    private ObservableList<Building> buildings = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Hyperlink goBack;
    @FXML
    private Label HeadingLabel;
    @FXML
    private String regID;

    public void initialize(URL url, ResourceBundle rb){
        String sql="";
        int[] userChoice = readUserChoice();
        this.regID = Integer.toString(userChoice[0]);
        this.housingChoice=userChoice[1];
        this.sharingChoice=userChoice[2];
        if(housingChoice==1 && sharingChoice==1){
            HeadingLabel.setText("Villa Listings");
            buildingName.setText("Villa No.");
            sql="select villa_id,tblvilla.street_id,street_name,villa_no,room_1,room_2,room_3,uni_distance,availability from tblvilla inner join tblstreet on tblstreet.street_id=tblvilla.street_id where (room_2<>0 or room_3<>0) and availability=1;";
            getDataFromDatabaseForVilla(sql);
            addColumnForVilla();
        } else if (housingChoice==1 && sharingChoice==2) {
            HeadingLabel.setText("Villa Listings");
            buildingName.setText("Villa No.");
            sql="select villa_id,tblvilla.street_id,street_name,villa_no,room_1,room_2,room_3,uni_distance,availability from tblvilla inner join tblstreet on tblstreet.street_id=tblvilla.street_id where room_1<>0 and availability=1;";
            getDataFromDatabaseForVilla(sql);
            addColumnForVilla();
        } else if (housingChoice==2 && sharingChoice==1) {
            HeadingLabel.setText("Building Listings");
            PropertyValueFactory<Villa, Hyperlink> buildingNameCellValueFactory = new PropertyValueFactory<>("buildingName");
            buildingName.setCellValueFactory(buildingNameCellValueFactory);
            buildingName.setText("Building Name");
            sql="select bld_id,tblBuilding.street_id,street_name,bld_name,uni_distance,availability from tblBuilding inner join tblStreet on tblBuilding.street_id=tblstreet.street_id where four_sharing_avail=1 and availability=1;";
            getDataFromDatabaseForBuilding(sql);
            addColumnForBuilding();
        } else if (housingChoice==2 && sharingChoice==2) {
            HeadingLabel.setText("Building Listings");
            PropertyValueFactory<Villa, Hyperlink> buildingNameCellValueFactory = new PropertyValueFactory<>("buildingName");
            buildingName.setCellValueFactory(buildingNameCellValueFactory);
            buildingName.setText("Building Name");
            sql="select bld_id,tblBuilding.street_id,street_name,bld_name,uni_distance,availability from tblBuilding inner join tblStreet on tblBuilding.street_id=tblstreet.street_id where three_sharing_avail=1 and availability=1;";
            getDataFromDatabaseForBuilding(sql);
            addColumnForBuilding();
        }
    }
    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
    public int[] readUserChoice(){
        int[] userChoice = new int[3];
        try {
            File file = new File(".userChoice.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i=0;
            while ((line = bufferedReader.readLine()) != null) {
                userChoice[i]=Integer.parseInt(line);
                i++;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userChoice;
    }
    public void getDataFromDatabaseForVilla(String sql) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String villa_id = rs.getString("villa_id");
                String street_id = rs.getString("street_id");
                int room_1 = rs.getInt("room_1");
                int room_2 = rs.getInt("room_2");
                int room_3 = rs.getInt("room_3");
                String street_name = rs.getString("street_name");
                String villa_no = rs.getString("villa_no");
                float distance = rs.getFloat("uni_distance");
                int availability = rs.getInt("availability");
                Villa villa = new Villa(villa_id,street_id,street_name, villa_no,room_1,room_2,room_3,distance,availability);
                villas.add(villa);
            }
            tableView.setItems(villas);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(streetName, buildingName , distance);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getDataFromDatabaseForBuilding(String sql) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.isAfterLast()) {
                    break;
                }
                String buildingID = rs.getString("bld_id");
                String streetID = rs.getString("street_id");
                String streetName = rs.getString("street_name");
                String buildingName = rs.getString("bld_name");
                float distance = rs.getFloat("uni_distance");
                int availability = rs.getInt("availability");
                Building building = new Building(buildingID, streetID, streetName, buildingName, distance, availability);
                buildings.add(building);
            }
            tableView.setItems(buildings);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(streetName, buildingName, distance);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForVilla(){
        TableColumn<Villa, Hyperlink> hyperlink = new TableColumn<>("Select");
        hyperlink.setCellValueFactory(cellData -> {
            Villa villa = cellData.getValue();
            Hyperlink link = new Hyperlink("View");
            link.setOnAction(event -> {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Housing Type Selected");
//                alert.setHeaderText(null);
//                alert.setContentText("Villa ID "+villa.getVillaID()+"Street Name"+villa.getStreetName());
//                alert.showAndWait();
                UserAuthentication admin = new UserAuthentication();
                admin.writeToAHiddenFile(regID+"\n"+villa.getVillaID()+"\n"+housingChoice+"\n"+sharingChoice);
                FXMLLoader loader = new FXMLLoader();
                if(sharingChoice==2) {
                    loader = new FXMLLoader(getClass().getResource("villaFourSharingDetails.fxml"));
                } else if (sharingChoice==1) {
                    loader = new FXMLLoader(getClass().getResource("villaThreeSharingDetails.fxml"));
                }
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                Stage stage = (Stage) tableView.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Villa Bed Listings");
                stage.setMaximized(false);
                stage.setMaximized(true);
                stage.show();
            });
            return new SimpleObjectProperty<>(link);
        });
        tableView.getColumns().add(hyperlink);
    }
    public void addColumnForBuilding(){
        TableColumn<Building, Hyperlink> hyperlink = new TableColumn<>("Select");
        hyperlink.setCellValueFactory(cellData -> {
            Building building = cellData.getValue();
            Hyperlink link = new Hyperlink("View");
            link.setOnAction(event -> {
                UserAuthentication admin = new UserAuthentication();
                String content = regID+"\n"+building.getBuildingID()+"\n"+housingChoice+"\n"+sharingChoice;
                admin.writeToAHiddenFile(content);
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Housing Type Selected");
//                alert.setHeaderText(null);
//                alert.setContentText("Building ID"+building.getBuildingID()+"Street Name"+building.getStreetName());
//                alert.showAndWait();
                try {
                    goToBuildingDetailsPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return new SimpleObjectProperty<>(link);
        });
        tableView.getColumns().add(hyperlink);
    }
    public void goToBuildingDetailsPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("buildingDetails.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Apartment Listings");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
