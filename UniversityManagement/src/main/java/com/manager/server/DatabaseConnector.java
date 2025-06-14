package com.manager.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    private static final String DB_URL = "jdbc:sqlite:university_management.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String sqlStudent = "CREATE TABLE IF NOT EXISTS students ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " first_name TEXT NOT NULL,"
                + " last_name TEXT NOT NULL,"
                + " email TEXT,"
                + " major TEXT,"
                + " graduation_end DATE"
                + ");";

        String sqlLecturer = "CREATE TABLE IF NOT EXISTS lecturers ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " first_name TEXT NOT NULL,"
                + " last_name TEXT NOT NULL,"
                + " email TEXT,"
                + " position TEXT,"
                + " academic_rank TEXT"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlStudent);
            stmt.execute(sqlLecturer);
            System.out.println("Database has been initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Critical error during database initialization: " + e.getMessage());
            System.exit(1);
        }
    }
}