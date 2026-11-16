package com.prizz.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * [DATABASE] Singleton Database Connection Manager.
 * Purpose: Manages relational database connection lifecycle.
 * Syllabus: JDBC DriverManager, Connection management, Singleton pattern.
 */
public class DBConnectionManager {
    private static volatile DBConnectionManager instance;
    private static final String DB_URL = "jdbc:sqlite:prizz_database.db";

    private DBConnectionManager() {
        // Driver initialization
    }

    public static DBConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DBConnectionManager.class) {
                if (instance == null) {
                    instance = new DBConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            // Graceful fallback for environments without explicit driver jar
            return DriverManager.getConnection("jdbc:default:connection");
        }
    }
}

