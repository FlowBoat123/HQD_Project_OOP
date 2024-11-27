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
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class controls the sign-up view and handles user registration.
 */
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

    private UserDAO userDAO;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * Sets the UserFormController.
     *
     * @param userFormController The UserFormController to be set.
     */
    public void setUserFormController(UserFormController userFormController) {
        this.userFormController = userFormController;
    }

    /**
     * Signs up a new user.
     *
     * @param user The User object to be added.
     */
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
//        shutdown();
    }

    /**
     * Constructor to initialize the UserDAO.
     */
    public SignupController() {
        userDAO = new UserDAO();
    }

    /**
     * Handles the sign-up button click event.
     *
     * @param actionEvent The ActionEvent that triggered this method.
     */
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
        signupUser(new User(username, password, new Date()));
    }

    private boolean fromLogin = false;

    /**
     * Sets whether the sign-up request came from the login screen.
     *
     * @param fromLogin True if the request came from the login screen, false otherwise.
     */
    public void setFromLogin(boolean fromLogin) {
        this.fromLogin = fromLogin;
        backToLoginButton.setVisible(fromLogin);
    }

    /**
     * Handles the back to login button click event.
     *
     * @param actionEvent The ActionEvent that triggered this method.
     */
    public void handleBackToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupStatusLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shuts down the executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }
}