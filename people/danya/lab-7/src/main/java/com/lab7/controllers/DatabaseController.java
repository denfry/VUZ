package com.lab7.controllers;

import com.lab7.db.DatabaseManager;
import com.lab7.gui.Dialogs;
import com.lab7.model.UserRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {

    @FXML
    private TextArea versionArea;
    @FXML
    private Label summaryLabel;
    @FXML
    private TableView<UserRecord> usersTable;
    @FXML
    private TableColumn<UserRecord, Integer> idColumn;
    @FXML
    private TableColumn<UserRecord, String> emailColumn;
    @FXML
    private TableColumn<UserRecord, String> roleColumn;
    @FXML
    private TableColumn<UserRecord, String> planColumn;
    @FXML
    private TableColumn<UserRecord, Integer> analysesColumn;
    @FXML
    private TableColumn<UserRecord, Boolean> activeColumn;
    @FXML
    private TableColumn<UserRecord, String> createdAtColumn;

    private final ObservableList<UserRecord> data = FXCollections.observableArrayList();
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
        usersTable.setItems(data);

        handleGetVersion();
        handleRefreshData();
        loadSummary();
    }

    public void initialize(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleGetVersion() {
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT VERSION()")) {
            if (rs.next()) {
                versionArea.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            Dialogs.showError("Ошибка", "Не удалось получить версию: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleRefreshData() {
        data.clear();
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("""
                     SELECT id,
                            COALESCE(email, '(без email)') AS email,
                            role::text AS role,
                            plan::text AS plan,
                            total_analyses,
                            is_active,
                            TO_CHAR(created_at, 'YYYY-MM-DD HH24:MI') AS created_at
                     FROM users
                     ORDER BY id
                     """)) {
            while (rs.next()) {
                data.add(new UserRecord(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("plan"),
                        rs.getInt("total_analyses"),
                        rs.getBoolean("is_active"),
                        rs.getString("created_at")
                ));
            }
            loadSummary();
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
            Dialogs.showError("Ошибка", "Не удалось загрузить пользователей: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleCreateDemoTable() {
        String createUserPlanTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_plan') THEN
                        CREATE TYPE user_plan AS ENUM ('free', 'starter', 'pro', 'business', 'enterprise');
                    END IF;
                END $$;
                """;
        String createUserRoleTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role') THEN
                        CREATE TYPE user_role AS ENUM ('user', 'admin');
                    END IF;
                END $$;
                """;
        String createAnalysisStatusTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'analysis_status') THEN
                        CREATE TYPE analysis_status AS ENUM ('pending', 'queued', 'processing', 'completed', 'failed');
                    END IF;
                END $$;
                """;
        String createSourceTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'source_type') THEN
                        CREATE TYPE source_type AS ENUM ('file', 'url', 'youtube', 'telegram', 'instagram', 'tiktok', 'vk');
                    END IF;
                END $$;
                """;
        String createPaymentStatusTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_status') THEN
                        CREATE TYPE payment_status AS ENUM ('pending', 'succeeded', 'canceled', 'failed');
                    END IF;
                END $$;
                """;
        String createPaymentProviderTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_provider') THEN
                        CREATE TYPE payment_provider AS ENUM ('yookassa');
                    END IF;
                END $$;
                """;
        String createBrandCategoryTypeSql = """
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'brand_category') THEN
                        CREATE TYPE brand_category AS ENUM (
                            'bank', 'telecom', 'auto', 'food', 'beverage', 'clothing', 'technology',
                            'marketplace', 'bookmaker', 'energy', 'airline', 'retail', 'pharma',
                            'cosmetics', 'gaming', 'education', 'other'
                        );
                    END IF;
                END $$;
                """;
        String createUsersSql = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    email VARCHAR(255) UNIQUE,
                    plan user_plan NOT NULL DEFAULT 'free',
                    role user_role NOT NULL DEFAULT 'user',
                    total_analyses INTEGER NOT NULL DEFAULT 0,
                    is_active BOOLEAN NOT NULL DEFAULT TRUE,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String createAnalysesSql = """
                CREATE TABLE IF NOT EXISTS analyses (
                    id SERIAL PRIMARY KEY,
                    task_id VARCHAR(255) UNIQUE NOT NULL,
                    video_id VARCHAR(255) UNIQUE NOT NULL,
                    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    source_type source_type NOT NULL,
                    status analysis_status NOT NULL DEFAULT 'pending',
                    confidence_score DOUBLE PRECISION NOT NULL DEFAULT 0,
                    has_advertising BOOLEAN NOT NULL DEFAULT FALSE,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String createPaymentsSql = """
                CREATE TABLE IF NOT EXISTS payments (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    amount DOUBLE PRECISION NOT NULL,
                    currency VARCHAR(8) NOT NULL DEFAULT 'RUB',
                    status payment_status NOT NULL DEFAULT 'pending',
                    provider payment_provider NOT NULL DEFAULT 'yookassa',
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String createCustomBrandsSql = """
                CREATE TABLE IF NOT EXISTS custom_brands (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                    name VARCHAR(255) NOT NULL,
                    category brand_category NOT NULL DEFAULT 'other',
                    is_active BOOLEAN NOT NULL DEFAULT TRUE,
                    detection_threshold DOUBLE PRECISION NOT NULL DEFAULT 0.15,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String insertUserSql = """
                INSERT INTO users (email, plan, role, total_analyses, is_active)
                SELECT ?, CAST(? AS user_plan), CAST(? AS user_role), ?, ?
                WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = ?)
                """;
        String insertAnalysisSql = """
                INSERT INTO analyses (task_id, video_id, user_id, source_type, status, confidence_score, has_advertising)
                SELECT ?, ?, ?, CAST(? AS source_type), CAST(? AS analysis_status), ?, ?
                WHERE NOT EXISTS (SELECT 1 FROM analyses WHERE task_id = ?)
                """;
        String insertPaymentSql = """
                INSERT INTO payments (user_id, amount, status, provider)
                SELECT ?, ?, CAST(? AS payment_status), CAST(? AS payment_provider)
                WHERE NOT EXISTS (
                    SELECT 1 FROM payments WHERE user_id = ? AND amount = ?
                )
                """;
        String insertCustomBrandSql = """
                INSERT INTO custom_brands (user_id, name, category, is_active, detection_threshold)
                SELECT ?, ?, CAST(? AS brand_category), ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM custom_brands WHERE user_id = ? AND name = ?
                )
                """;

        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            stmt.execute(createUserPlanTypeSql);
            stmt.execute(createUserRoleTypeSql);
            stmt.execute(createAnalysisStatusTypeSql);
            stmt.execute(createSourceTypeSql);
            stmt.execute(createPaymentStatusTypeSql);
            stmt.execute(createPaymentProviderTypeSql);
            stmt.execute(createBrandCategoryTypeSql);
            stmt.execute(createUsersSql);
            stmt.execute(createAnalysesSql);
            stmt.execute(createPaymentsSql);
            stmt.execute(createCustomBrandsSql);

            try (PreparedStatement userPs = DatabaseManager.getConnection().prepareStatement(insertUserSql)) {
                insertUser(userPs, "admin@veritasad.ai", "enterprise", "admin", 24, true);
                insertUser(userPs, "analyst@veritasad.ai", "pro", "user", 11, true);
                insertUser(userPs, "demo@veritasad.ai", "starter", "user", 3, false);
            }

            int adminId = findUserIdByEmail("admin@veritasad.ai");
            int analystId = findUserIdByEmail("analyst@veritasad.ai");

            try (PreparedStatement analysisPs = DatabaseManager.getConnection().prepareStatement(insertAnalysisSql)) {
                insertAnalysis(analysisPs, "task-admin-001", "video-admin-001", adminId, "youtube", "completed", 0.97, true);
                insertAnalysis(analysisPs, "task-analyst-001", "video-analyst-001", analystId, "telegram", "processing", 0.61, true);
                insertAnalysis(analysisPs, "task-analyst-002", "video-analyst-002", analystId, "vk", "pending", 0.18, false);
            }

            try (PreparedStatement paymentPs = DatabaseManager.getConnection().prepareStatement(insertPaymentSql)) {
                insertPayment(paymentPs, adminId, 49990.00, "succeeded", "yookassa");
                insertPayment(paymentPs, analystId, 7990.00, "pending", "yookassa");
            }

            try (PreparedStatement brandPs = DatabaseManager.getConnection().prepareStatement(insertCustomBrandSql)) {
                insertCustomBrand(brandPs, adminId, "VeritasAI", "technology", true, 0.20);
                insertCustomBrand(brandPs, analystId, "AdWatch", "technology", true, 0.15);
            }

            Dialogs.showInfo("Успех", "Базовые таблицы созданы и заполнены демонстрационными данными.", dialogStage);
            handleRefreshData();
        } catch (SQLException e) {
            Dialogs.showError("Ошибка", "Не удалось инициализировать схему: " + e.getMessage(), dialogStage);
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
             ResultSet rs = stmt.executeQuery("""
                     SELECT
                         (SELECT COUNT(*) FROM users) AS users_count,
                         (SELECT COUNT(*) FROM analyses) AS analyses_count,
                         (SELECT COUNT(*) FROM payments) AS payments_count,
                         (SELECT COUNT(*) FROM custom_brands) AS brands_count
                     """)) {
            if (rs.next()) {
                summaryLabel.setText(String.format(
                        "users: %d | analyses: %d | payments: %d | custom_brands: %d",
                        rs.getInt("users_count"),
                        rs.getInt("analyses_count"),
                        rs.getInt("payments_count"),
                        rs.getInt("brands_count")
                ));
            }
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
        }
    }

    private void insertUser(PreparedStatement ps, String email, String plan, String role, int totalAnalyses, boolean active) throws SQLException {
        ps.setString(1, email);
        ps.setString(2, plan);
        ps.setString(3, role);
        ps.setInt(4, totalAnalyses);
        ps.setBoolean(5, active);
        ps.setString(6, email);
        ps.executeUpdate();
    }

    private int findUserIdByEmail(String email) throws SQLException {
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("SELECT id FROM users WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Пользователь не найден: " + email);
    }

    private void insertAnalysis(PreparedStatement ps, String taskId, String videoId, int userId, String sourceType, String status, double confidenceScore, boolean hasAdvertising) throws SQLException {
        ps.setString(1, taskId);
        ps.setString(2, videoId);
        ps.setInt(3, userId);
        ps.setString(4, sourceType);
        ps.setString(5, status);
        ps.setDouble(6, confidenceScore);
        ps.setBoolean(7, hasAdvertising);
        ps.setString(8, taskId);
        ps.executeUpdate();
    }

    private void insertPayment(PreparedStatement ps, int userId, double amount, String status, String provider) throws SQLException {
        ps.setInt(1, userId);
        ps.setDouble(2, amount);
        ps.setString(3, status);
        ps.setString(4, provider);
        ps.setInt(5, userId);
        ps.setDouble(6, amount);
        ps.executeUpdate();
    }

    private void insertCustomBrand(PreparedStatement ps, int userId, String name, String category, boolean active, double detectionThreshold) throws SQLException {
        ps.setInt(1, userId);
        ps.setString(2, name);
        ps.setString(3, category);
        ps.setBoolean(4, active);
        ps.setDouble(5, detectionThreshold);
        ps.setInt(6, userId);
        ps.setString(7, name);
        ps.executeUpdate();
    }
}
