package org.example.javafxtutorial;

import javafx.concurrent.Task;

import java.sql.*;
import java.time.LocalDateTime;

public class InsertUserTask extends Task<Boolean> {
    private User user;
    private int generatedId;

    public InsertUserTask(String username, String password, LocalDateTime creationTime, String bio, String email,
                          String website) {
        this.user = new User(username, password, creationTime, bio, email, website, 0, 0, 0);
    }

    @Override
    protected Boolean call() {
        String query = "INSERT INTO users (username, password, creation_time, bio, email, website) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setTimestamp(3, Timestamp.valueOf(user.getCreationTime()));
            stmt.setString(4, user.getBio());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getWebsite());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        user.setID(generatedId);
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public int getGeneratedId() {
        return generatedId;
    }
}
