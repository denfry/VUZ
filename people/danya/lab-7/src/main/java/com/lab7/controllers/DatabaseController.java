package com.lab7.controllers;

import com.lab7.db.DatabaseManager;
import com.lab7.gui.Dialogs;
import com.lab7.model.AnalysisRecord;
import com.lab7.model.UserRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DatabaseController {

    @FXML private TextArea versionArea;
    @FXML private Label summaryLabel;

    @FXML private TableView<UserRecord> usersTable;
    @FXML private TableColumn<UserRecord, Integer> idColumn;
    @FXML private TableColumn<UserRecord, String> emailColumn;
    @FXML private TableColumn<UserRecord, String> roleColumn;
    @FXML private TableColumn<UserRecord, String> planColumn;
    @FXML private TableColumn<UserRecord, Integer> analysesColumn;
    @FXML private TableColumn<UserRecord, Boolean> activeColumn;
    @FXML private TableColumn<UserRecord, String> createdAtColumn;

    @FXML private TextField userSearchField;
    @FXML private TextField userEmailField;
    @FXML private ComboBox<String> userRoleCombo;
    @FXML private ComboBox<String> userPlanCombo;
    @FXML private TextField userTotalAnalysesField;
    @FXML private CheckBox userActiveCheck;

    @FXML private TableView<AnalysisRecord> analysesTable;
    @FXML private TableColumn<AnalysisRecord, Integer> analysisIdColumn;
    @FXML private TableColumn<AnalysisRecord, String> taskIdColumn;
    @FXML private TableColumn<AnalysisRecord, String> videoIdColumn;
    @FXML private TableColumn<AnalysisRecord, String> sourceTypeColumn;
    @FXML private TableColumn<AnalysisRecord, String> statusColumn;
    @FXML private TableColumn<AnalysisRecord, Double> confidenceColumn;
    @FXML private TableColumn<AnalysisRecord, Boolean> hasAdvertisingColumn;
    @FXML private TableColumn<AnalysisRecord, String> analysisCreatedAtColumn;

    @FXML private TextField analysisTaskIdField;
    @FXML private TextField analysisVideoIdField;
    @FXML private ComboBox<String> analysisSourceTypeCombo;
    @FXML private ComboBox<String> analysisStatusCombo;
    @FXML private TextField analysisConfidenceField;
    @FXML private CheckBox analysisHasAdvertisingCheck;

    @FXML private DatePicker reportDateFrom;
    @FXML private DatePicker reportDateTo;

    private final ObservableList<UserRecord> users = FXCollections.observableArrayList();
    private final ObservableList<AnalysisRecord> analyses = FXCollections.observableArrayList();

    private Stage dialogStage;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        planColumn.setCellValueFactory(new PropertyValueFactory<>("plan"));
        analysesColumn.setCellValueFactory(new PropertyValueFactory<>("totalAnalyses"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        usersTable.setItems(users);

        analysisIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        videoIdColumn.setCellValueFactory(new PropertyValueFactory<>("videoId"));
        sourceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sourceType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        confidenceColumn.setCellValueFactory(new PropertyValueFactory<>("confidenceScore"));
        hasAdvertisingColumn.setCellValueFactory(new PropertyValueFactory<>("hasAdvertising"));
        analysisCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        analysesTable.setItems(analyses);

        userRoleCombo.setItems(FXCollections.observableArrayList("user", "admin"));
        userPlanCombo.setItems(FXCollections.observableArrayList("free", "starter", "pro", "business", "enterprise"));
        analysisSourceTypeCombo.setItems(FXCollections.observableArrayList("file", "url", "youtube", "telegram", "instagram", "tiktok", "vk"));
        analysisStatusCombo.setItems(FXCollections.observableArrayList("pending", "queued", "processing", "completed", "failed"));

        clearUserForm();
        clearAnalysisForm();
        reportDateFrom.setValue(LocalDate.now().minusMonths(1));
        reportDateTo.setValue(LocalDate.now());

        handleGetVersion();
        handleRefreshData();

        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                clearUserForm();
                analyses.clear();
                clearAnalysisForm();
            } else {
                fillUserForm(newVal);
                loadAnalysesForUser(newVal.getId());
            }
        });

        analysesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                clearAnalysisForm();
            } else {
                fillAnalysisForm(newVal);
            }
        });
    }

    public void initialize(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleGetVersion() {
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             java.sql.ResultSet rs = stmt.executeQuery("SELECT H2VERSION()")) {
            if (rs.next()) {
                versionArea.setText("H2 " + rs.getString(1));
            }
        } catch (SQLException e) {
            Dialogs.showError("Ошибка", "Не удалось получить версию БД: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleRefreshData() {
        try {
            users.setAll(DatabaseManager.getUsers(userSearchField.getText()));
            if (!users.isEmpty() && usersTable.getSelectionModel().getSelectedItem() == null) {
                usersTable.getSelectionModel().selectFirst();
            }
            loadSummary();
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
            Dialogs.showError("Ошибка", "Не удалось загрузить пользователей: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleSearchUsers() {
        handleRefreshData();
    }

    @FXML
    private void handleAddUser() {
        try {
            String email = required(userEmailField.getText(), "Введите email пользователя");
            String role = required(userRoleCombo.getValue(), "Выберите роль");
            String plan = required(userPlanCombo.getValue(), "Выберите тариф");
            int total = parseNonNegativeInt(userTotalAnalysesField.getText(), "Количество анализов должно быть неотрицательным числом");
            boolean active = userActiveCheck.isSelected();

            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                int id = DatabaseManager.insertUser(email, plan, role, total, active);
                con.commit();
                Dialogs.showInfo("Успех", "Пользователь добавлен, ID = " + id, dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            handleRefreshData();
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleUpdateUser() {
        UserRecord selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Dialogs.showError("Ошибка", "Выберите пользователя в таблице", dialogStage);
            return;
        }

        try {
            String email = required(userEmailField.getText(), "Введите email пользователя");
            String role = required(userRoleCombo.getValue(), "Выберите роль");
            String plan = required(userPlanCombo.getValue(), "Выберите тариф");
            int total = parseNonNegativeInt(userTotalAnalysesField.getText(), "Количество анализов должно быть неотрицательным числом");
            boolean active = userActiveCheck.isSelected();

            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                DatabaseManager.updateUser(selected.getId(), email, plan, role, total, active);
                con.commit();
                Dialogs.showInfo("Успех", "Пользователь обновлён", dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            handleRefreshData();
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleDeleteUser() {
        UserRecord selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Dialogs.showError("Ошибка", "Выберите пользователя в таблице", dialogStage);
            return;
        }

        try {
            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                DatabaseManager.deleteUser(selected.getId());
                con.commit();
                Dialogs.showInfo("Успех", "Пользователь удалён", dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            handleRefreshData();
            analyses.clear();
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleAddAnalysis() {
        UserRecord user = usersTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            Dialogs.showError("Ошибка", "Сначала выберите пользователя (главная таблица)", dialogStage);
            return;
        }

        try {
            String taskId = required(analysisTaskIdField.getText(), "Введите task_id");
            String videoId = required(analysisVideoIdField.getText(), "Введите video_id");
            String sourceType = required(analysisSourceTypeCombo.getValue(), "Выберите источник");
            String status = required(analysisStatusCombo.getValue(), "Выберите статус");
            double confidence = parseDouble(analysisConfidenceField.getText(), "Точность должна быть числом");
            boolean hasAdvertising = analysisHasAdvertisingCheck.isSelected();

            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                int id = DatabaseManager.insertAnalysis(user.getId(), taskId, videoId, sourceType, status, confidence, hasAdvertising);
                con.commit();
                Dialogs.showInfo("Успех", "Аналитика добавлена, ID = " + id, dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            loadAnalysesForUser(user.getId());
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleUpdateAnalysis() {
        UserRecord user = usersTable.getSelectionModel().getSelectedItem();
        AnalysisRecord analysis = analysesTable.getSelectionModel().getSelectedItem();
        if (user == null || analysis == null) {
            Dialogs.showError("Ошибка", "Выберите запись аналитики в подчинённой таблице", dialogStage);
            return;
        }

        try {
            String taskId = required(analysisTaskIdField.getText(), "Введите task_id");
            String videoId = required(analysisVideoIdField.getText(), "Введите video_id");
            String sourceType = required(analysisSourceTypeCombo.getValue(), "Выберите источник");
            String status = required(analysisStatusCombo.getValue(), "Выберите статус");
            double confidence = parseDouble(analysisConfidenceField.getText(), "Точность должна быть числом");
            boolean hasAdvertising = analysisHasAdvertisingCheck.isSelected();

            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                DatabaseManager.updateAnalysis(analysis.getId(), taskId, videoId, sourceType, status, confidence, hasAdvertising);
                con.commit();
                Dialogs.showInfo("Успех", "Аналитика обновлена", dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            loadAnalysesForUser(user.getId());
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleDeleteAnalysis() {
        UserRecord user = usersTable.getSelectionModel().getSelectedItem();
        AnalysisRecord analysis = analysesTable.getSelectionModel().getSelectedItem();
        if (user == null || analysis == null) {
            Dialogs.showError("Ошибка", "Выберите запись аналитики в подчинённой таблице", dialogStage);
            return;
        }

        try {
            Connection con = DatabaseManager.getConnection();
            con.setAutoCommit(false);
            try {
                DatabaseManager.deleteAnalysis(analysis.getId());
                con.commit();
                Dialogs.showInfo("Успех", "Аналитика удалена", dialogStage);
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

            loadAnalysesForUser(user.getId());
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleGenerateReport() {
        try {
            LocalDate from = reportDateFrom.getValue();
            LocalDate to = reportDateTo.getValue();
            if (from == null || to == null) {
                Dialogs.showError("Ошибка", "Укажите период отчёта", dialogStage);
                return;
            }
            if (from.isAfter(to)) {
                Dialogs.showError("Ошибка", "Дата 'с' не может быть позже даты 'по'", dialogStage);
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("DATE_FROM", Date.valueOf(from));
            params.put("DATE_TO", Date.valueOf(to));

            Path outDir = Paths.get("target", "reports");
            Files.createDirectories(outDir);
            String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path out = outDir.resolve("lab11_analyses_" + stamp + ".pdf");

            try (Connection con = DatabaseManager.getConnection();
                 InputStream jrxml = getClass().getResourceAsStream("/com/lab7/reports/analyses_report.jrxml")) {
                if (jrxml == null) {
                    throw new IllegalStateException("Шаблон отчёта не найден: /com/lab7/reports/analyses_report.jrxml");
                }
                JasperReport report = JasperCompileManager.compileReport(jrxml);
                JasperPrint print = JasperFillManager.fillReport(report, params, con);
                JasperExportManager.exportReportToPdfFile(print, out.toString());
            }

            Dialogs.showInfo("Успех", "Отчёт сформирован: " + out.toAbsolutePath(), dialogStage);
        } catch (JRException | SQLException e) {
            Dialogs.showError("Ошибка отчёта", e.getMessage(), dialogStage);
        } catch (Exception e) {
            Dialogs.showError("Ошибка", e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleCreateDemoTable() {
        try {
            DatabaseManager.ensureSchemaAndSeed();
            Dialogs.showInfo("Успех", "База и тестовые данные инициализированы", dialogStage);
            handleRefreshData();
        } catch (SQLException e) {
            Dialogs.showError("Ошибка", "Не удалось инициализировать данные: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private void loadSummary() {
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             java.sql.ResultSet rs = stmt.executeQuery("""
                     SELECT
                         (SELECT COUNT(*) FROM users) AS users_count,
                         (SELECT COUNT(*) FROM analyses) AS analyses_count
                     """)) {
            if (rs.next()) {
                summaryLabel.setText(String.format("users: %d | analyses: %d", rs.getInt("users_count"), rs.getInt("analyses_count")));
            }
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
        }
    }

    private void loadAnalysesForUser(int userId) {
        try {
            analyses.setAll(DatabaseManager.getAnalysesByUser(userId));
        } catch (SQLException e) {
            analyses.clear();
            Dialogs.showError("Ошибка", "Не удалось загрузить аналитику: " + e.getMessage(), dialogStage);
        }
    }

    private void fillUserForm(UserRecord user) {
        userEmailField.setText(user.getEmail());
        userRoleCombo.getSelectionModel().select(user.getRole());
        userPlanCombo.getSelectionModel().select(user.getPlan());
        userTotalAnalysesField.setText(String.valueOf(user.getTotalAnalyses()));
        userActiveCheck.setSelected(user.isActive());
    }

    private void clearUserForm() {
        userEmailField.clear();
        userRoleCombo.getSelectionModel().select("user");
        userPlanCombo.getSelectionModel().select("free");
        userTotalAnalysesField.setText("0");
        userActiveCheck.setSelected(true);
    }

    private void fillAnalysisForm(AnalysisRecord analysis) {
        analysisTaskIdField.setText(analysis.getTaskId());
        analysisVideoIdField.setText(analysis.getVideoId());
        analysisSourceTypeCombo.getSelectionModel().select(analysis.getSourceType());
        analysisStatusCombo.getSelectionModel().select(analysis.getStatus());
        analysisConfidenceField.setText(String.valueOf(analysis.getConfidenceScore()));
        analysisHasAdvertisingCheck.setSelected(analysis.isHasAdvertising());
    }

    private void clearAnalysisForm() {
        analysisTaskIdField.clear();
        analysisVideoIdField.clear();
        analysisSourceTypeCombo.getSelectionModel().select("youtube");
        analysisStatusCombo.getSelectionModel().select("pending");
        analysisConfidenceField.setText("0.0");
        analysisHasAdvertisingCheck.setSelected(false);
    }

    private static String required(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static int parseNonNegativeInt(String value, String message) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 0) {
                throw new IllegalArgumentException(message);
            }
            return parsed;
        } catch (Exception e) {
            throw new IllegalArgumentException(message);
        }
    }

    private static double parseDouble(String value, String message) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(message);
        }
    }
}