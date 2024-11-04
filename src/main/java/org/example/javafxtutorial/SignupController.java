package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController {
    @FXML
    private TextField signupUsernameField;

    @FXML
    public PasswordField signupPasswordField;

    @FXML
    public Label signupStatusLabel;

    @FXML
    public PasswordField signupConfirmPasswordField;

    public void handleSignUp(ActionEvent actionEvent) {
        String username = signupUsernameField.getText();
        String password = signupPasswordField.getText();
        String confirmPassword = signupConfirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            signupStatusLabel.setText("Password do not match!");
            return;
        }

        // Call the method to run the insertion task
        runInsertUserTask(username, password);

    }

    private boolean insertUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUE (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                return stmt.executeUpdate() > 0; // return true if a row was inserted
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void runInsertUserTask(String username, String password) {
        // Create the task
        InsertUserTask task = new InsertUserTask(username, password);

        // Handle task completion and result
        task.setOnSucceeded(event -> {
            Boolean result = task.getValue();
            if (result) {
                System.out.println("User inserted successfully.");
                // Optionally update UI here (e.g., show success message)
            } else {
                System.out.println("User insertion failed.");
                // Optionally update UI here (e.g., show error message)
            }
        });

        task.setOnFailed(event -> {
            System.out.println("Task failed.");
            // Optionally update UI here (e.g., show failure message)
        });

        // Run the task in a background thread
        new Thread(task).start();
    }

    public void handleBackToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupStatusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
