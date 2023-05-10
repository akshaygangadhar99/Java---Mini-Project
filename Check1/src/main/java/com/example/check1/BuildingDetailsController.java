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

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class BuildingDetailsController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn ApartmentID;
    @FXML
    private TableColumn ApartmentNo;
    @FXML
    private TableColumn Floor;
    @FXML
    private Hyperlink goBack;
    private int sharingChoice;
    private ObservableList<Apartment> apartments = FXCollections.observableArrayList();
    private String regID;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql="";
        String[] userChoice = readUserChoice();
        this.regID = userChoice[0];
        this.sharingChoice = Integer.parseInt(userChoice[1]);
        String buildingID = userChoice[2];
        if(sharingChoice==1){
            sql="select apt_id,tblapartment.bld_id,bld_name,apt_no,room_1,room_2,room_3,tblapartment.availability " +
                    "from tblapartment join tblbuilding on tblapartment.bld_id=tblbuilding.bld_id where tblapartment.bld_id="+buildingID+" " +
                    "and (room_2<>0 or room_3<>0) and tblapartment.availability=1;";
        } else if (sharingChoice==2) {
            sql="select apt_id,tblapartment.bld_id,bld_name,apt_no,room_1,room_2,room_3,tblapartment.availability " +
                    "from tblapartment join tblbuilding on tblapartment.bld_id=tblbuilding.bld_id where tblapartment.bld_id="+buildingID+" " +
                    "and room_1<>0 and tblapartment.availability=1;";
        }
        getDataFromDatabaseForBuildingDetails(sql);
        addColumnForSelectingBuilding();
    }
    public String[] readUserChoice(){
        String[] userChoice = new String[3];
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
    public void getDataFromDatabaseForBuildingDetails(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "0123456789");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String apt_id = rs.getString("apt_id");
                String bld_id = rs.getString("bld_id");
                String apt_no = rs.getString("apt_no");
                int room_1 = rs.getInt("room_1");
                int room_2 = rs.getInt("room_2");
                int room_3 = rs.getInt("room_3");
                int availability = rs.getInt("availability");
                String floor = Character.toString(apt_no.charAt(0));
                Apartment apartment = new Apartment(apt_id,bld_id,apt_no,floor,room_1,room_2,room_3,availability);
                apartments.add(apartment);
            }
            tableView.setItems(apartments);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(ApartmentID,Floor,ApartmentNo);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForSelectingBuilding(){
        TableColumn<Apartment, Hyperlink> hyperlink = new TableColumn<>("Select");
        hyperlink.setCellValueFactory(cellData -> {
            Apartment apartment = cellData.getValue();
            Hyperlink link = new Hyperlink("Select");
            link.setOnAction(event -> {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Selected Apartment");
//                alert.setHeaderText(null);
//                alert.setContentText("Apartment ID "+apartment.getApartmentID()+"Apartment Name"+apartment.getApartmentNo());
//                alert.showAndWait();
                if(sharingChoice==2){
                    UserAuthentication admin = new UserAuthentication();
                    String content = regID+"\n"+apartment.getApartmentID();
                    admin.writeToAHiddenFile(content);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("apartmentFourSharingDetails.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Apartment Details");
                    stage.setMaximized(false);
                    stage.setMaximized(true);
                    stage.show();
                } else if (sharingChoice==1) {
                    UserAuthentication admin = new UserAuthentication();
                    String content = regID+"\n"+apartment.getApartmentID();
                    admin.writeToAHiddenFile(content);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("apartmentThreeSharingDetails.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Apartment Details");
                    stage.setMaximized(false);
                    stage.setMaximized(true);
                    stage.show();
                }
            });
            return new SimpleObjectProperty<>(link);
        });
        tableView.getColumns().add(hyperlink);
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
}
