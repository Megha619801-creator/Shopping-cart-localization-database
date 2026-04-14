package com.example.cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;


public class DatabaseConnection {

    // Fix #1: Private constructor to prevent instantiation
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");

        return DriverManager.getConnection(url, user, pass);
    }


    }
