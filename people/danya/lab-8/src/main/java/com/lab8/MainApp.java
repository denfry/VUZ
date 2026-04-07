package com.lab8;

import com.lab8.controllers.LoginController;
import com.lab8.controllers.MainController;
import com.lab8.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        if (showLoginDialog(primaryStage)) {
            showMainWindow();
        } else {
            primaryStage.close();
        }
    }

    private boolean showLoginDialog(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lab8/fxml/LoginView.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Авторизация");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            dialogStage.setScene(new Scene(page));

            LoginController controller = loader.getController();
            controller.initialize(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lab8/fxml/MainView.fxml"));
            BorderPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Главное окно");
            stage.setScene(new Scene(page, 980, 700));

            MainController controller = loader.getController();
            controller.initialize(stage);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        DatabaseManager.disconnect();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

