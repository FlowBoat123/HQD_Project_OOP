package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is the main entry point for the JavaFX application.
 * It initializes the login view and sets up the primary stage.
 */
public class LoginApp extends Application {

    /**
     * The start method is called when the application is launched.
     * It initializes the primary stage and sets up the login view.
     *
     * @param primaryStage The primary stage for this application.
     * @throws Exception If an error occurs during initialization.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the application icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Avatar/icon.png")));
        primaryStage.getIcons().add(icon);

        // Load the FXML file for the login view
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/application/login.fxml")));

        // Set the title of the primary stage
        primaryStage.setTitle("Library Management");

        // Set the scene with the loaded FXML content
        primaryStage.setScene(new Scene(root));

        // Show the primary stage
        primaryStage.show();
    }

    /**
     * The main method is the entry point for the application.
     * It launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}