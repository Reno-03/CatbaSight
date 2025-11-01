package com.example.catbasightapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String DATABASE_NAME = "catbasight_db";
        String DATABASE_USERNAME = "root";
        String DATABASE_PASSWORD = "123456";

        String DATABASE_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}