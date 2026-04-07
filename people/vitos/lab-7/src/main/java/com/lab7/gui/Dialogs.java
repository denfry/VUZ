package com.lab7.gui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public final class Dialogs {

    private Dialogs() {
    }

    public static void showInfo(String title, String message, Stage stage) {
        showDialog(title, message, Alert.AlertType.INFORMATION, stage);
    }

    public static void showError(String title, String message, Stage stage) {
        showDialog(title, message, Alert.AlertType.ERROR, stage);
    }

    private static void showDialog(String title, String message, Alert.AlertType type, Stage stage) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        if (stage != null) {
            alert.initOwner(stage);
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
