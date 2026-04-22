module com.lab7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;
    requires jasperreports;
    requires java.desktop;

    opens com.lab7 to javafx.fxml;
    opens com.lab7.controllers to javafx.fxml;
    opens com.lab7.model to javafx.base;

    exports com.lab7;
    exports com.lab7.controllers;
    exports com.lab7.model;
    exports com.lab7.db;
}
