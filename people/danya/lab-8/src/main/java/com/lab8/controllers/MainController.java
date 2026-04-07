package com.lab8.controllers;

import com.lab8.db.DatabaseManager;
import com.lab8.gui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainController {

    @FXML
    private ImageView imageView;
    @FXML
    private Label databaseInfoLabel;

    private Stage dialogStage;

    public void initialize(Stage dialogStage) {
        this.dialogStage = dialogStage;
        loadImage();
        loadDatabaseInfo();
    }

    @FXML
    private void handleOpenDatabaseWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lab8/fxml/DatabaseView.fxml"));
            BorderPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Окно работы с БД");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setScene(new Scene(page, 900, 520));

            DatabaseController controller = loader.getController();
            controller.initialize(stage);

            stage.showAndWait();
        } catch (IOException e) {
            Dialogs.showError("Ошибка", "Не удалось открыть форму БД: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleUsersView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lab8/fxml/UsersView.fxml"));
            BorderPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Справочник: Пользователи");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(dialogStage);
            stage.setScene(new Scene(page, 980, 520));

            UsersViewController controller = loader.getController();
            controller.initialize(stage);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.showError("Ошибка", "Не удалось открыть форму справочника: " + e, dialogStage);
        }
    }

    @FXML
    private void handleExit() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void loadImage() {
        try (InputStream stream = getClass().getResourceAsStream("/images/database-banner.png")) {
            if (stream != null) {
                imageView.setImage(new Image(stream));
            }
        } catch (Exception ignored) {
            imageView.setVisible(false);
            imageView.setManaged(false);
        }
    }

    private void loadDatabaseInfo() {
        if (databaseInfoLabel == null) {
            return;
        }

        String productName = DatabaseManager.getDatabaseProductName();
        String databaseName = DatabaseManager.getDatabaseName();

        if (productName == null || productName.isBlank()) {
            databaseInfoLabel.setText("Работа с таблицей базы данных открывается через меню сверху.");
            return;
        }

        if (databaseName == null || databaseName.isBlank()) {
            databaseInfoLabel.setText("Подключение выполнено к СУБД: " + productName + ".");
            return;
        }

        databaseInfoLabel.setText("Подключение выполнено к СУБД: " + productName + ", база данных: " + databaseName + ".");
    }
}

