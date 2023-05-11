module com.example.check1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.check1 to javafx.fxml;
    exports com.example.check1;
}