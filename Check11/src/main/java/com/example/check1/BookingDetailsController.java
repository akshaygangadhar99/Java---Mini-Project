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
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class BookingDetailsController implements Initializable {
    @FXML
    public TableColumn BookingID;
    @FXML
    public TableColumn UserID;
    @FXML
    public TableColumn Date;
    @FXML
    public TableColumn Time;
    @FXML
    public TableColumn VillaBedID;
    @FXML
    public TableColumn ApartmentBedID;
    @FXML
    private TableView tableView;
    @FXML
    private Hyperlink goBack;
    private String regID;
    @FXML
    private AnchorPane anchorPane;
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sql="";
        this.regID = readUserChoice();
        sql="select * from tblbookingdetails;";
        getDataFromDatabaseForBookingDetails(sql);
        addColumnForDeleting();
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
    public void getDataFromDatabaseForBookingDetails(String sql){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbPortal", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.isAfterLast()){
                    break;
                }
                String booking_id = rs.getString("booking_id");
                String user_id = rs.getString("user_id");
                LocalDate date = rs.getDate("booking_date").toLocalDate();
                Time time = rs.getTime("booking_time");
                String villa_bed_id = rs.getString("villa_bed_id");
                String apartment_bed_id = rs.getString("apt_bed_id");
                Booking booking = new Booking(booking_id,user_id,date,time,villa_bed_id,apartment_bed_id);
                bookings.add(booking);
            }
            tableView.setItems(bookings);
            tableView.getColumns().clear();
            tableView.getColumns().addAll(BookingID,UserID,Date,Time,VillaBedID,ApartmentBedID);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addColumnForDeleting(){
        TableColumn<Booking, Hyperlink> hyperlink = new TableColumn<>("Delete");
        hyperlink.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            Hyperlink link = new Hyperlink("Delete");
            link.setOnAction(event -> {
                if(AdminMethods.deleteUserBooking(booking.getUserID())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("Booking with "+booking.getUserID()+", deleted Successfully");
                    alert.show();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("bookingDetails.fxml")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)  anchorPane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Booking Details");
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
