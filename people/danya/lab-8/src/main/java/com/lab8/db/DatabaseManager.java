package com.lab8.db;

import com.lab8.model.UserDirectoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DatabaseManager {
    private static Connection connection;

    public static void connect(String url, String user, String password) throws SQLException {
        disconnect();
        connection = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() throws SQLException {
        if (!isConnected()) {
            throw new SQLException("Соединение с базой данных не установлено.");
        }
        return connection;
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    public static ObservableList<UserDirectoryItem> loadUsers() throws SQLException {
        ObservableList<UserDirectoryItem> users = FXCollections.observableArrayList();
        String sql = """
                SELECT id,
                       email,
                       role::text AS role,
                       plan::text AS plan,
                       total_analyses,
                       is_active,
                       created_at
                FROM users
                ORDER BY id
                """;

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LocalDateTime createdAt = null;
                if (rs.getTimestamp("created_at") != null) {
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                }

                users.add(new UserDirectoryItem(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("plan"),
                        rs.getInt("total_analyses"),
                        rs.getBoolean("is_active"),
                        createdAt
                ));
            }
        } catch (SQLException e) {
            rollback();
            throw e;
        }

        return users;
    }

    public static String getDatabaseProductName() {
        if (!isConnected()) {
            return null;
        }

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData != null ? metaData.getDatabaseProductName() : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getDatabaseName() {
        if (!isConnected()) {
            return null;
        }

        try {
            String catalog = connection.getCatalog();
            return (catalog == null || catalog.isBlank()) ? null : catalog;
        } catch (SQLException e) {
            return null;
        }
    }

    private static void rollback() {
        try {
            if (connection != null && !connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException ignored) {
        }
    }
}

