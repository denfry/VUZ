package com.controllers;

import com.data.Refrigerator;
import com.gui.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для управления таблицей холодильников
 */
public class RefrigeratorsViewController implements Initializable {

    // Таблица и колонки
    @FXML
    private TableView<Refrigerator> refrigeratorsTable;
    @FXML
    private TableColumn<Refrigerator, String> manufacturerColumn;
    @FXML
    private TableColumn<Refrigerator, String> colorColumn;
    @FXML
    private TableColumn<Refrigerator, Double> heightColumn;
    @FXML
    private TableColumn<Refrigerator, Double> widthColumn;
    @FXML
    private TableColumn<Refrigerator, Double> depthColumn;
    @FXML
    private TableColumn<Refrigerator, Double> volumeColumn;

    // Поля ввода
    @FXML
    private TextField edManufacturer;
    @FXML
    private ComboBox<String> cbColor;
    @FXML
    private TextField edHeight;
    @FXML
    private TextField edWidth;
    @FXML
    private TextField edDepth;

    // Кнопки
    @FXML
    private Button bAdd;
    @FXML
    private Button bEdit;
    @FXML
    private Button bDel;
    @FXML
    private Button bClear;

    // Набор данных
    private ObservableList<Refrigerator> refrigeratorsData;

    /**
     * Инициализация контроллера
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Инициализация колонок таблицы
        manufacturerColumn.setCellValueFactory(cellData -> cellData.getValue().manufacturerProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        heightColumn.setCellValueFactory(cellData -> cellData.getValue().heightProperty().asObject());
        widthColumn.setCellValueFactory(cellData -> cellData.getValue().widthProperty().asObject());
        depthColumn.setCellValueFactory(cellData -> cellData.getValue().depthProperty().asObject());

        // Колонка для вычисляемого значения объема
        volumeColumn.setCellValueFactory(cellData -> {
            Refrigerator ref = cellData.getValue();
            return javafx.beans.binding.Bindings.createObjectBinding(
                () -> ref.calculateVolume(),
                ref.heightProperty(), ref.widthProperty(), ref.depthProperty()
            );
        });

        // Форматирование числовых колонок
        heightColumn.setCellFactory(tc -> new TableCell<Refrigerator, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value));
                }
            }
        });

        widthColumn.setCellFactory(tc -> new TableCell<Refrigerator, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value));
                }
            }
        });

        depthColumn.setCellFactory(tc -> new TableCell<Refrigerator, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value));
                }
            }
        });

        volumeColumn.setCellFactory(tc -> new TableCell<Refrigerator, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value));
                }
            }
        });

        // Заполнение списка цветов
        String[] colors = {"Белый", "Серебристый", "Черный", "Красный", "Синий", "Серый"};
        cbColor.setItems(FXCollections.observableArrayList(colors));

        // Загрузка тестовых данных
        loadData();

        // Добавление данных в таблицу
        refrigeratorsTable.setItems(refrigeratorsData);

        // Обработчик двойного клика для редактирования
        refrigeratorsTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Refrigerator selected = refrigeratorsTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    showRefrigerator(selected);
                }
            }
        });
    }

    /**
     * Загрузка тестовых данных
     */
    private void loadData() {
        refrigeratorsData = FXCollections.observableArrayList();
        refrigeratorsData.add(new Refrigerator("Samsung", "Белый", 185.0, 60.0, 65.0));
        refrigeratorsData.add(new Refrigerator("LG", "Серебристый", 200.0, 70.0, 70.0));
        refrigeratorsData.add(new Refrigerator("Bosch", "Белый", 180.0, 60.0, 62.0));
    }

    /**
     * Копирование данных из таблицы в поля редактирования
     */
    private void showRefrigerator(Refrigerator ref) {
        edManufacturer.setText(ref.getManufacturer());
        cbColor.setValue(ref.getColor());
        edHeight.setText(String.valueOf(ref.getHeight()));
        edWidth.setText(String.valueOf(ref.getWidth()));
        edDepth.setText(String.valueOf(ref.getDepth()));
    }

    /**
     * Очистка полей редактирования
     */
    private void clearFields() {
        edManufacturer.clear();
        cbColor.setValue(null);
        edHeight.clear();
        edWidth.clear();
        edDepth.clear();
    }

    /**
     * Валидация полей ввода
     */
    private boolean validateFields() {
        if (edManufacturer.getText().isEmpty() ||
            cbColor.getValue() == null ||
            edHeight.getText().isEmpty() ||
            edWidth.getText().isEmpty() ||
            edDepth.getText().isEmpty()) {

            Dialogs.showDialog("Предупреждение",
                    "Не заполнены все поля холодильника",
                    Alert.AlertType.WARNING);
            return false;
        }

        try {
            Double.parseDouble(edHeight.getText());
            Double.parseDouble(edWidth.getText());
            Double.parseDouble(edDepth.getText());
        } catch (NumberFormatException e) {
            Dialogs.showDialog("Ошибка",
                    "Размеры должны быть числами",
                    Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    /**
     * Добавление нового холодильника
     */
    @FXML
    private void handleNew() {
        if (!validateFields()) {
            return;
        }

        Refrigerator ref = new Refrigerator();
        ref.setManufacturer(edManufacturer.getText());
        ref.setColor(cbColor.getValue());
        ref.setHeight(Double.parseDouble(edHeight.getText()));
        ref.setWidth(Double.parseDouble(edWidth.getText()));
        ref.setDepth(Double.parseDouble(edDepth.getText()));

        refrigeratorsData.add(ref);
        refrigeratorsTable.getSelectionModel().selectLast();

        clearFields();

        Dialogs.showDialog("Успех",
                "Холодильник успешно добавлен",
                Alert.AlertType.INFORMATION);
    }

    /**
     * Редактирование выбранного холодильника
     */
    @FXML
    private void handleEdit() {
        Refrigerator selected = refrigeratorsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Dialogs.showDialog("Предупреждение",
                    "Выберите холодильник для редактирования",
                    Alert.AlertType.WARNING);
            return;
        }

        if (!validateFields()) {
            return;
        }

        selected.setManufacturer(edManufacturer.getText());
        selected.setColor(cbColor.getValue());
        selected.setHeight(Double.parseDouble(edHeight.getText()));
        selected.setWidth(Double.parseDouble(edWidth.getText()));
        selected.setDepth(Double.parseDouble(edDepth.getText()));

        refrigeratorsTable.refresh();

        // Не очищаем поля после редактирования, чтобы пользователь видел сохраненные данные
        // clearFields();
    }

    /**
     * Удаление выбранного холодильника
     */
    @FXML
    private void handleDel() {
        int selectedIndex = refrigeratorsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            if (!Dialogs.showConfirmDialog("Удалить выбранный холодильник?")) {
                return;
            }
            refrigeratorsTable.getItems().remove(selectedIndex);
            clearFields();

            Dialogs.showDialog("Успех",
                    "Холодильник успешно удален",
                    Alert.AlertType.INFORMATION);
        } else {
            Dialogs.showDialog("Предупреждение",
                    "Выберите холодильник для удаления",
                    Alert.AlertType.WARNING);
        }
    }

    /**
     * Очистка полей ввода
     */
    @FXML
    private void handleClear() {
        clearFields();
        refrigeratorsTable.getSelectionModel().clearSelection();
    }
}
