package database;

import logic.User;
import org.example.javafxtutorial.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

import Exception.UsernameAlreadyExistsException;

public class UserDAO implements DAO<User> {

    @Override
    public void add(User user) {
        String sqlCheck = "SELECT COUNT(*) FROM users WHERE username = ?";
        String sqlInsert = "INSERT INTO users (username, password, creation_time, bio, email, website, details, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

            // Check if username already exists
            pstmtCheck.setString(1, user.getUsername());
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new UsernameAlreadyExistsException("Username already exists.");
                }
            }

            // Insert new user if username does not exist
            pstmtInsert.setString(1, user.getUsername());
            pstmtInsert.setString(2, user.getPassword());
            pstmtInsert.setTimestamp(3, Timestamp.valueOf(user.getCreationTime()));
            pstmtInsert.setString(4, user.getBio());
            pstmtInsert.setString(5, user.getEmail());
            pstmtInsert.setString(6, user.getWebsite());
            pstmtInsert.setString(7, user.getDetails());
            pstmtInsert.setString(8, user.getAvatar()); // Assuming avatar is a String now
            pstmtInsert.executeUpdate();
        } catch (UsernameAlreadyExistsException e) {
            // Handle the exception here
            System.out.println("Note: " + e.getMessage());
            // Optionally, you can store this status in the User object or another variable if needed
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Username exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Username does not exist
    }



    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        System.out.println(user.getID());
        String sql = "UPDATE users SET password = ?, creation_time = ?, bio = ?, email = ?, website = ?, details = ?, avatar = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getPassword());
            pstmt.setTimestamp(2, Timestamp.valueOf(user.getCreationTime()));
            pstmt.setString(3, user.getBio());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getWebsite());
            pstmt.setString(6, user.getDetails());
            pstmt.setString(7, user.getAvatar());
            pstmt.setInt(8, user.getID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreationTime(rs.getTimestamp("creation_time").toLocalDateTime());
                user.setBio(rs.getString("bio"));
                user.setEmail(rs.getString("email"));
                user.setWebsite(rs.getString("website"));
                user.setDetails(rs.getString("details"));
                user.setAvatar(rs.getString("avatar"));
                user.setID(rs.getInt("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
