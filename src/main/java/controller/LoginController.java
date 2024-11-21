package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.User;
import org.example.javafxtutorial.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    @FXML
    private ProgressIndicator loadingIndicator;

    public void initialize() {
//        logoImageView.setStyle("-fx-background-color: red;");
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
        logoImageView.setImage(image);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        char[] password = passwordField.getText().toCharArray();
        String passwordStr = new String(password);

        Task<Void> loginTask = new Task<>() {
            @Override
            protected Void call() {
                updateMessage("Logging in...");

                Connection connection = DatabaseConnection.getConnection();
                if (connection == null) {
                    updateMessage("Failed to connect to the database");
                    return null;
                }

                String query = "SELECT id, username, password, creation_time, bio, email, website, details, avatar FROM users WHERE username = ? AND password = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, passwordStr);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        if (!username.equals("admin") && !passwordStr.equals("admin")) {
                            int ID = resultSet.getInt("id");
                            String retrievedUsername = resultSet.getString("username");
                            String retrievedPassword = resultSet.getString("password");
                            LocalDateTime creationTime = resultSet.getTimestamp("creation_time").toLocalDateTime();
                            String bio = resultSet.getString("bio");
                            String email = resultSet.getString("email");
                            String website = resultSet.getString("website");
                            String details = resultSet.getString("details");
                            String avatar = resultSet.getString("avatar");
                            User user = new User(ID, retrievedUsername, retrievedPassword, creationTime, bio, email, website, details, avatar);
                            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/example/javafxtutorial/UserDashboard.fxml")));
                            Parent userDashboard = loader.load();
                            UserDashboardController controller = loader.getController();
                            controller.setUser(user);
                            Scene userDashboardScene = new Scene(userDashboard);

                            Platform.runLater(() -> {
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(userDashboardScene);
                                window.show();
                            });
                        } else {
                            Parent adminDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/javafxtutorial/AdminDashboard.fxml")));
                            Scene adminDashboardScene = new Scene(adminDashboard);

                            Platform.runLater(() -> {
                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(adminDashboardScene);
                                window.show();
                            });
                        }
                    } else {
                        updateMessage("Invalid username or password");
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    updateMessage("Failed to login");
                }
                return null;
            }
        };

        statusLabel.textProperty().bind(loginTask.messageProperty());

        loginTask.setOnRunning(e -> loadingIndicator.setVisible(true));
        loginTask.setOnSucceeded(e -> loadingIndicator.setVisible(false));
        loginTask.setOnFailed(e -> loadingIndicator.setVisible(false));

        Thread loginThread = new Thread(loginTask);
        loginThread.setDaemon(true);
        loginThread.start();
    }


    public void handleSignUp(ActionEvent actionEvent) {
        try {
            URL fxmlLocation = getClass().getResource("/org/example/javafxtutorial/signup.fxml");
            System.out.println(fxmlLocation); // This should print the path to the console
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            // Get the controller and set the origin
            SignupController signUpController = loader.getController();
            signUpController.setFromLogin(true); // Indicate that the request came from the login screen

            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
