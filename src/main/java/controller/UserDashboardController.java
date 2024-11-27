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

public class UserDashboardController implements Initializable {

    private LibraryService libraryService;
    private Button currentlyFocusedButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryService = new LibraryService();
        Circle clip = new Circle(25, 25, 25);
        profileImage.setClip(clip);
    }

    @FXML
    private StackPane mainView;

    @FXML
    private Label loadingLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label welcomeLabel;

    @FXML
    private User currentUser;

    @FXML
    public void setUser(User user) {
        this.currentUser = user;

        welcomeLabel.setText("Welcome, " + user.getUsername());
        System.out.println("Avatar URL: " + user.getAvatar());

        try {
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                // Assuming the avatar is in the resources directory
                String avatarPath = getClass().getResource(user.getAvatar()).toExternalForm();
                Image avatarImage = new Image(avatarPath, true);
                profileImage.setImage(avatarImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load avatar image: " + e.getMessage());
        }
    }


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

    private void showLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(true));
    }

    private void hideLoading() {
        Platform.runLater(() -> loadingLabel.setVisible(false));
    }

    @FXML
    private void viewUserProfile(MouseEvent event) {
        System.out.println("Navigating to the profile page of " + currentUser.getUsername());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/user_profile.fxml"));
            Parent userProfile = loader.load();

            // If you need to pass the currentUser to the new controller, do it here
            UserProfileController controller = loader.getController();
            controller.setLibraryService(libraryService);
            controller.setUser(currentUser);

            Scene userProfileScene = new Scene(userProfile);

            // Get the current stage and set the new scene
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(userProfileScene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load user profile: " + e.getMessage());
        }
    }

    public void backToLogin(ActionEvent actionEvent) {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private interface LoaderCallback {
        void call(FXMLLoader loader);
    }

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