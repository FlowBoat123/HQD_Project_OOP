package database;

import logic.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Exception.UsernameAlreadyExistsException;

import javax.sql.DataSource;

/**
 * This class provides methods to interact with the 'users' table in the database.
 * It allows adding, deleting, updating, and retrieving user information.
 */
public class UserDAO implements DAO<User> {

    private DataSource dataSource;

    /**
     * Constructor to initialize the DataSource.
     */
    public UserDAO() {
        this.dataSource = DataSourceFactory.getDataSource();
    }

    /**
     * Adds a new user to the 'users' table in the database.
     *
     * @param user The User object to be added.
     */
    @Override
    public void add(User user) {
        String sqlCheck = "SELECT COUNT(*) FROM users WHERE username = ?";
        String sqlInsert = "INSERT INTO users (username, password, creation_time, bio, email, website, details, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            pstmtInsert.setString(3, dateFormat.format(user.getCreationTime()));
            pstmtInsert.setString(4, user.getBio());
            pstmtInsert.setString(5, user.getEmail());
            pstmtInsert.setString(6, user.getWebsite());
            pstmtInsert.setString(7, user.getDetails());
            pstmtInsert.setString(8, user.getAvatar()); // Assuming avatar is a String now
            pstmtInsert.executeUpdate();
        } catch (UsernameAlreadyExistsException e) {
            // Message when username already exists
            System.out.println("Note: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a username already exists in the 'users' table.
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
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

    /**
     * Deletes a user from the 'users' table.
     *
     * @param user The User object to be deleted.
     */
    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // username is unique
            pstmt.setString(1, user.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the information of an existing user in the 'users' table.
     *
     * @param user The User object with updated information.
     */
    @Override
    public void update(User user) {
        System.out.println(user.getID());
        String sql = "UPDATE users SET password = ?, creation_time = ?, bio = ?, email = ?, website = ?, details = ?, avatar = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Update user info in users table
            pstmt.setString(1, user.getPassword());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setString(2, dateFormat.format(user.getCreationTime()));
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

    /**
     * Retrieves all users from the 'users' table.
     *
     * @return An ArrayList of User objects.
     */
    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Show all user information
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (rs.getString("creation_time") != null) {
                    user.setCreationTime(dateFormat.parse(rs.getString("creation_time").split(" ")[0]));
                }
                user.setBio(rs.getString("bio"));
                user.setEmail(rs.getString("email"));
                user.setWebsite(rs.getString("website"));
                user.setDetails(rs.getString("details"));
                user.setAvatar(rs.getString("avatar"));
                user.setID(rs.getInt("id"));
                System.out.println(user);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}