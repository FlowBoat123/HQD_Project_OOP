package database;

import logic.User;
import org.example.javafxtutorial.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserDAO implements DAO<User> {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void add(User user) {
        readWriteLock.writeLock().lock();
        String sql = "INSERT INTO users (username, password, creation_time, bio, email, website, details, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        for (int attempt = 1; attempt <= 5; attempt++) {
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
                break; // Exit the loop if successful
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) { // MySQL SQLState for deadlock
                    try {
                        Thread.sleep(100 * attempt);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

    @Override
    public void delete(User user) {
        readWriteLock.writeLock().lock();
        String sql = "DELETE FROM users WHERE username = ?";
        for (int attempt = 1; attempt <= 5; attempt++) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, user.getUsername());
                pstmt.executeUpdate();
                break; // Exit the loop if successful
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) {
                    try {
                        Thread.sleep(100 * attempt);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

    @Override
    public void update(User user) {
        readWriteLock.writeLock().lock();
        String sql = "UPDATE users SET password = ?, creation_time = ?, bio = ?, email = ?, website = ?, details = ?, avatar = ? WHERE id = ?";
        for (int attempt = 1; attempt <= 5; attempt++) {
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
                break; // Exit the loop if successful
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) {
                    try {
                        Thread.sleep(100 * attempt);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

    @Override
    public ArrayList<User> getAll() {
        readWriteLock.readLock().lock();
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
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return userList;
    }
}
