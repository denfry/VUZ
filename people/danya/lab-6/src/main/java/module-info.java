module com {
    requires javafx.controls;
    requires javafx.fxml;

    opens com to javafx.fxml;
    opens com.controllers to javafx.fxml;
    opens com.data to javafx.base;

    exports com;
    exports com.controllers;
    exports com.data;
    exports com.gui;
}
