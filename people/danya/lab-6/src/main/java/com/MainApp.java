package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Главный класс JavaFX приложения для управления холодильниками
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            showRefrigerators(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показать окно с таблицей холодильников
     */
    private void showRefrigerators(Stage stage) throws IOException {
        // Загружаем FXML-файл
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/com/fxml/RefrigeratorsView.fxml"));
        AnchorPane page = loader.load();

        // Задаём заголовок окна
        stage.setTitle("Каталог холодильников");

        // Создаем сцену
        Scene scene = new Scene(page);
        stage.setScene(scene);

        // Показываем окно
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
