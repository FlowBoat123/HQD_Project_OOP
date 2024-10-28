package org.example.javafxtutorial;

import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserTask extends Task<Boolean> {
    private final String username;
    private final String password;

    public InsertUserTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Boolean call() {
        String query = "INSERT INTO users (username, password) VALUE (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                return stmt.executeUpdate() > 0; // Return true if a row was inserted
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
