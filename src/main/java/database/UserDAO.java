package database;

import logic.User;
import org.example.javafxtutorial.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements DAO<User> {

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (username, password, creation_time, bio, email, website, details, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setTimestamp(3, Timestamp.valueOf(user.getCreationTime()));
            pstmt.setString(4, user.getBio());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getWebsite());
            pstmt.setString(7, user.getDetails());
            pstmt.setString(8, user.getAvatar()); // Assuming avatar is a String now
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
