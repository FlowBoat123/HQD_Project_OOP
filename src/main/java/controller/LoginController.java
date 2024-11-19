package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.javafxtutorial.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView logoImageView;

    public void initialize() {
//        logoImageView.setStyle("-fx-background-color: red;");
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
        logoImageView.setImage(image);
    }

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String username = usernameField.getText();
        char[] password = passwordField.getText().toCharArray();
        String passwordStr = new String(password);

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            statusLabel.setText("Failed to connect to the database");
            return;
        }

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordStr);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // UserDashBoard
                if (!username.equals("admin") && !password.equals("admin")) {
                    Parent userDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/javafxtutorial/UserDashboard.fxml")));
                    Scene adminDashboardScene = new Scene(userDashboard);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(adminDashboardScene);
                    window.show();
                } else {
                    // Login successful, redirect to Admin Dashboard
                    Parent adminDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/javafxtutorial/AdminDashboard.fxml")));
                    Scene adminDashboardScene = new Scene(adminDashboard);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(adminDashboardScene);
                    window.show();
                }
            } else {
                // Login failed, show error message
                statusLabel.setText("Invalid username or password");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to login");
        }
    }

    public void handleSignUp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
