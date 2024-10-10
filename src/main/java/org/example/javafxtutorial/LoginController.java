package org.example.javafxtutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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

    private final User user = new User("john_doe", "password123".toCharArray());

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getText().toCharArray();

        if (user.authenticate(username, password)) {
            statusLabel.setText("Login successful!");
        } else {
            statusLabel.setText("Invalid username or password.");
        }

        user.clearPassword();
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
