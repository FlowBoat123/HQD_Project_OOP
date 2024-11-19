package org.example.javafxtutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql-3417d26-vnu-7971.b.aivencloud.com:11871/library_management?useSSL=true";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_KwvYFp4CEP2D-pRqfYd";

    // Static initializer for loading the driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to create the database connection.");
        }
        return connection;
    }
}
