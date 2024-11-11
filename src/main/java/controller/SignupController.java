package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.javafxtutorial.DatabaseConnection;
import org.example.javafxtutorial.InsertUserTask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SignupController {
    @FXML
    private TextField signupUsernameField;

    @FXML
    public PasswordField signupPasswordField;

    @FXML
    public Label signupStatusLabel;

    @FXML
    public PasswordField signupConfirmPasswordField;

    @FXML
    private UserFormController userFormController;

    @FXML
    private ProgressIndicator loadingIndicator;

    public void setUserFormController(UserFormController userFormController) {
        this.userFormController = userFormController;
    }

    public void handleSignUp(ActionEvent actionEvent) {
        String username = signupUsernameField.getText();
        String password = signupPasswordField.getText();
        String confirmPassword = signupConfirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            signupStatusLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            signupStatusLabel.setText("Password do not match!");
            return;
        }

        // Call the method to run the insertion task
        runInsertUserTask(username, password, new Timestamp(System.currentTimeMillis()).toLocalDateTime(), "", "", "");
    }

    public void runInsertUserTask(String username, String password, LocalDateTime creationTime, String bio, String email, String website) {
        // Create the task
        InsertUserTask task = new InsertUserTask(username, password, creationTime,
                bio, email, website);

        task.setOnRunning(event -> loadingIndicator.setVisible(true)); // progress bar
        // Handle task completion and result
        task.setOnSucceeded(event -> {
            loadingIndicator.setVisible(false);
            Boolean result = task.getValue();
            if (result) {
                int userId = task.getGeneratedId();
                System.out.println("User inserted successfully with ID: " + userId);
                signupStatusLabel.setText("User inserted successfully with ID: " + userId);
                // Optionally update UI here (e.g., show success message)
                if (userFormController != null) {
                    userFormController.refreshTable();
                }
            } else {
                System.out.println("User insertion failed.");
                signupStatusLabel.setText("User insertion failed. Username already exists");
                // Optionally update UI here (e.g., show error message)
            }
        });

        task.setOnFailed(event -> {
            loadingIndicator.setVisible(false);
            System.out.println("Task failed.");
            signupStatusLabel.setText("Task failed.");
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
