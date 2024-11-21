//package database;
//
//import javafx.concurrent.Task;
//import logic.User;
//import org.example.javafxtutorial.DatabaseConnection;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//
//public class UserDatabaseTask extends Task<Boolean> {
//    public enum Operation {
//        INSERT, UPDATE
//    }
//
//    private User user;
//    private int generatedId;
//    private Operation operation;
//
//    public UserDatabaseTask(Operation operation, User user) {
//        this.operation = operation;
//        this.user = user;
//    }
//
//    @Override
//    protected Boolean call() {
//        if (operation == Operation.INSERT) {
//            return insertUser();
//        } else if (operation == Operation.UPDATE) {
//            return updateUser();
//        }
//        return false;
//    }
//
//    private Boolean insertUser() {
//        String query = "INSERT INTO users (username, password, creation_time, bio, email, website) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getPassword());
//            stmt.setTimestamp(3, Timestamp.valueOf(user.getCreationTime()));
//            stmt.setString(4, user.getBio());
//            stmt.setString(5, user.getEmail());
//            stmt.setString(6, user.getWebsite());
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected > 0) {
//                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        generatedId = generatedKeys.getInt(1);
//                        user.setID(generatedId);
//                        return true;
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//
//    private Boolean updateUser() {
//        String query = "UPDATE users SET username = ?, bio = ?, email = ?, website = ? WHERE id = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getBio());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getWebsite());
//            stmt.setInt(5, user.getID());
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected > 0) {
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public int getGeneratedId() {
//        return generatedId;
//    }
//}
