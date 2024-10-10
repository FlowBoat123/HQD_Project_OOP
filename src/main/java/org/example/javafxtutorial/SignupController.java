package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

        if (insertUser(username, password)) {
            signupStatusLabel.setText("Sign-up successful! :3");
        } else {
            signupStatusLabel.setText("Sign-up failed! Username may already existed.");
        }

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
