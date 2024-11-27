package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.LibraryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Admin Dashboard view.
 * This class handles the initialization and event handling for the admin dashboard.
 * It manages the switching of views based on user interactions and maintains the focus state of buttons.
 */
public class AdminDashboardController implements Initializable {

    /**
     * Service class that provides library-related functionalities.
     */
    private LibraryService libraryService;

    /**
     * Button that is currently in focus.
     */
    private Button currentlyFocusedButton;

    /**
     * AnchorPane that serves as the main view container.
     */
    @FXML
    private AnchorPane mainView;

    /**
     * Event handler for launching the API view.
     * Loads the API view and sets it as the content of the main view.
     * Updates the focus state of the clicked button.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchAPI(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/api.fxml"));
            Node content = loader.load();
            GoogleAPIController apiController = loader.getController();
            apiController.setMainView(mainView);
            mainView.getChildren().setAll(content);
            apiController.setLibraryService(libraryService);
            updateFocus(clickedButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for launching the Add User view.
     * Loads the Add User view and sets it as the content of the main view.
     * Updates the focus state of the clicked button.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchAddUser(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/signup.fxml"));
            Node content = loader.load();
            SignupController signUpController = loader.getController();
            signUpController.setFromLogin(false);
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for launching the Library View.
     * Loads the Library View and sets it as the content of the main view.
     * Updates the focus state of the clicked button.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchLibView(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/lib-view.fxml"));
            Node content = loader.load();
            LibraryViewController libraryViewController = loader.getController();
            libraryViewController.setLibraryService(libraryService);
            libraryViewController.setMainView(mainView);
            libraryService.updateBooksRequestNumber();
            libraryViewController.initializeLibraryView(libraryService.getBooks());
            mainView.getChildren().setAll(content);
            updateFocus(clickedButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for launching the User Information view.
     * Loads the User Information view and sets it as the content of the main view.
     * Updates the focus state of the clicked button.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void launchUserInfo(ActionEvent event) {
        Object source = event.getSource();
        Button clickedButton = (Button) source;
        updateFocus(clickedButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/user-form.fxml"));
            Node content = loader.load();
            UserFormController userInfoController = loader.getController();
            userInfoController.setLibraryService(libraryService);
            mainView.getChildren().setAll(content);
            clickedButton.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It initializes the LibraryService instance.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryService = new LibraryService();
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

    /**
     * Event handler for navigating back to the login view.
     * Loads the login view and sets it as the content of the current stage.
     *
     * @param actionEvent The ActionEvent triggered by the button click.
     */
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
}