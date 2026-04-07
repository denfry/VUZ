module com.lab8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.lab8 to javafx.fxml;
    opens com.lab8.controllers to javafx.fxml;
    opens com.lab8.model to javafx.base;

    exports com.lab8;
    exports com.lab8.controllers;
    exports com.lab8.model;
    exports com.lab8.db;
}

