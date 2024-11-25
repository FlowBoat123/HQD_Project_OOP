package controller;

import database.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.User;
import org.example.javafxtutorial.LibraryService;
import org.example.javafxtutorial.ShelfController;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class controls the user profile view and handles interactions with the user profile.
 */
public class UserProfileController {
    @FXML
    private StackPane contentPane;  // Ensure this matches the fx:id in FXML

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private boolean isAdmin;

    @FXML
    private Label loadingSymbol;


    private LibraryService libraryService;
    private User user;

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * Sets the user for the profile.
     *
     * @param user The User object to be set.
     */
    public void setUser(User user) {
        this.user = user;
        isAdmin = false;
        showScene1(null);
    }

    /**
     * Sets the user as an admin.
     */
    public void setAdmin() {
        isAdmin = true;
    }

    /**
     * Shows the first scene (profile view).
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void showScene1(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/profile-view.fxml"));
            AnchorPane newContent = loader.load();
            ProfileViewController profileViewController = loader.getController();
            profileViewController.setUser(this.user);
            contentPane.getChildren().setAll(newContent);  // Ensure contentPane is not null
        } catch (IOException e) {
            e.printStackTrace();
        }
        button1.getStyleClass().add("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().remove("button-active");
        button4.getStyleClass().remove("button-active");
    }

    /**
     * Shows the second scene (shelf view).
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void showScene2(ActionEvent event) {
        String shelfName = "Currently Reading";
        loadView("/org/example/javafxtutorial/shelf-view.fxml", (loader) -> {
            ShelfController shelfController = loader.getController();
            shelfController.setLibraryService(libraryService);
            shelfController.setMainView(contentPane);
            shelfController.setShelfTitle(shelfName);
            shelfController.setUpShelfViewForUserProfile(user.getID());
            shelfController.init();
        });
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().add("button-active");
        button3.getStyleClass().remove("button-active");
        button4.getStyleClass().remove("button-active");
    }

    /**
     * Shows the third scene.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void showScene3(ActionEvent event) {
        String shelfName = "Completed";
        loadView("/org/example/javafxtutorial/shelf-view.fxml", (loader) -> {
            ShelfController shelfController = loader.getController();
            shelfController.setLibraryService(libraryService);
            shelfController.setMainView(contentPane);
            shelfController.setShelfTitle(shelfName);
            shelfController.setUpShelfViewForUserProfile(user.getID());
            shelfController.init();
        });
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().add("button-active");
        button4.getStyleClass().remove("button-active");
    }

    /**
     * Shows the fourth scene.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void showScene4(ActionEvent event) {
        String shelfName = "Waiting";
        loadView("/org/example/javafxtutorial/shelf-view.fxml", (loader) -> {
            ShelfController shelfController = loader.getController();
            shelfController.setLibraryService(libraryService);
            shelfController.setMainView(contentPane);
            shelfController.setShelfTitle(shelfName);
            shelfController.setUpShelfViewForUserProfile(user.getID());
            shelfController.init();
        });
        button1.getStyleClass().remove("button-active");
        button2.getStyleClass().remove("button-active");
        button3.getStyleClass().remove("button-active");
        button4.getStyleClass().add("button-active");
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {

    }

    /**
     * Handles the image click event to navigate back to the appropriate dashboard.
     *
     * @param mouseEvent The MouseEvent that triggered this method.
     */
    @FXML
    public void handleImageClick(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader;
            if (user.getID() == 2 || this.isAdmin) {
                loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/AdminDashboard.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/org/example/javafxtutorial/UserDashboard.fxml"));
            }

            Parent root = loader.load();
            Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            if (user.getID() != 2 && !this.isAdmin) {
                UserDashboardController controller = loader.getController();
                controller.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath, UserProfileController.LoaderCallback callback) {
        showLoading();
        new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node content = loader.load();
                Platform.runLater(() -> {
                    contentPane.getChildren().setAll(content);
                    callback.call(loader);
                    hideLoading();
                });
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(this::hideLoading);
            }
        }).start();
    }

    private void showLoading() {
        Platform.runLater(() -> loadingSymbol.setVisible(true));
    }

    private void hideLoading() {
        Platform.runLater(() -> loadingSymbol.setVisible(false));
    }

    private interface LoaderCallback {
        void call(FXMLLoader loader);
    }

    /**
     * Sets the avatar image for the user.
     *
     * @param avatar The Image object to be set as the avatar.
     */
    public void setAvatar(Image avatar) {
        // Implementation for setting avatar
    }

    /**
     * Stops the executor service.
     */
    @FXML
    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}