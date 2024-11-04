package org.example.javafxtutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql-3417d26-vnu-7971.b.aivencloud.com:11871/library_management?useSSL=true"; // DB URL
    private static final String USER = "avnadmin"; // user name db
    private static final String PASSWORD = "AVNS_KwvYFp4CEP2D-pRqfYd"; // pass word db

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
