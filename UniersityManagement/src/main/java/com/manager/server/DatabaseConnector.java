package com.manager.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector{
private static String db_url = "jdbc:mysql://127.0.0.1:3306/?user=root";
private static String user = "Admin";
    private static String password = "Q1_r$8k";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Помилка: MySQL JDBC Драйвер не знайдено.");
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found", e);
        }

        return DriverManager.getConnection(db_url, user, password);
    }
}