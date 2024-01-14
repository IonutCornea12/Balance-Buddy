module com.example.proiect2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.BalanceBuddy to javafx.fxml;
    exports com.example.BalanceBuddy;
}