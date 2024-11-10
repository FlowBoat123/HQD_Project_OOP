package org.example.javafxtutorial;

import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class InsertUserTask extends Task<Boolean> {
    private User user;

    public InsertUserTask(String username, String password, LocalDateTime creationTime, String bio, String email,
                          String website) {
        this.user = new User(username, password, creationTime, bio, email, website, 0, 0, 0);
    }

    @Override
    protected Boolean call() {
        String query = "INSERT INTO users (username, password, creation_time, bio, email, website) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setTimestamp(3, Timestamp.valueOf(user.getCreationTime()));
            stmt.setString(4, user.getBio());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getWebsite());
            return stmt.executeUpdate() > 0; // return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
