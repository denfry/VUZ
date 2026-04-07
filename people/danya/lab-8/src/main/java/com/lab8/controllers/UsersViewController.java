package com.lab8.controllers;

import com.lab8.db.DatabaseManager;
import com.lab8.gui.Dialogs;
import com.lab8.model.UserDirectoryItem;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UsersViewController {

    @FXML
    private TableView<UserDirectoryItem> usersTable;
    @FXML
    private TableColumn<UserDirectoryItem, Number> idColumn;
    @FXML
    private TableColumn<UserDirectoryItem, String> emailColumn;
    @FXML
    private TableColumn<UserDirectoryItem, String> roleColumn;
    @FXML
    private TableColumn<UserDirectoryItem, String> planColumn;
    @FXML
    private TableColumn<UserDirectoryItem, Number> analysesColumn;
    @FXML
    private TableColumn<UserDirectoryItem, Boolean> activeColumn;
    @FXML
    private TableColumn<UserDirectoryItem, LocalDateTime> createdAtColumn;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private Stage dialogStage;

    @FXML
    public void initialize() {
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        planColumn.setCellValueFactory(cellData -> cellData.getValue().planProperty());
        analysesColumn.setCellValueFactory(cellData -> cellData.getValue().totalAnalysesProperty());
        activeColumn.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
        createdAtColumn.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());

        idColumn.setStyle("-fx-alignment: center-right;");
        analysesColumn.setStyle("-fx-alignment: center-right;");
        createdAtColumn.setStyle("-fx-alignment: center-right;");
        createdAtColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : dateFormatter.format(item));
            }
        });

        loadUsers();
    }

    public void initialize(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleRefresh() {
        loadUsers();
    }

    @FXML
    private void handleClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void loadUsers() {
        try {
            usersTable.setItems(DatabaseManager.loadUsers());
        } catch (SQLException e) {
            Dialogs.showError("Ошибка загрузки", "Не удалось получить данные users: " + e.getMessage(), dialogStage);
        }
    }
}
