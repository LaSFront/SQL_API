package ru.netology.api.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void cleanDB() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
        connection.close();
    }

    @SneakyThrows
    public static String getVerifyData() {
        var codeSQLQuery = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var connection = getConn();
        return runner.query(connection, codeSQLQuery, new ScalarHandler<>());

    }

    @SneakyThrows
    public static List<CardInfo> getCards() {
        var codeSQLQuery = "SELECT id, number, balance_in_kopecks as balance FROM cards ORDER BY id";
        var connection = getConn();
        return runner.query(connection, codeSQLQuery, new BeanListHandler<>(CardInfo.class));
    }
}
