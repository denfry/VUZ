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
    private TableColumn<UserRecord, String> usernameColumn;
    @FXML
    private TableColumn<UserRecord, String> riskColumn;
    @FXML
    private TableColumn<UserRecord, String> balanceColumn;
    @FXML
    private TableColumn<UserRecord, Integer> openOrdersColumn;
    @FXML
    private TableColumn<UserRecord, Boolean> activeColumn;
    @FXML
    private TableColumn<UserRecord, String> createdAtColumn;

    private final ObservableList<UserRecord> data = FXCollections.observableArrayList();
    private Stage dialogStage;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        riskColumn.setCellValueFactory(new PropertyValueFactory<>("riskProfile"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        openOrdersColumn.setCellValueFactory(new PropertyValueFactory<>("openOrders"));
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
                     SELECT u.id,
                            u.username,
                            COALESCE(r.title, u.risk_profile) AS risk_profile,
                            TO_CHAR(u.balance, 'FM9999999990.00') AS balance,
                            COALESCE(o.open_orders, 0) AS open_orders,
                            u.active,
                            TO_CHAR(u.created_at, 'YYYY-MM-DD HH24:MI') AS created_at
                     FROM bot_users u
                     LEFT JOIN ref_risk_profiles r ON r.code = u.risk_profile
                     LEFT JOIN (
                         SELECT user_id,
                                COUNT(*) FILTER (WHERE status IN ('NEW', 'PARTIALLY_FILLED')) AS open_orders
                         FROM orders
                         GROUP BY user_id
                     ) o ON o.user_id = u.id
                     ORDER BY u.id
                     """)) {
            while (rs.next()) {
                data.add(new UserRecord(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("risk_profile"),
                        rs.getString("balance"),
                        rs.getInt("open_orders"),
                        rs.getBoolean("active"),
                        rs.getString("created_at")
                ));
            }
            loadSummary();
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
            Dialogs.showError("Ошибка", "Не удалось загрузить пользователей VSC_Vitos: " + e.getMessage(), dialogStage);
        }
    }

    @FXML
    private void handleCreateDemoTable() {
        String createRiskProfilesSql = """
                CREATE TABLE IF NOT EXISTS ref_risk_profiles (
                    code VARCHAR(16) PRIMARY KEY,
                    title VARCHAR(64) NOT NULL,
                    max_daily_drawdown NUMERIC(6,2) NOT NULL
                )
                """;
        String createTickersSql = """
                CREATE TABLE IF NOT EXISTS ref_tickers (
                    ticker VARCHAR(16) PRIMARY KEY,
                    exchange VARCHAR(16) NOT NULL,
                    lot_size INTEGER NOT NULL DEFAULT 1,
                    is_active BOOLEAN NOT NULL DEFAULT TRUE
                )
                """;
        String createOperationTypesSql = """
                CREATE TABLE IF NOT EXISTS ref_operation_types (
                    code VARCHAR(24) PRIMARY KEY,
                    title VARCHAR(80) NOT NULL
                )
                """;
        String createBotUsersSql = """
                CREATE TABLE IF NOT EXISTS bot_users (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(64) UNIQUE NOT NULL,
                    risk_profile VARCHAR(16) NOT NULL,
                    balance NUMERIC(14,2) NOT NULL DEFAULT 0,
                    active BOOLEAN NOT NULL DEFAULT TRUE,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String createStrategiesSql = """
                CREATE TABLE IF NOT EXISTS trading_strategies (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER NOT NULL REFERENCES bot_users(id) ON DELETE CASCADE,
                    strategy_name VARCHAR(128) NOT NULL,
                    ticker VARCHAR(16) NOT NULL,
                    timeframe VARCHAR(16) NOT NULL,
                    is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    UNIQUE(user_id, strategy_name)
                )
                """;
        String createOrdersSql = """
                CREATE TABLE IF NOT EXISTS orders (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER NOT NULL REFERENCES bot_users(id) ON DELETE CASCADE,
                    strategy_id INTEGER REFERENCES trading_strategies(id) ON DELETE SET NULL,
                    ticker VARCHAR(16) NOT NULL,
                    side VARCHAR(8) NOT NULL,
                    order_type VARCHAR(16) NOT NULL,
                    quantity NUMERIC(16,4) NOT NULL,
                    price NUMERIC(16,4) NOT NULL,
                    status VARCHAR(24) NOT NULL,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;
        String createTradingOperationsSql = """
                CREATE TABLE IF NOT EXISTS trading_operations (
                    id SERIAL PRIMARY KEY,
                    user_id INTEGER NOT NULL REFERENCES bot_users(id) ON DELETE CASCADE,
                    order_id INTEGER REFERENCES orders(id) ON DELETE SET NULL,
                    operation_type_code VARCHAR(24) NOT NULL REFERENCES ref_operation_types(code),
                    amount NUMERIC(16,4) NOT NULL,
                    commission NUMERIC(16,4) NOT NULL DEFAULT 0,
                    note VARCHAR(255),
                    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;

        String insertRiskSql = """
                INSERT INTO ref_risk_profiles (code, title, max_daily_drawdown)
                VALUES (?, ?, ?)
                ON CONFLICT (code) DO NOTHING
                """;
        String insertTickerSql = """
                INSERT INTO ref_tickers (ticker, exchange, lot_size, is_active)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (ticker) DO NOTHING
                """;
        String insertOperationTypeSql = """
                INSERT INTO ref_operation_types (code, title)
                VALUES (?, ?)
                ON CONFLICT (code) DO NOTHING
                """;
        String insertUserSql = """
                INSERT INTO bot_users (username, risk_profile, balance, active)
                SELECT ?, ?, ?, ?
                WHERE NOT EXISTS (SELECT 1 FROM bot_users WHERE username = ?)
                """;
        String insertStrategySql = """
                INSERT INTO trading_strategies (user_id, strategy_name, ticker, timeframe, is_enabled)
                SELECT ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM trading_strategies WHERE user_id = ? AND strategy_name = ?
                )
                """;
        String insertOrderSql = """
                INSERT INTO orders (user_id, strategy_id, ticker, side, order_type, quantity, price, status)
                SELECT ?, ?, ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM orders
                    WHERE user_id = ? AND strategy_id = ? AND ticker = ? AND side = ? AND status = ?
                )
                """;
        String insertOperationSql = """
                INSERT INTO trading_operations (user_id, order_id, operation_type_code, amount, commission, note)
                SELECT ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM trading_operations
                    WHERE user_id = ? AND order_id = ? AND operation_type_code = ? AND note = ?
                )
                """;

        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            stmt.execute(createRiskProfilesSql);
            stmt.execute(createTickersSql);
            stmt.execute(createOperationTypesSql);
            stmt.execute(createBotUsersSql);
            stmt.execute(createStrategiesSql);
            stmt.execute(createOrdersSql);
            stmt.execute(createTradingOperationsSql);

            try (PreparedStatement riskPs = DatabaseManager.getConnection().prepareStatement(insertRiskSql)) {
                insertRiskProfile(riskPs, "aggressive", "Агрессивный", 8.50);
                insertRiskProfile(riskPs, "balanced", "Сбалансированный", 4.00);
                insertRiskProfile(riskPs, "conservative", "Консервативный", 2.50);
            }

            try (PreparedStatement tickerPs = DatabaseManager.getConnection().prepareStatement(insertTickerSql)) {
                insertTicker(tickerPs, "AAPL", "NASDAQ", 1, true);
                insertTicker(tickerPs, "MSFT", "NASDAQ", 1, true);
                insertTicker(tickerPs, "NVDA", "NASDAQ", 1, true);
            }

            try (PreparedStatement opTypePs = DatabaseManager.getConnection().prepareStatement(insertOperationTypeSql)) {
                insertOperationType(opTypePs, "ORDER_OPEN", "Открытие заявки");
                insertOperationType(opTypePs, "PARTIAL_FILL", "Частичное исполнение");
                insertOperationType(opTypePs, "FULL_FILL", "Полное исполнение");
            }

            try (PreparedStatement userPs = DatabaseManager.getConnection().prepareStatement(insertUserSql)) {
                insertUser(userPs, "vitos_admin", "aggressive", 250000.00, true);
                insertUser(userPs, "vitos_trader", "balanced", 120000.00, true);
                insertUser(userPs, "vitos_demo", "conservative", 50000.00, false);
            }

            int adminId = findUserIdByUsername("vitos_admin");
            int traderId = findUserIdByUsername("vitos_trader");

            try (PreparedStatement strategyPs = DatabaseManager.getConnection().prepareStatement(insertStrategySql)) {
                insertStrategy(strategyPs, adminId, "Breakout_US", "AAPL", "15m", true);
                insertStrategy(strategyPs, traderId, "MeanReversion", "MSFT", "1h", true);
                insertStrategy(strategyPs, traderId, "TrendFollow", "NVDA", "4h", false);
            }

            int breakoutId = findStrategyId(adminId, "Breakout_US");
            int meanRevId = findStrategyId(traderId, "MeanReversion");

            try (PreparedStatement orderPs = DatabaseManager.getConnection().prepareStatement(insertOrderSql)) {
                insertOrder(orderPs, adminId, breakoutId, "AAPL", "BUY", "LIMIT", 10.0, 182.50, "NEW");
                insertOrder(orderPs, adminId, breakoutId, "AAPL", "SELL", "LIMIT", 5.0, 186.30, "PARTIALLY_FILLED");
                insertOrder(orderPs, traderId, meanRevId, "MSFT", "BUY", "MARKET", 7.0, 421.10, "FILLED");
            }

            int orderNewId = findOrderId(adminId, "AAPL", "NEW");
            int orderPartialId = findOrderId(adminId, "AAPL", "PARTIALLY_FILLED");
            int orderFilledId = findOrderId(traderId, "MSFT", "FILLED");

            try (PreparedStatement operationPs = DatabaseManager.getConnection().prepareStatement(insertOperationSql)) {
                insertOperation(operationPs, adminId, orderNewId, "ORDER_OPEN", 1825.00, 0.35, "Выставлена лимитная заявка");
                insertOperation(operationPs, adminId, orderPartialId, "PARTIAL_FILL", 931.50, 0.24, "Исполнена часть объема");
                insertOperation(operationPs, traderId, orderFilledId, "FULL_FILL", 2947.70, 0.58, "Маркет-заявка исполнена полностью");
            }

            Dialogs.showInfo("Успех", "Схема VSC_Vitos, справочники и учет операций созданы и заполнены.", dialogStage);
            handleRefreshData();
        } catch (SQLException e) {
            Dialogs.showError("Ошибка", "Не удалось инициализировать схему VSC_Vitos: " + e.getMessage(), dialogStage);
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
                         (SELECT COUNT(*) FROM bot_users) AS users_count,
                         (SELECT COUNT(*) FROM trading_strategies) AS strategies_count,
                         (SELECT COUNT(*) FROM orders) AS orders_count,
                         (SELECT COUNT(*) FROM ref_tickers) AS dict_tickers,
                         (SELECT COUNT(*) FROM trading_operations) AS operations_count
                     """)) {
            if (rs.next()) {
                summaryLabel.setText(String.format(
                        "пользователи: %d | стратегии: %d | заявки: %d | тикеры (справ.): %d | операции: %d",
                        rs.getInt("users_count"),
                        rs.getInt("strategies_count"),
                        rs.getInt("orders_count"),
                        rs.getInt("dict_tickers"),
                        rs.getInt("operations_count")
                ));
            }
        } catch (SQLException e) {
            summaryLabel.setText("Сводка недоступна.");
        }
    }

    private void insertRiskProfile(PreparedStatement ps, String code, String title, double maxDrawdown) throws SQLException {
        ps.setString(1, code);
        ps.setString(2, title);
        ps.setDouble(3, maxDrawdown);
        ps.executeUpdate();
    }

    private void insertTicker(PreparedStatement ps, String ticker, String exchange, int lotSize, boolean active) throws SQLException {
        ps.setString(1, ticker);
        ps.setString(2, exchange);
        ps.setInt(3, lotSize);
        ps.setBoolean(4, active);
        ps.executeUpdate();
    }

    private void insertOperationType(PreparedStatement ps, String code, String title) throws SQLException {
        ps.setString(1, code);
        ps.setString(2, title);
        ps.executeUpdate();
    }

    private void insertUser(PreparedStatement ps, String username, String riskProfile, double balance, boolean active) throws SQLException {
        ps.setString(1, username);
        ps.setString(2, riskProfile);
        ps.setDouble(3, balance);
        ps.setBoolean(4, active);
        ps.setString(5, username);
        ps.executeUpdate();
    }

    private int findUserIdByUsername(String username) throws SQLException {
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("SELECT id FROM bot_users WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Пользователь не найден: " + username);
    }

    private void insertStrategy(PreparedStatement ps, int userId, String strategyName, String ticker, String timeframe, boolean enabled) throws SQLException {
        ps.setInt(1, userId);
        ps.setString(2, strategyName);
        ps.setString(3, ticker);
        ps.setString(4, timeframe);
        ps.setBoolean(5, enabled);
        ps.setInt(6, userId);
        ps.setString(7, strategyName);
        ps.executeUpdate();
    }

    private int findStrategyId(int userId, String strategyName) throws SQLException {
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(
                "SELECT id FROM trading_strategies WHERE user_id = ? AND strategy_name = ?")) {
            ps.setInt(1, userId);
            ps.setString(2, strategyName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Стратегия не найдена: " + strategyName);
    }

    private int findOrderId(int userId, String ticker, String status) throws SQLException {
        try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(
                """
                        SELECT id
                        FROM orders
                        WHERE user_id = ? AND ticker = ? AND status = ?
                        ORDER BY id DESC
                        LIMIT 1
                        """)) {
            ps.setInt(1, userId);
            ps.setString(2, ticker);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Заявка не найдена: " + ticker + " / " + status);
    }

    private void insertOrder(PreparedStatement ps, int userId, int strategyId, String ticker, String side, String orderType,
                             double quantity, double price, String status) throws SQLException {
        ps.setInt(1, userId);
        ps.setInt(2, strategyId);
        ps.setString(3, ticker);
        ps.setString(4, side);
        ps.setString(5, orderType);
        ps.setDouble(6, quantity);
        ps.setDouble(7, price);
        ps.setString(8, status);
        ps.setInt(9, userId);
        ps.setInt(10, strategyId);
        ps.setString(11, ticker);
        ps.setString(12, side);
        ps.setString(13, status);
        ps.executeUpdate();
    }

    private void insertOperation(PreparedStatement ps, int userId, int orderId, String operationTypeCode,
                                 double amount, double commission, String note) throws SQLException {
        ps.setInt(1, userId);
        ps.setInt(2, orderId);
        ps.setString(3, operationTypeCode);
        ps.setDouble(4, amount);
        ps.setDouble(5, commission);
        ps.setString(6, note);
        ps.setInt(7, userId);
        ps.setInt(8, orderId);
        ps.setString(9, operationTypeCode);
        ps.setString(10, note);
        ps.executeUpdate();
    }
}
