package controller;

import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logic.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @FXML
    private Button backToLoginButton;

    private UserDAO userDAO = new UserDAO();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public void setUserFormController(UserFormController userFormController) {
        this.userFormController = userFormController;
    }

    public void signupUser(User user) {
        // Show the progress indicator and update the status label
        loadingIndicator.setVisible(true);
        signupStatusLabel.setText("Signing up...");

        // Run the add user task in a separate thread
        Runnable task = () -> {
            if (userDAO.isUsernameExists(user.getUsername())) {
                // Update the UI if the username already exists
                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    signupStatusLabel.setText("Username already exists.");
                });
            } else {
                // Add the user if the username does not exist
                userDAO.add(user);

                // Update the UI after the task is done
                javafx.application.Platform.runLater(() -> {
                    loadingIndicator.setVisible(false);
                    signupStatusLabel.setText("Signup completed!");
                });
            }
        };
        executorService.submit(task);
    }

    public SignupController() {
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
            signupStatusLabel.setText("Passwords do not match!");
            return;
        }

        // Call the method to run the insertion task
        signupUser(new User(username, password, new Timestamp(System.currentTimeMillis()).toLocalDateTime()));
    }

    private boolean fromLogin = false;

    public void setFromLogin(boolean fromLogin) {
        this.fromLogin = fromLogin;
        backToLoginButton.setVisible(fromLogin);
    }

    public void handleBackToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupStatusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}