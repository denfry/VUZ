package com.lab7.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
