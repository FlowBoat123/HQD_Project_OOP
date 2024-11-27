package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import logic.User;
import logic.LibraryService;

import java.io.IOException;

/**
 * Controller class for the User Dashboard view.
 * This class handles the initialization and event handling for the user dashboard.
 * It manages the switching of views based on user interactions and maintains the focus state of buttons.
 */
public class UserDashboardController implements Initializable {

    /**
     * Service class that provides library-related functionalities.
     */
    private LibraryService libraryService;

    /**
     * Button that is currently in focus.
     */
    private Button currentlyFocusedButton;

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It initializes the LibraryService instance and sets up the profile image clipping.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryService = new LibraryService();
        Circle clip = new Circle(25, 25, 25);
        profileImage.setClip(clip);
    }

    /**
     * The main view container.
     */
    @FXML
    private StackPane mainView;

    /**
     * Label to display loading status.
     */
    @FXML
    private Label loadingLabel;

    /**
     * ImageView to display the user's profile image.
     */
    @FXML
    private ImageView profileImage;

    /**
     * Label to display the welcome message.
     */
    @FXML
    private Label welcomeLabel;

    /**
     * The current user object.
     */
    @FXML
    private User currentUser;

    /**
     * Sets the current user and updates the UI with the user's details.
     *
     * @param user The User object to be set.
     */
    @FXML
    public void setUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getUsername());
        System.out.println("Avatar URL: " + user.getAvatar());

        try {
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                String avatarPath = getClass().getResource(user.getAvatar()).toExternalForm();
                Image avatarImage = new Image(avatarPath, true);
                profileImage.setImage(avatarImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load avatar image: " + e.getMessage());
        }
    }

    /**
     * Event handler for launching the shelf view.
     * Loads the shelf view FXML and initializes it with the selected shelf's details.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchShelfView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        String shelfName = clickedButton.getText();
        loadView("/application/shelf-view.fxml", (loader) -> {
            ShelfController shelfController = loader.getController();
            shelfController.setLibraryService(libraryService);
            shelfController.setMainView(mainView);
            shelfController.setShelfTitle(shelfName);
            shelfController.init();
        });
    }

    /**
     * Event handler for launching the browse view.
     * Loads the browse view FXML and initializes it.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchBrowse(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        loadView("/application/browse-view.fxml", (loader) -> {
            BrowseUserViewController browseViewController = loader.getController();
            browseViewController.setLibraryService(libraryService);
            browseViewController.setMainView(mainView);
            browseViewController.init();
        });
    }

    /**
     * Event handler for launching the recommendation view.
     * Loads the recommendation view FXML and initializes it.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchRecommendation(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        loadView("/application/recom-view.fxml", (loader) -> {
            RecomUserViewController recomUserViewController = loader.getController();
            recomUserViewController.setLibraryService(libraryService);
            recomUserViewController.setMainView(mainView);
            recomUserViewController.init();
        });
    }

    /**
     * Loads a view asynchronously.
     *
     * @param fxmlPath The path to the FXML file.
     * @param callback The callback to be executed after the view is loaded.
     */
    private void loadView(String fxmlPath, LoaderCallback callback) {
        showLoading();
        new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node content = loader.load();
                Platform.runLater(() -> {
                    mainView.getChildren().setAll(content);
                    callback.call(loader);
                    hideLoading();
                });
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(this::hideLoading);
            }
        }).start();
    }

    /**
     * Shows the loading label.
     */
    private void showLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(true));
    }

    /**
     * Hides the loading label.
     */
    private void hideLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(false));
    }

    /**
     * Event handler for viewing the user profile.
     * Loads the user profile FXML and initializes it with the current user's details.
     *
     * @param event The MouseEvent triggered by the profile image click.
     */
    @FXML
    private void viewUserProfile(MouseEvent event) {
        System.out.println("Navigating to the profile page of " + currentUser.getUsername());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/user_profile.fxml"));
            Parent userProfile = loader.load();
            UserProfileController controller = loader.getController();
            controller.setLibraryService(libraryService);
            controller.setUser(currentUser);
            Scene userProfileScene = new Scene(userProfile);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(userProfileScene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load user profile: " + e.getMessage());
        }
    }

    /**
     * Event handler for navigating back to the login view.
     * Loads the login view FXML and sets it as the content of the current stage.
     *
     * @param actionEvent The ActionEvent triggered by the button click.
     */
    public void backToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback interface for loading views.
     */
    private interface LoaderCallback {
        void call(FXMLLoader loader);
    }

    /**
     * Updates the focus state of the buttons.
     * Removes the focus style from the previously focused button and applies it to the newly clicked button.
     *
     * @param clickedButton The button that was clicked and should receive focus.
     */
    private void updateFocus(Button clickedButton) {
        if (currentlyFocusedButton != null) {
            currentlyFocusedButton.getStyleClass().remove("focused-button");
            currentlyFocusedButton.getStyleClass().add("transparent-button");
        }
        clickedButton.requestFocus();
        clickedButton.getStyleClass().remove("transparent-button");
        clickedButton.getStyleClass().add("focused-button");
        currentlyFocusedButton = clickedButton;
    }
}