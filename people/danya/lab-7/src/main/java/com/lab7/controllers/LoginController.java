package com.lab7.controllers;

import com.lab7.db.DatabaseManager;
import com.lab7.gui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class LoginController {

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;
    private boolean okClicked;
    private String url;

    public void initialize(Stage dialogStage) {
        this.dialogStage = dialogStage;
        loadProperties();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleLogin() {
        okClicked = false;

        String user = userField.getText().trim();
        String password = passwordField.getText();

        if (user.isEmpty() || password.isEmpty()) {
            Dialogs.showError("Ошибка ввода", "Логин и пароль должны быть заполнены.", dialogStage);
            passwordField.clear();
            return;
        }

        if (url == null || url.isBlank()) {
            Dialogs.showError("Ошибка конфигурации", "Не найден параметр URL_DB в файле conf.prop.", dialogStage);
            return;
        }

        try {
            DatabaseManager.connect(url, user, password);
            okClicked = true;
            dialogStage.close();
        } catch (SQLException e) {
            Dialogs.showError("Ошибка соединения с БД", e.getMessage(), dialogStage);
            passwordField.clear();
        }
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        File file = new File("conf.prop");
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
            url = properties.getProperty("URL_DB");
            userField.setText(properties.getProperty("User", ""));
            passwordField.setText(properties.getProperty("Pwd", ""));
        } catch (IOException e) {
            Dialogs.showError("Ошибка конфигурации", "Файл conf.prop отсутствует или недоступен.", dialogStage);
        }
    }
}