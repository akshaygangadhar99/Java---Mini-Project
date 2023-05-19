package com.example.check1;

import com.example.check1.BackendMethods.AdminMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdminBuildingDetailsController implements Initializable {
    @FXML
    public TableColumn buildingName;
    @FXML
    public TableColumn streetName;
    @FXML
    public TableColumn distance;
    @FXML
    private TableView tableView;
    private ObservableList<Apartment> apartments = FXCollections.observableArrayList();
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
    private String choice;


    public void initialize(URL url, ResourceBundle rb){
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.choice = userChoice[1];
        String sql="";
        if(Integer.parseInt(choice)==1){
            sql="select * from tblBuilding join tblStreet on tblBuilding.street_id=tblstreet.street_id;";
            loadData(sql);
            addColumnForBlocking();
        } else if (Integer.parseInt(choice)==2) {
            sql="select apt_id,bld_name,apt_no,tblapartment.availability from tblapartment join tblbuilding on tblapartment.bld_id=tblbuilding.bld_id;";
            loadData(sql);
            addColumnForBlocking();
        } else if (Integer.parseInt(choice)==3) {
            sql="select * from tblvilla join tblstreet on tblstreet.street_id=tblvilla.street_id;";
            loadData(sql);
            addColumnForBlocking();
        }
    }

    public String[] readUserChoice(){
        String[] userChoice = new String[2];
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
    public void loadData(String sql) {
        if(Integer.parseInt(choice)==1){
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if(rs.isAfterLast()){
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
        } else if(Integer.parseInt(choice)==2){
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if(rs.isAfterLast()){
                        break;
                    }
                    String apt_id = rs.getString("apt_id");
                    String bld_name = rs.getString("bld_name");
                    String apt_no = rs.getString("apt_no");
                    int availability = rs.getInt("availability");
                    String floor = Character.toString(apt_no.charAt(0));
                    Apartment apartment = new Apartment(apt_id,bld_name,apt_no,floor,availability);
                    apartments.add(apartment);
                }
                tableView.setItems(apartments);
                tableView.getColumns().clear();
                TableColumn<Apartment, String> BuildingName = new TableColumn<>("Building Name");
                BuildingName.setCellValueFactory(new PropertyValueFactory<>("buildingName"));
                TableColumn<Apartment, String> Floor = new TableColumn<>("Floor");
                Floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
                TableColumn<Apartment, String> ApartmentNo = new TableColumn<>("Apartment No");
                ApartmentNo.setCellValueFactory(new PropertyValueFactory<>("apartmentNo"));
                tableView.getColumns().addAll(BuildingName, ApartmentNo, Floor);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(Integer.parseInt(choice)==3){
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
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
                TableColumn<Villa, String> StreetName = new TableColumn<>("Street Name");
                StreetName.setCellValueFactory(new PropertyValueFactory<>("streetName"));
                TableColumn<Villa, String> VillaNo = new TableColumn<>("Villa Number");
                VillaNo.setCellValueFactory(new PropertyValueFactory<>("villaNo"));
                TableColumn<Villa, String> Distance = new TableColumn<>("Distance in (KM)");
                Distance.setCellValueFactory(new PropertyValueFactory<>("distance"));
                tableView.getColumns().addAll(StreetName, VillaNo, Distance);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void addColumnForBlocking(){
        if(Integer.parseInt(choice)==1){
            TableColumn<Building, Hyperlink> hyperlink = new TableColumn<>("Block");
            hyperlink.setCellValueFactory(cellData -> {
                Building building = cellData.getValue();
                Hyperlink link = new Hyperlink();
                if(building.getAvailability()==1){
                    link.setText("Block");
                } else{
                    link.setText("Unblock");
                }
                link.setOnAction(event -> {
                    if(AdminMethods.changeBuildingAvailability(building.getBuildingID(),0) && link.getText().equals("Block")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Blocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Building "+building.getBuildingName()+", Blocked Successfully");
                        alert.show();
                        link.setText("Unblock");
                    } else if(AdminMethods.changeBuildingAvailability(building.getBuildingID(),1) && link.getText().equals("Unblock")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unblocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Building "+building.getBuildingName()+", Unblocked Successfully");
                        alert.show();
                        link.setText("Block");
                    }
                });
                return new SimpleObjectProperty<>(link);
            });
            tableView.getColumns().add(hyperlink);
        } else if (Integer.parseInt(choice)==2) {
            TableColumn<Apartment, Hyperlink> hyperlink = new TableColumn<>("Block");
            hyperlink.setCellValueFactory(cellData -> {
                Apartment apartment = cellData.getValue();
                Hyperlink link = new Hyperlink();
                if(apartment.getAvailability()==1){
                    link.setText("Block");
                } else{
                    link.setText("Unblock");
                }
                link.setOnAction(event -> {
                    if(AdminMethods.changeApartmentAvailability(apartment.getApartmentID(),0) && link.getText().equals("Block")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Blocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Apartment "+apartment.getApartmentID()+", Blocked Successfully");
                        alert.show();
                        link.setText("Unblock");
                    } else if(AdminMethods.changeApartmentAvailability(apartment.getApartmentID(),1) && link.getText().equals("Unblock")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unblocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Apartment "+apartment.getApartmentID()+", Unblocked Successfully");
                        alert.show();
                        link.setText("Block");
                    }
                });
                return new SimpleObjectProperty<>(link);
            });
            tableView.getColumns().add(hyperlink);
        }else if (Integer.parseInt(choice)==3) {
            TableColumn<Villa, Hyperlink> hyperlink = new TableColumn<>("Block");
            hyperlink.setCellValueFactory(cellData -> {
                Villa villa = cellData.getValue();
                Hyperlink link = new Hyperlink();
                if(villa.getAvailability()==1){
                    link.setText("Block");
                } else{
                    link.setText("Unblock");
                }
                link.setOnAction(event -> {
                    if(AdminMethods.changeVillaAvailability(villa.getVillaID(),0) && link.getText().equals("Block")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Blocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Villa "+villa.getVillaID()+", Blocked Successfully");
                        alert.show();
                        link.setText("Unblock");
                    } else if(AdminMethods.changeVillaAvailability(villa.getVillaID(),1) && link.getText().equals("Unblock")){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unblocked");
                        alert.setHeaderText(null);
                        alert.setContentText("Villa "+villa.getVillaID()+", Unblocked Successfully");
                        alert.show();
                        link.setText("Block");
                    }
                });
                return new SimpleObjectProperty<>(link);
            });
            tableView.getColumns().add(hyperlink);
        }
    }

    public void goBackToHome() throws IOException {
        UserAuthentication user = new UserAuthentication();
        user.writeToAHiddenFile(regID);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminHomePage.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.show();
    }
}
