package com.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Класс для работы с диалоговыми окнами
 */
public class Dialogs {

    /**
     * Показать информационный диалог
     */
    public static void showDialog(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Показать диалог подтверждения
     * @return true если пользователь нажал OK, false если отменил
     */
    public static boolean showConfirmDialog(String question) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(null);
        alert.setContentText(question);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
